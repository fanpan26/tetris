package com.univteam.tetris.push.message;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 15:31
 * @Project com.univteam.tetris
 */
public class UserScoreMessage {

    public UserScoreMessage(String uid,int score){
        this.uid = uid;
        this.score = score;
    }

    private String uid;
    private int score;

    public String getUid() {
        return uid;
    }

    public int getScore(){
        return score;
    }
}
