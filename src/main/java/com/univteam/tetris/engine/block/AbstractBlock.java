package com.univteam.tetris.engine.block;

import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.game.GameMap;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/5 14:42
 * @Project com.univteam.tetris
 */
public abstract class AbstractBlock implements Block {

    /**
     * @description 每个方块四个点
     * */
    private final int POINT_NUM = 4;
    /**
     * 历史轨迹，用于数据传输
     * */
    private final HistoryData historyData = new HistoryData();
    protected final Point[] points = new Point[POINT_NUM];
    //保存变形之前的数据，用于还原
    private final Point[] beforeRotate = new Point[POINT_NUM];
    protected BlockStatus blockStatus;

    public AbstractBlock(){
        this(GameMap.getStartPoint());
    }

    public AbstractBlock(Point startPoint){
        blockStatus = getDefaultStatus();
        build(startPoint);

        historyData.setAdded(points);
    }

    public BlockStatus getBlockStatus() {
        return blockStatus;
    }

    /**
     * @description 返回方块
     * */
    @Override
    public Point[] getPoints(){
        return points;
    }
    @Override
    public HistoryData getHistory() {
        return historyData;
    }

    /**
     * @description 旋转
     * */
    @Override
    public void rotate() {
        if (rotatable()) {
            changeStatusToClear();
            saveRotateData();
            try {
                doRotate();
                changeStatusToNormal();
            }catch (RotatedFailedException e){
                System.out.println("旋转失败，需要返回");
                prev();
            }
        }
    }

    /**
     * 方块向左移动
     * */
    @Override
    public void left(){
        changeStatusToClear();
        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].left();
        }
        changeStatusToNormal();
    }

    /**
     * 方块向右移动
     * */
    @Override
    public void right(){
        changeStatusToClear();
        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].right();
        }
        changeStatusToNormal();
    }

    /**
     * 方块快速向下移动
     * */
    @Override
    public void down(){
        next();
    }

    /**
     * 点向下移动
     * 将之前的点设置为清除状态
     * 之后的点设置为方块状态
     * */
    @Override
    public void next() {
        changeStatusToClear();
        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].down();
        }
        changeStatusToNormal();
    }

    /**
     * 上一步
     * */
    @Override
    public void prev(){
        for (int i = 0; i < points.length; i++) {
            points[i] = beforeRotate[i];
        }
        historyData.setAdded(null);
        historyData.setCleared(null);
    }

    /**
     * 下一步
     * */
    @Override
    public void stop(){
        changeStatusToStop();
        historyData.setStoped(points);
    }

    /**
     * @description 是否支持旋转
     * */
    public abstract boolean rotatable();

    /**
     * @description 旋转操作
     * */
    public abstract void doRotate() throws RotatedFailedException;

    /**
     * @description 构建方块
     * */
    protected abstract void build(Point startPoint);

    /**
     * @description 默认的方块状态
     * */
    protected abstract BlockStatus getDefaultStatus();

    @Override
    public String toString() {
        return points[0].toString() +" "+ points[1] +" "+ points[2] +" "+ points[3];
    }

    /**
     * 每个点的状态变化
     * */
    private void changeStatusToNormal(){
        historyData.setAdded(points);
    }
    /**
     * 将点清除
     * */
    private void changeStatusToClear(){
        historyData.setCleared(points);
    }
    /**
     * 将点的状态替换成已经停止
     * */
    private void changeStatusToStop(){
    }

    /**
     * 保存变形前数据
     * */
    private void saveRotateData(){
       for (int i=0;i<4;i++){
           beforeRotate[i] = points[i];
       }
    }
}
