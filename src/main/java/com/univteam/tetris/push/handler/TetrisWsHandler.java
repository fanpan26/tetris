package com.univteam.tetris.push.handler;

import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.room.Room;
import com.univteam.tetris.push.BodyWrapper;
import com.univteam.tetris.push.handler.cmd.AbstractCmdHandler;
import com.univteam.tetris.push.handler.cmd.CmdFactory;
import org.apache.commons.lang3.StringUtils;
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

        /**
         * 处理指令
         * */
        private String getCmd(String text) {

            if (StringUtils.isBlank(text)) {
                return "";
            }
            String[] cmds = text.split(":");
            if (cmds.length>2){
                return cmds[1];
            }
            return "";
        }
        @Override
        public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {

            String cmd = getCmd(text);
            AbstractCmdHandler handler = CmdFactory.getHandler(cmd);
            if(handler == null){
                return BodyWrapper.createBody(PushData.build("无效的命令" ,0)) ;
            }
           return handler.handle(text,channelContext);
        }

        @Override
        public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
            return httpResponse;
        }

        @Override
        public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
            return null;
        }
    }
