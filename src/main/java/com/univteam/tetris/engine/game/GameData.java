package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.block.BlockUtil;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.point.PointCache;
import com.univteam.tetris.engine.block.Block;
import com.univteam.tetris.engine.data.HistoryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 16:42
 * @Project com.univteam.tetris
 */
public class GameData {

    private GameStatus status;
    private Block currentBlock;
    private final GameListener gameListener = new DefaultGameListener();
    private final ConcurrentMap<String,Point> stopedPoints = new ConcurrentHashMap<>(1000);
    private final Player player;
    private Block nextBlock;
    private int totalScore;

    public int getTotalScore(){
        return totalScore;
    }

    public  GameData (Player player) {
        this.player = player;
        this.status = GameStatus.WAITING;
        nextBlock = BlockUtil.createBlock();
    }

    public GameStatus getStatus() {
        return status;
    }

    public boolean isOver(){
        return status == GameStatus.OVER;
    }


    public Player getPlayer() {
        return player;
    }

    /**
     * 游戏开始
     * */
    public void start(){
       createNextBlock();
    }

    /*
    * 创建一个新的方块
    * **/
    private void createNextBlock(){
        currentBlock = nextBlock;
        nextBlock = BlockUtil.createBlock();
        if (shouldStop(Direction.NONE)){
            status = GameStatus.OVER;
            gameListener.gameOver(player);
        }else {
            status = GameStatus.STARTED;
            gameListener.createBlock(player.getRoom().getGroupId(), player.getId(), nextBlock);
        }
    }

    /**
     * 设置准备就绪状态
     * */
    public void prepare(){
        status = GameStatus.PREPARED;
        totalScore = 0;
        stopedPoints.clear();
    }

    /**
     * 游戏下一步
     * */
    public void refresh() {
        switch (status){
            case WAITING:
                break;
            case STOPED:
            case PREPARED:
                start();
                break;
            case STARTED:
                //如果到了停止状态，就先停止
                if (GameMap.onBottom(currentBlock) || shouldStop(Direction.DOWN)){
                    //停止
                    currentBlock.stop();
                    //将已经停止的方块保存起来
                    for (Point point : currentBlock.getPoints()) {
                        stopedPoints.put(PointCache.getKey(point.getX(),point.getY()),point);
                    }
                    //状态设置为停止
                    status =  GameStatus.STOPED;
                    //检查分数
                    calculate();
                }else{
                    currentBlock.next();
                }
                break;
            case OVER:
                //gameListener.gameOver(player);
                break;
        }
    }

    /**
     * 方块变向
     * */
    public void rotate() {
        if (gameStarted()) {
            currentBlock.rotate();
            if (!canRotate()){
                currentBlock.prev();
            }
        }
    }

    /**
     * 方块向左
     * */
    public void left(){
        if (gameStarted()&&canMoveToLeft()) {
            currentBlock.left();
        }
    }

    /**
     * 方块向右
     * */
    public void right() {
        if (gameStarted() && canMoveToRight()) {
            currentBlock.right();
        }
    }

    /**
     * 方块到最下边
     * */
    public void down(){
        if (gameStarted()) {
            //这里必须加判断，否则键盘向下操作会导致点不对的问题
            if (!GameMap.onBottom(currentBlock) && !shouldStop(Direction.DOWN)) {
                currentBlock.down();
            }
        }
    }

    /**
     * 获取历史数据
     * */
    public HistoryData getHistory(){
        if (currentBlock != null) {
            return currentBlock.getHistory();
        }else{
            return null;
        }
    }

    /**
     * 游戏是否开始
     * */
    private boolean gameStarted(){
        return status == GameStatus.STARTED;
    }

    /**
     * 是否可以旋转
     * */
    private boolean canRotate(){
       return !shouldStop(Direction.ROTATE);
    }
    /**
     * 是否可以向左移动
     * */
    private boolean canMoveToLeft(){
        return !GameMap.onLeft(currentBlock) && !shouldStop(Direction.LEFT);
    }

