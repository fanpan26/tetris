package com.univteam.tetris.push.handler;

import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.room.Room;
import com.univteam.tetris.push.BodyWrapper;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/5 18:01
 * @Project com.univteam.tetris
 */
public class TetrisWsHandler implements IWsMsgHandler {
        @Override
        public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
            Aio.remove(channelContext,"onClose");
            return null;
        }

        @Override
        public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
           Room room = GameStarter.getEngine().getRooms().get(s);
           if(room == null){
               return "无效的房间号";
           }else {
               Player player = new Player();
               player.setId(10000L);
               player.setName("test");
               player.setPhoto("");

               boolean res = room.addPlayer(player);
               return BodyWrapper.createBody(PushData.build(res ?"加入房间成功" : "游戏已经开始，观战中...",0)) ;
           }
        }

        @Override
        public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
            Aio.bindUser(channelContext,"10000");
            //测试阶段，绑定房间号
            Aio.bindGroup(channelContext,"1");
            return httpResponse;
        }

        @Override
        public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
            return null;
        }
    }
