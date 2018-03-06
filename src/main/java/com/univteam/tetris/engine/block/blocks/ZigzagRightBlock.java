package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.block.AbstractBlock;

/**
 *                  口
 *               口 口    口 口
 *               口          口 口
 *
 * @Author fyp
 * @Description 俄罗斯方框游戏：之字形方块，开始节点在右边
 * @Date Created at 2018/3/5 15:56
 * @Project com.univteam.tetris
 */
public class ZigzagRightBlock extends AbstractBlock {
    @Override
    protected void build(Point startPoint) {
        points[0] = startPoint;
        points[2] = points[0].down();
        points[1] = points[2].left();
        points[3] = points[1].down();
    }

    @Override
    public boolean rotatable() {
        return true;
    }

    @Override
    public void doRotate() {
        switch (blockStatus){
            case VERTICAL:
                points[0] = points[1].left();
                points[2] = points[1].down();
                points[3] = points[2].right();

                blockStatus = BlockStatus.HORIZONTAL;
                break;
            case HORIZONTAL:
                points [2] = points[1].right();
                points[0] = points[2].up();
                points[3] = points[1].down();

                blockStatus = BlockStatus.HORIZONTAL;
                break;

        }
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.VERTICAL;
    }
}
