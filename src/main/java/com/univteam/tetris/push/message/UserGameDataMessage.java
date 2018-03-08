package com.univteam.tetris.push.message;

import com.univteam.tetris.engine.data.HistoryData;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 18:16
 * @Project com.univteam.tetris
 */
public class UserGameDataMessage extends BaseMessage{
    private HistoryData his;

    public void setHis(HistoryData his){
        this.his = his;
    }
    public HistoryData getHis() {
        return his;
    }
}
