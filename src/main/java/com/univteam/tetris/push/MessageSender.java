package com.univteam.tetris.push;

import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.push.message.GameOverMessage;
import com.univteam.tetris.push.message.UserScoreMessage;
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
     * 发送消息
     * */
    private static void sendMessage(String groupId, Object data){
        GroupContext groupContext = GameStarter.getWsServerStarter().getServerGroupContext();
        Aio.sendToGroup(groupContext,groupId,BodyWrapper.createBody(data));
    }
}
