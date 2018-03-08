package com.univteam.tetris.push.handler.cmd;

import org.apache.commons.lang3.StringUtils;
import org.tio.core.ChannelContext;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 10:48
 * @Project com.univteam.tetris
 */
public abstract class AbstractCmdHandler {
    /**
     * 处理消息
     * */
    public abstract Object handle(String params, ChannelContext channelContext);
}
