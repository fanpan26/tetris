package com.univteam.tetris.push.message;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 15:38
 * @Project com.univteam.tetris
 */
public class GameOverMessage {

    public GameOverMessage(String uid){
        this.uid = uid;
    }

    private String uid;

    public String getUid() {
        return uid;
    }
}