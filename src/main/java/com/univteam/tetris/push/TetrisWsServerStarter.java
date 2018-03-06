package com.univteam.tetris.push;

import com.univteam.tetris.push.handler.TetrisWsHandler;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/5 17:59
 * @Project com.univteam.tetris
 */
public class TetrisWsServerStarter {
    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    public TetrisWsServerStarter(int port) throws Exception {
        WsServerConfig config = new WsServerConfig(port);
        wsServerStarter = new WsServerStarter(config, new TetrisWsHandler());
        serverGroupContext = wsServerStarter.getServerGroupContext();
    }

    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }

    public void start() throws IOException {
        wsServerStarter.start();
    }

    /**
     * @return the serverGroupContext
     */
    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }
}
