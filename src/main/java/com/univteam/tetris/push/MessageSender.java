package com.univteam.tetris.push;

import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.push.message.*;
import org.tio.core.Aio;
import org.tio.core.GroupContext;
import org.tio.websocket.common.WsResponse;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 15:29
 * @Project com.univteam.tetris
 */
public class MessageSender {

    /**
     * 发送积分消息
     * */
    public static void sendUserScoreMessage(String groupId,String userId,int score){

        PushData data = PushData.build(new UserScoreMessage(userId,score),2);
        sendMessage(groupId,data);
    }

    /**
     * 发送游戏结束信息
     * */
    public static void sendGameOverMessage(String groupId,String userId) {
        PushData data = PushData.build(new GameOverMessage(userId), 3);
        sendMessage(groupId, data);
    }


    /**
     * 发送系统消息
     * */
    public static void sendSysMessage(String groupId,String message){
        SysMessage sysMessage = new SysMessage(message);

        PushData data = PushData.build(sysMessage,100);
        sendMessage(groupId, data);
    }

    /**
     * 发送加入聊天室消息
     * */
    public static void sendJoinRoomMessage(String groupId, String userId, JoinRoomMessage message){
        PushData data = PushData.build(message,0);
        sendMessage(groupId, data);
    }

   /**
    * 发送游戏数据消息
    * */
    public static void sendGameDataMessage(String groupId, String userId, HistoryData historyData) {
        UserGameDataMessage message = new UserGameDataMessage();
        message.setUid(userId);
        message.setHis(historyData);

        PushData data = PushData.buildHisData(message);
        sendMessage(groupId, data);
    }

    /**
     * 发送消息
     * */
    private static void sendMessage(String groupId, Object data){
        GroupContext groupContext = GameStarter.getWsServerStarter().getServerGroupContext();
        Aio.sendToGroup(groupContext,groupId,BodyWrapper.createBody(data));
    }
}
