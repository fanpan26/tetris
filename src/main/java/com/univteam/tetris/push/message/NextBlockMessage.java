package com.univteam.tetris.push.message;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/8 15:21
 * @Project com.univteam.tetris
 */
public class NextBlockMessage extends BaseMessage {
    private String points;

    public NextBlockMessage(String userId,String points){
        this.uid = userId;
        this.points = points;
    }

    public String getPoints() {
        return points;
    }
}
