package com.univteam.tetris.engine.block;

import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.data.HistoryData;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：方块接口
 * @Date Created at 2018/3/5 14:33
 * @Project com.univteam.tetris
 */
public interface Block {
    /**
     * 旋转
     * */
    void rotate();

    /**
     * 上一步
     * */
    void prev();

    /**
     * 下一步
     * */
    void next();
    /**
     * 停止
     * */
    void stop();

    /**
     * 方块向左移动
     * */
    void left();
    /**
     * 方块向右移动
     * */
    void right();
    /**
     * 方块快速向下
     * */
    void down();
    /**
     * 获取方块的元素
     * */
    Point[] getPoints();

    HistoryData getHistory();
}
