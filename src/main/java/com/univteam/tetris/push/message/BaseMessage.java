package com.univteam.tetris.push.message;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 18:16
 * @Project com.univteam.tetris
 */
public class BaseMessage {
    protected String uid;
    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }
}
