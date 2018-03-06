package com.univteam.tetris.push.handler;

import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
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
           // WsResponse response = BodyWrappe.createBody(s);
           // Aio.sendToGroup(channelContext.getGroupContext(),UnivTeamConst.UNIVTEAM_ID,response);
            return null;
        }

        @Override
        public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
//            Aio.bindGroup(channelContext,UnivTeamConst.UNIVTEAM_ID);
//            Aio.bindUser(channelContext, SystemTimer.currentTimeMillis()+"");
            return httpResponse;
        }

        @Override
        public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
            return null;
        }
    }
