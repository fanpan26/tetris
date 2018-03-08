package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.block.Block;
import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.engine.room.Room;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 14:52
 * @Project com.univteam.tetris
 */
public interface GameListener {

    /**
     * 游戏结束
     * */
    void gameOver(Player player);

    /**
     * 游戏积分变化
     * */
    void scoreChange(Player player);

    /**
     * 创建方块事件
     * */
    void createBlock(String groupId, String userId, Block nextBlock);
}
