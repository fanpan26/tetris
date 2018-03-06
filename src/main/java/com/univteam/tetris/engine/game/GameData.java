package com.univteam.tetris.engine.game;

import com.univteam.tetris.engine.block.Block;
import com.univteam.tetris.engine.block.BlockUtil;
import com.univteam.tetris.engine.data.HistoryData;
import com.univteam.tetris.engine.block.blocks.LetterLLeftBlock;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.point.PointCache;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 16:42
 * @Project com.univteam.tetris
 */
public class GameData {

    public static GameData DEFAULT = new GameData(GameStatus.WAITING);

    private GameStatus status;
    private Block currentBlock;
    private Map<String,Point> stopedPoints = new HashMap<>(1000);

    public  GameData (GameStatus status) {
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    /**
     * 游戏开始
     * */
    public void start(){
        currentBlock = BlockUtil.createBlock();
        this.status = GameStatus.STARTED;
    }

    /**
     * 游戏下一步
     * */
    public void refresh() {
        switch (status){
            case WAITING:
            case STOPED:
                start();
                break;
            case STARTED:
                //如果到了停止状态，就先停止
                currentBlock.next();
                if(shouldStop()){
                    if (stopedPoints.size()>0) {
                        //还原到上一部
                        currentBlock.prev();
                    }
                    //停止移动
                    currentBlock.stop();
                    for (Point point : currentBlock.getPoints()) {
                        stopedPoints.put(PointCache.getKey(point.getX(),point.getY()),point);
                    }
                    //状态设置为停止
                    status =  GameStatus.STOPED;
                    //TODO 是否需要得分
                }
                break;
            case OVER:
                break;
        }
    }

    /**
     * 获取历史数据
     * */
    public HistoryData getHistory(){
        return currentBlock.getHistory();
    }

    public enum GameStatus{
        WAITING,
        STARTED,
        STOPED,
        OVER
    }

    private boolean shouldStop(){
        if (stopedPoints.size() > 0) {
            //判断下一步的点是否在已经停止的点中存在
            for (Point point : currentBlock.getPoints()) {
                if (stopedPoints.containsKey(PointCache.getKey(point.getX(),point.getY()))) {
                    return true;
                }
            }
        }
        //最后判断是否到底部
        return GameMap.onBottom(currentBlock);
    }
}
