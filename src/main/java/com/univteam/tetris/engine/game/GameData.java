package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.block.Block;
import com.univteam.tetris.engine.block.BlockUtil;
import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.block.blocks.LetterLLeftBlock;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.point.PointCache;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

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
    private final Map<String,Point> stopedPoints = new HashMap<>(1000);
    private final Player player;
    private int totalScore;

    public int getTotalScore(){
        return totalScore;
    }

    public  GameData (Player player) {
        this.player = player;
        this.status = GameStatus.WAITING;
    }

    public GameStatus getStatus() {
        return status;
    }


    public Player getPlayer() {
        return player;
    }

    /**
     * 游戏开始
     * */
    public void start(){
        currentBlock = BlockUtil.createBlock();
        //检查当前这个方块是否在已经停止的方块上
        if (shouldStop(Direction.NONE)){
            status = GameStatus.OVER;
            gameListener.gameOver(player);
        }else {
            status = GameStatus.STARTED;
        }
    }

    /**
     * 游戏下一步
     * */
    public void refresh() {
        switch (status){
            case WAITING:
            case STOPED:
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
                System.out.println("游戏已经结束");
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
                System.out.println("不可以旋转，撤回");
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
            currentBlock.down();
        }
    }

    /**
     * 获取历史数据
     * */
    public HistoryData getHistory(){
        return currentBlock.getHistory();
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
    private void calculate(){
        int total = GameMap.getFullCount(stopedPoints);
        if(total > 0) {
            int totalScore = ScoreIndicator.getScore(total);
            this.totalScore += totalScore;
            gameListener.scoreChange(player);
        }
    }

    /**
     * 游戏状态
     * */
    private enum GameStatus{
        WAITING,
        STARTED,
        STOPED,
        OVER
    }


    /**
     * 方块方向
     * */
    private enum Direction{
        LEFT,RIGHT,DOWN,ROTATE,NONE
    }
}
