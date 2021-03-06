package com.univteam.tetris.engine.game;

import com.univteam.tetris.push.MessageSender;
import com.univteam.tetris.engine.room.Room;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 15:13
 * @Project com.univteam.tetris
 */
public class GameEngine {

    private final int refreshTime;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
    private ScheduledExecutorService stateService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> mapFuture;

    private HashMap<String,Room> rooms = new HashMap<>(2);
    private void initRoom() {
        Room room = new Room(1);
        room.setRoomListener((data,uid) -> {
            MessageSender.sendGameDataMessage(room.getGroupId(),uid,data);
        });
        rooms.put("1", room);
        //rooms.put("2",new Room(2));
    }

    public GameEngine(){
        refreshTime = 200;
        initRoom();
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    /**
     * 游戏开始
     * */
    public void start() {
        mapFuture = executorService.scheduleWithFixedDelay(() -> {
            gameRefresh();
        }, refreshTime, refreshTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 游戏刷新
     * */
    private void gameRefresh(){
        for (Map.Entry<String,Room> entry : rooms.entrySet()){
            entry.getValue().refresh();
        }
    }
}
