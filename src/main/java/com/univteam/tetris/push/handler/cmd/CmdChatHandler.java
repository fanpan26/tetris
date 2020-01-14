package com.univteam.tetris.push.handler.cmd;

import com.univteam.tetris.push.MessageSender;
import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.room.Room;
import com.univteam.tetris.push.BodyWrapper;
import org.apache.commons.lang3.StringUtils;
import org.tio.core.ChannelContext;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/8 17:36
 * @Project com.univteam.tetris
 */
public class CmdChatHandler extends AbstractCmdHandler {

    @Override
    public Object handle(String params, ChannelContext channelContext) {
        String[] cmds = params.split(":");
        if (cmds.length == 5) {
            String roomId = cmds[2];
            String userId = cmds[3];
            String chatContent;
            try {
                chatContent = URLDecoder.decode(cmds[4], "utf-8");
            } catch (UnsupportedEncodingException e) {
                //不处理
                chatContent = "*** ***";
            }
            if (StringUtils.isBlank(chatContent)) {
                return null;
            } else {
                Room room = GameStarter.getEngine().getRooms().get(roomId);
                if (room == null) {
                    return null;
                } else {
                    Player player = room.getPlayer(userId);
                    if (player == null) {
                        return null;
                    } else {
                        MessageSender.sendSysMessage(roomId,String.format("%s 说：%s",player.getName(),chatContent));
                        return null;
                    }
                }
            }
        } else {
            return BodyWrapper.createBody(PushData.build("指令无效", 100));
        }
    }
}
