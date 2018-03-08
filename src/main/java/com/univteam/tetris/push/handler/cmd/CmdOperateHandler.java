package com.univteam.tetris.push.handler.cmd;


import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.room.Room;
import com.univteam.tetris.push.BodyWrapper;
import org.tio.core.ChannelContext;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 12:23
 * @Project com.univteam.tetris
 */
public class CmdOperateHandler extends  AbstractCmdHandler {

    public void handle(Player player){

    }

    @Override
    public Object handle(String params, ChannelContext channelContext){
        String[] cmds = params.split(":");
        if (cmds.length == 4) {
           String roomId = cmds[2];
           String userId = cmds[3];

            Room room = GameStarter.getEngine().getRooms().get(roomId);
            if (room == null) {
                return BodyWrapper.createBody(PushData.build("房间不存在", 100));
            }else{
                Player player = room.getPlayer(userId);
                if(player == null){
                    return BodyWrapper.createBody(PushData.build("玩家不存在", 100));
                }else{
                    handle(player);
                    if (room.getListener() != null){
                        room.getListener().onchange(player.getGameData().getHistory(),player.getId());
                    }
                }
            }
            return null;
        } else {
            return BodyWrapper.createBody(PushData.build("指令无效", 100));
        }
    }
}
