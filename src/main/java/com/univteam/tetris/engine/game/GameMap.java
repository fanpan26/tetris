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
        return PointCache.getPoint(5,0);
    }
    /**
     * 地图宽度
     * */
    public static final int MAX_RANGE_WIDTH = 10;
    /**
     * 地图高度
     * */
    public static final int MAX_RANGE_HEIGHT = 20;

    /**
     * 判断一个方块是否出界
     * */
    public static boolean outOfRange(Block block){
        return checkPoint(block, point -> outOfRange(point));
    }

    /**
     * 判断是否在底部边界
     * */
    public static boolean onBottom(Block block){
        return checkPoint(block, point -> onBottom(point));
    }

    /**
     * 检查是否在边界，左右
     * */
    public static boolean onLeft(Block block) {
        return checkPoint(block, point -> onLeft(point));
    }
    /**
     * 检查是否在边界，左右
     * */
    public static boolean onRight(Block block) {
        return checkPoint(block, point -> onRight(point));
    }

    /**
     * 检查是否得分
     * */
    public static int getFullCount(Map<String,Point> points) {
        if(points == null || points.size() < MAX_RANGE_WIDTH-1){
            return 0;
        }
        int total = 0;
        boolean fullCount = false;
        for (int y = MAX_RANGE_HEIGHT - 1; y >= 0; y--) {
            fullCount = true;
            for (int x = 0; x < MAX_RANGE_WIDTH; x++) {
                //如果不存在某个点，说明不是一个满行，不能加分
                if (!points.containsKey(PointCache.getKey(x, y))){
                    fullCount = false;
                    break;
                }
            }
            if(fullCount) {
                total += 1;
            }
        }
        return total;
    }

    /**
     * 总的检查方法
     * */
    private static boolean checkPoint(Block block,CheckPoint check){
        Point[] points = block.getPoints();
        boolean res;
        for (Point point : points){
            res = check.doCheck(point);
            if(res){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个点是否在最右边
     * */
    private static boolean onRight(Point point){
        return point.getX() == GameMap.MAX_RANGE_WIDTH - 1;
    }
    /**
     * 判断某个点是否在最左边
     * */
    private static boolean onLeft(Point point){
        return point.getX() == 0 ;
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

    /**
     * 点检查
     * */
    protected interface CheckPoint{
        boolean doCheck(Point point);
    }
}
