package com.univteam.tetris.engine.data;

import com.univteam.tetris.engine.point.Point;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 16:57
 * @Project com.univteam.tetris
 */
public class HistoryData {
    private String cleared;
    private String added;
    private String stoped;

    private String getData(Point[] points) {
        if (points == null||points.length == 0) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        for (Point point : points) {
            str.append(point);
            str.append("|");
        }
        String s = str.toString();
        return s.substring(0,s.length() - 1);
    }

    public  void setCleared(Point[] points){
        this.cleared = getData(points);
    }

    public void setAdded(Point[] points) {
        this.added = getData(points);
    }

    public void setStoped(Point[] points){
        this.stoped = getData(points);
    }

    public String getAdded() {
        return added;
    }
    public String getCleared(){
        return cleared;
    }
    public String getStoped(){return stoped;}

    public void resetData(Point[] stoped,Point[] cleared){
        setAdded(null);
        setCleared(cleared);
        setStoped(stoped);
    }
}
