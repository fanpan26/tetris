package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.player.Player;
import com.univteam.tetris.push.MessageSender;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 14:55
 * @Project com.univteam.tetris
 */
public class DefaultGameListener implements GameListener {

    /**
     * 游戏结束事件
     * */
    @Override
    public void gameOver(Player player) {
        String roomId = player.getRoom().getGroupId();
        String playerId = player.getId();

        MessageSender.sendGameOverMessage(roomId,playerId);
    }

    /**
     * 积分变化事件
     * */
    @Override
    public void scoreChange(Player player){
        String roomId = player.getRoom().getGroupId();
        String playerId = player.getId();
        int totalScore = player.getGameData().getTotalScore();

        MessageSender.sendUserScoreMessage(roomId,playerId,totalScore);
    }
}
