package com.univteam.tetris.engine.point;

import com.univteam.tetris.engine.GameMap;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：地图上的某个点
 * @Date Created at 2018/3/5 14:20
 * @Project com.univteam.tetris
 */
public class Point {

    /**
     * 坐标构造函数
     * */
    public Point(int x,int y){
        if(x < 0 || x >= GameMap.MAX_RANGE_WIDTH){
            throw new IllegalArgumentException("x");
        }
        if(y < 0 || y >= GameMap.MAX_RANGE_HEIGHT){
            throw new IllegalArgumentException("y");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;

    private int y;

    /**
     * 获取当前点左边的点
     * */
    public Point left(){
        return PointCache.getPoint(x - 1,y);
    }
    /**
     * 获取当前点右边的点
     * */
    public Point right(){
        return  PointCache.getPoint(x + 1,y);
    }

    /**
     * 获取当前点上边的点
     * */
    public Point up(){
        return PointCache.getPoint(x ,y - 1);
    }

    /**
     * 获取当前点下边的点
     * */
    public Point down(){
        return  PointCache.getPoint(x ,y + 1);
    }


    @Override
    public String toString() {
        return String.format("X:%d Y:%d",x,y);
    }
}
