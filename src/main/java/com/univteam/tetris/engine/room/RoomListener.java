package com.univteam.tetris.engine.room;

import com.univteam.tetris.engine.data.HistoryData;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 17:09
 * @Project com.univteam.tetris
 */
public interface RoomListener {
    void onchange(HistoryData historyData,String uid);
}
