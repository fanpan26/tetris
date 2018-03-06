package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.block.Block;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.point.PointCache;
import org.omg.PortableServer.POA;

import java.util.Map;

/**
 * @Author fyp
 * @Description 俄罗斯方块游戏：地图
 * @Date Created at 2018/3/5 14:14
 * @Project com.univteam.pushserver
 */
public class GameMap {

    /**
     * 获取起始点
     * */
    public static Point getStartPoint(){
        return PointCache.getPoint(15,0);
    }
    /**
     * 地图宽度
     * */
    public static final int MAX_RANGE_WIDTH = 30;
    /**
     * 地图高度
     * */
    public static final int MAX_RANGE_HEIGHT = 60;

    /**
     * 判断一个方块是否出界
     * */
    public static boolean outOfRange(Block block){
        Point[] points = block.getPoints();
        boolean out ;
        for (Point point : points){
            out = outOfRange(point);
            if(out){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否在底部边界
     * */
    public static boolean onBottom(Block block){
        Point[] points = block.getPoints();
        boolean bottom;
        for (Point point : points){
            bottom = onBottom(point);
            if(bottom){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个点是否在最底部
     * */
    private static boolean onBottom(Point point){
        return point.getY() == GameMap.MAX_RANGE_HEIGHT  - 1;
    }
    /**
     * 判断一个点是否出界
     * */
    private static boolean outOfRange(Point point){
        return !(point.getX()>=0 && point.getX() <= 29 && point.getY() >=0 && point.getY() <=59);
    }
}
