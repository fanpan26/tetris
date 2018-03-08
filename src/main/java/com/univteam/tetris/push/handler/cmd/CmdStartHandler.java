package com.univteam.tetris.push.handler.cmd;

import com.univteam.tetris.engine.player.Player;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/8 17:14
 * @Project com.univteam.tetris
 */
public class CmdStartHandler extends CmdOperateHandler {
    @Override
    public void handle(Player player) {
        player.getGameData().prepare();
    }
}