    /**
     * 是否可以向右移动
     * */
    private boolean canMoveToRight(){
        return !GameMap.onRight(currentBlock) && !shouldStop(Direction.RIGHT);
    }

    /**
     * 判断是否有其他方块阻碍
     * */
    private boolean shouldStop(Direction direction){
        if (stopedPoints.size() > 0) {
            Point nextPoint;
            //判断下一步的点是否在已经停止的点中存在
            for (Point point : currentBlock.getPoints()) {
                switch (direction) {
                    case LEFT:
                        nextPoint = point.left();
                        break;
                    case RIGHT:
                        nextPoint = point.right();
                        break;
                    case DOWN:
                        nextPoint = point.down();
                        break;
                    case ROTATE:
                    case NONE:
                        nextPoint = point;
                        break;
                    default:
                        nextPoint = point;
                        break;
                }
                if (stopedPoints.containsKey(PointCache.getKey(nextPoint.getX(),nextPoint.getY()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 计算分数
     * */
    private void calculate() {
        List<Integer> list = GameMap.getFullCountY(stopedPoints);
        if (list.size() > 0) {
            int totalScore = ScoreIndicator.getScore(list.size());
            this.totalScore += totalScore;
            gameListener.scoreChange(player);
            //消除整行
            doAfterCalculate(list);
        }
    }

    /**
     * 获取分数之后，要计算新的位置
     * */

    private void doAfterCalculate(List<Integer> list) {
        //遍历point
        List<Point> shouldClearPoints = new ArrayList<>();
        List<Point> shouldStopedPoints = new ArrayList<>();

        System.out.println("移除之前条数为：" + stopedPoints.size());
        //第一遍循环，移除满行的点
        int y;
        for (Integer l : list) {
            y = l.intValue();
            String key;
            //遍历需要移除的点，都移除掉
            for (int x = 0; x < GameMap.MAX_RANGE_WIDTH; x++) {
                key = PointCache.getKey(x, y);
                if (stopedPoints.containsKey(key)) {
                    stopedPoints.remove(key);
                    shouldClearPoints.add(PointCache.getPoint(x, y));
                }
            }
        }
        //第二遍循环，重置每个点的位置
        //遍历剩余的点
        int downCount;
        for (Map.Entry<String, Point> entry : stopedPoints.entrySet()) {
            downCount = 0;
            Point point = entry.getValue();
            for (Integer l : list) {
                y = l.intValue();
                if (point.getY() < y) {
                    downCount++;
                }
            }
            //根据downCount向下移动
            if (downCount > 0){
                shouldClearPoints.add(point);
                shouldStopedPoints.add(point.down(downCount));
            }
        }
        System.out.println("移除之后条数为：" + stopedPoints.size());
        //最后消除不需要的行数
        for (Point point : shouldClearPoints){
            stopedPoints.remove(PointCache.getKey(point.getX(),point.getY()));
        }
        for (Point point : shouldStopedPoints){
            stopedPoints.put(PointCache.getKey(point.getX(),point.getY()),point);
        }
        //消除整行之后，重新画图
        currentBlock.getHistory().resetData(shouldStopedPoints.toArray(new Point[shouldStopedPoints.size()]), shouldClearPoints.toArray(new Point[shouldClearPoints.size()]));
    }

    /**
     * 游戏状态
     * */
    private enum GameStatus{
        /**
         * 等待开始
         * */
        WAITING,
        /**
         * 准备就绪，可以开始
         * */
        PREPARED,
        /**
         * 已经开始
         * */
        STARTED,
        /**
         * 停止状态
         * */
        STOPED,
        /**
         * 游戏结束
         * */
        OVER
    }


    /**
     * 方块方向
     * */
    private enum Direction{
        LEFT,RIGHT,DOWN,ROTATE,NONE
    }
}
