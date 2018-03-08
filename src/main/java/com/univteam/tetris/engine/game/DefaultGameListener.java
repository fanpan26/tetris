package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.block.Block;
import com.univteam.tetris.engine.data.DataUtil;
import com.univteam.tetris.engine.data.HistoryData;
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
        MessageSender.sendSysMessage(roomId,String.format("系统：用户【%s】游戏结束。总得分：%d",player.getName(),player.getGameData().getTotalScore()));
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

    /**
     * 下一个block事件
     * */
    @Override
    public void createBlock(String groupId, String userId, Block nextBlock) {
        if (nextBlock == null) {
            return;
        }
        String pointString = DataUtil.transData(nextBlock.getPoints());
        MessageSender.sendNextBlockMessage(groupId,userId,pointString);
    }
}
