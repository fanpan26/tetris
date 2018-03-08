package com.univteam.tetris.push.message;

import com.univteam.tetris.engine.player.Player;

import java.util.List;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/8 11:44
 * @Project com.univteam.tetris
 */
public class JoinRoomMessage extends BaseMessage {

    public JoinRoomMessage(String currentUid, List<Player> players){
        this.uid = currentUid;
        setUsers(players);
    }

    private User[] users;

    private void setUsers(List<Player> players){
        users = new User[players.size()];
        int i = 0;
        for (Player player : players){
            users[i] = new User(player.getId(),player.getPhoto(),player.getName());
            i++;
        }
    }

    public User[] getUsers() {
        return users;
    }
}
