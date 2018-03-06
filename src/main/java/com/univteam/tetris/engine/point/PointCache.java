package com.univteam.tetris.engine.point;

import com.univteam.tetris.engine.GameMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/5 16:06
 * @Project com.univteam.tetris
 */
public class PointCache {
    /**
     * 地图上的点缓存
     * */
    private final static ConcurrentMap<String,Point> cachedPointMap = new ConcurrentHashMap(GameMap.MAX_RANGE_HEIGHT * GameMap.MAX_RANGE_WIDTH);

    /**
     * 根据坐标获取缓存key
     * */
    private static String getKey(int x,int y){
        return x + "_"+ y;
    }

    /**
     * 从缓存中获取point
     * */
    public static Point getPoint(int x,int y){
        String key = getKey(x,y);
        Point point = cachedPointMap.get(key);
        if(point == null){
            point = new Point(x,y);
            cachedPointMap.putIfAbsent(key,point);
        }
        return point;
    }
}
