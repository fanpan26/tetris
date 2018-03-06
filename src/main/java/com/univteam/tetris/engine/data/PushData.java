package com.univteam.tetris.engine.data;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 17:38
 * @Project com.univteam.tetris
 */
public class PushData {
    private int t;
    public void setT(int type){
        this.t = type;
    }
    public int getT(){
        return t;
    }
    public void setD(Object data){
        this.d = data;
    }
    private Object d;

    public Object getD() {
        return d;
    }

    public static PushData build(Object data, int type){
        PushData blockPushData = new PushData();
        blockPushData.setD(data);
        blockPushData.setT(type);

        return blockPushData;
    }

    public static PushData buildHisData(Object data){
        return build(data,1);
    }
}
