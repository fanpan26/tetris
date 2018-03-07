package com.univteam.tetris.push.handler.cmd;

import com.univteam.tetris.GameStarter;
import com.univteam.tetris.engine.data.PushData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.room.Room;
import com.univteam.tetris.push.BodyWrapper;

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
    public Object handle(String cmd) {
        String[] cmds = cmd.split(":");
        String roomId;
        if (cmds.length == 3) {
             roomId = cmds[2];
        }else{
            roomId = null;
        }
        Room room = GameStarter.getEngine().getRooms().get(roomId);
        if (room == null) {
            return BodyWrapper.createBody(PushData.build("无效的房间号", 0));
        } else {
            Player player = new Player();
            player.setId("10000");
            player.setName("test");
            player.setPhoto("");
            player.setRoom(room);

            boolean res = room.addPlayer(player);
            return BodyWrapper.createBody(PushData.build(res ? "加入房间成功" : "游戏已经开始，观战中...", 0));
        }
    }
}
