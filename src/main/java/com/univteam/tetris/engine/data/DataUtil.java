package com.univteam.tetris.engine.data;

import com.univteam.tetris.engine.point.Point;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/8 15:19
 * @Project com.univteam.tetris
 */
public class DataUtil {
    /**
     * 将点转化为字符串
     * */
    public static String transData(Point[] points) {
        if (points == null || points.length == 0) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        for (Point point : points) {
            str.append(point);
            str.append("|");
        }
        String s = str.toString();
        return s.substring(0, s.length() - 1);
    }
}
