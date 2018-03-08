package com.univteam.tetris.engine.room;

import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 15:27
 * @Project com.univteam.tetris
 */
public class Room {

    public Room(long roomId){
        this.roomId = roomId;
    }

    private long roomId;
    public long getRoomId(){
        return roomId;
    }

    public String getGroupId(){
        return getRoomId()+"";
    }

    /**
     * 设置监听器
     * */
    public void setRoomListener(RoomListener listener){
        this.listener = listener;
    }
    /**
     * 每个房间的最大游戏人数
     * */
    private final int PLAYER_COUNT = 2;
    /**
     * 游戏是否开始
     * */
    private boolean started = false;

    /**
     * 当前房间玩家列表
     * */
    private final List<Player> players = new ArrayList<>(2);

    /**
     * 当前房间监听器
     * */
    private RoomListener listener;

    /**
     * 判断是否满员
     * */
    public boolean isFull(){
        return players.size() >= PLAYER_COUNT;
    }

    /**
     * 游戏开始
     * */
    private void gameStart(){
        started = true;
    }
    /**
     * 添加一名玩家
     * */
    public synchronized boolean addPlayer(Player player){
        if(isFull()) {
            return false;
        }
        if (joined(player)){
            return true;
        }
        players.add(player);
        if(isFull()){
           gameStart();
        }
        return true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * 玩家是否已经加入
     * */
    private boolean joined(Player player){
        for (Player p : players){
            if (p.getId().equals(player.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前的player
     * */
    public Player getPlayer(String playerId){
        for (Player player : players){
            if (player.getId().equals(playerId)){
                return player;
            }
        }
        return null;
    }

    public RoomListener getListener() {
        return listener;
    }

    /**
     * 更新游戏状态
     * */
    public void refresh(){
        if (started) {
            //System.out.println("游戏已经开始...");
            for (Player player : players){
                player.play();
                HistoryData historyData = player.getGameData().getHistory();
                if (listener != null && historyData != null){
                    listener.onchange(historyData,player.getId());
                }
            }
        }else{
            //System.out.println("游戏尚未开始,正在等人...");
        }
    }
}
