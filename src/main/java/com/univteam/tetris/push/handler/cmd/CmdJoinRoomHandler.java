package com.univteam.tetris.push.handler.cmd;

import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.player.PlayerCreator;
import com.univteam.tetris.engine.room.Room;
import com.univteam.tetris.push.BodyWrapper;
import com.univteam.tetris.push.MessageSender;
import com.univteam.tetris.push.message.JoinRoomMessage;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 10:50
 * @Project com.univteam.tetris
 */
public class CmdJoinRoomHandler extends AbstractCmdHandler {

    /**
     * CMD:JOIN:ROOMID
     * */
    @Override
    public Object handle(String cmd, ChannelContext channelContext) {
        String[] cmds = cmd.split(":");
        String roomId;
        String userId;
        if (cmds.length == 4) {
             roomId = cmds[2];
             userId = cmds[3];
        }else{
            roomId = null;
            userId = null;
        }
        Room room = GameStarter.getEngine().getRooms().get(roomId);
        if (room == null) {
            return BodyWrapper.createBody(PushData.build("无效的房间号", 0));
        } else {
            Player player = PlayerCreator.create(userId);
            player.setRoom(room);

            boolean res = room.addPlayer(player);

            if (res){
                Aio.bindGroup(channelContext,roomId);
                Aio.bindUser(channelContext,player.getId());
                //发送加入房间的消息
                MessageSender.sendJoinRoomMessage(roomId,player.getId(),new JoinRoomMessage(player.getId(),room.getPlayers(),room.isFull()));
                //要发送一条系统消息
                MessageSender.sendSysMessage(roomId,String.format("系统：用户【%s】加入游戏",player.getName()));
                return null;
            }else{
                MessageSender.sendSysMessage(roomId,String.format("系统：用户【%s】加入游戏，观战中...",player.getName()));
                return null;
            }
        }
    }
}
