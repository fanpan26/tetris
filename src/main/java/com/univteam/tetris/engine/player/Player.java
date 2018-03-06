package com.univteam.tetris.engine.player;

import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.game.GameData;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 15:31
 * @Project com.univteam.tetris
 */
public class Player {
    private long id;
    private String name;
    private String photo;

    private GameData gameData = GameData.DEFAULT;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public GameData getGameData() {
        return gameData;
    }

    /**
     * 用户玩游戏
     * */
    public void play(){
        getGameData().refresh();
    }

    /**
     * 获取历史记录，层数太多，需要重构
     * */
    public HistoryData getHistory(){
        return getGameData().getHistory();
    }
}
