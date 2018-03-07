package com.univteam.tetris.engine.game;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 15:23
 * @Project com.univteam.tetris
 */
public class ScoreIndicator {

    /**
     * 单行积分 10分
     * */
    private final static int SINGLE_COUNT_SCORE = 10;


    /**
     * 根据记分指标得分
     * */
    public static int getScore(int fullCount){
        //基础分
        int score = fullCount * SINGLE_COUNT_SCORE;
        //奖励分
        if (fullCount == 4){
            return score +  2 * SINGLE_COUNT_SCORE;
        }
        if (fullCount > 1){
            return score + 1 * SINGLE_COUNT_SCORE;
        }
        return score;
    }
}
