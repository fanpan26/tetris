package com.univteam.tetris.engine.block;

import com.univteam.tetris.engine.point.Point;

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
    protected final Point[] points = new Point[POINT_NUM];
    protected BlockStatus blockStatus;

    public AbstractBlock(){
        this(new Point(0,0));
    }

    public AbstractBlock(Point startPoint){
        blockStatus = getDefaultStatus();
        build(startPoint);
    }

    public BlockStatus getBlockStatus() {
        return blockStatus;
    }

    /**
     * @description 返回方块
     * */
    public Point[] getPoints(){
        return points;
    }

    /**
     * @description 旋转
     * */
    @Override
    public void rotate() {
        if(rotatable()){
           doRotate();
        }
    }

    /**
     * @description 是否支持旋转
     * */
    public abstract boolean rotatable();

    /**
     * @description 旋转操作
     * */
    public abstract void doRotate();

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
        return points[0].toString() + points[1] + points[2] + points[3];
    }
}
