package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.AbstractBlock;
import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.point.Point;

/**
 *              口 口
 *              口 口
 *
 * @Author fyp
 * @Description 俄罗斯方框游戏：正方形方块
 * @Date Created at 2018/3/5 14:48
 * @Project com.univteam.tetris
 */
public class SquareBlock extends AbstractBlock {
    /**
     * 方块不支持旋转，旋转过后是自己
     * */
    @Override
    public boolean rotatable() {
        return false;
    }

    /**
     * 旋转
     * */
    @Override
    public void doRotate() {

    }

    /**
     * 正方形方块构造
     * 左上角为第一个，所以坐标如下
     * 1,1 1,2
     * 2,1 2,2
     * */
    @Override
    protected void build(Point startPoint) {
        points[0] = startPoint;
        points[1] = startPoint.right();
        points[2] = startPoint.down();
        points[3] = points[1].down();
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.NONE;
    }
}
