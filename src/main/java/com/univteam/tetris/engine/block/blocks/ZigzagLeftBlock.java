package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.block.AbstractBlock;

/**
 *               口
 *               口 口       口 口
 *                  口    口 口
 *
 * @Author fyp
 * @Description 俄罗斯方框游戏：之字形状的方块 ，开始节点在左边
 * @Date Created at 2018/3/5 15:52
 * @Project com.univteam.tetris
 */
public class ZigzagLeftBlock extends AbstractBlock {

    @Override
    protected void build(Point startPoint) {
        points[0] = startPoint;
        points[1] = startPoint.down();
        points[2] = points[1].right();
        points[3] = points[2].down();
    }

    @Override
    public boolean rotatable() {
        return true;
    }

    @Override
    public void doRotate() {
        switch (blockStatus){
            case VERTICAL:
                points[0] = points[2];
                points[1] = points[0].right();
                points[3] = points[0].down();
                points[2] = points[3].left();

                blockStatus = BlockStatus.HORIZONTAL;
                break;
            case HORIZONTAL:
                points[1] = points[0].left();
                points[0]= points[1].up();
                points[2]= points[1].right();
                points[3]=points[2].down();

                blockStatus = BlockStatus.VERTICAL;
                break;
        }
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.VERTICAL;
    }
}
