package com.univteam.tetris.push.handler.cmd;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 11:00
 * @Project com.univteam.tetris
 */
public class CmdFactory {
    private static Map<String,AbstractCmdHandler> cmdHandlerMap = new HashMap<>(2);

    static {
        cmdHandlerMap.put("JOIN",new CmdJoinRoomHandler());
        cmdHandlerMap.put("UP",new CmdUpHandler());
        cmdHandlerMap.put("DOWN",new CmdDownHandler());
        cmdHandlerMap.put("LEFT",new CmdLeftHandler());
        cmdHandlerMap.put("RIGHT",new CmdRightHandler());
    }

    public static AbstractCmdHandler getHandler(String cmd){
        return cmdHandlerMap.get(cmd);
    }
}
