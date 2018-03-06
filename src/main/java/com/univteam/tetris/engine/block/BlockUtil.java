package com.univteam.tetris.engine.block;

import cn.hutool.core.util.RandomUtil;
import com.univteam.tetris.engine.block.blocks.*;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 18:38
 * @Project com.univteam.tetris
 */
public class BlockUtil {
    public static Block createBlock(){
        int random = RandomUtil.randomInt(1,7);
        int rotateTime = RandomUtil.randomInt(1,2);
        Block block;
        switch (random) {
            case 1:
                block = new LetterLLeftBlock();
                break;
            case 2:
                block = new LetterLRightBlock();
                break;
            case 3:
                block = new LineBlock();
                break;
            case 4:
                block = new LinePointBlock();
                break;
            case 5:
                block = new SquareBlock();
                break;
            case 6:
                block = new ZigzagLeftBlock();
                break;
            case 7:
                block = new ZigzagRightBlock();
                break;
            default:
                block = new LineBlock();
                break;
        }
       // for (int i=0;i<rotateTime;i++) {
        //    block.rotate();
        //}
        return block;
    }
}
