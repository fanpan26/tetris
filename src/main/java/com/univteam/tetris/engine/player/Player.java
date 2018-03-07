package com.univteam.tetris.engine.player;

import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.game.GameData;
import com.univteam.tetris.engine.room.Room;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 15:31
 * @Project com.univteam.tetris
 */
public class Player {
    private String id;
    private String name;
    private String photo;
    private Room room;

    public void setRoom(Room room){
        this.room = room;
    }

    public Room getRoom(){
        return room;
    }

    private GameData gameData = new GameData(this);

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

}
