package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.AbstractBlock;
import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.block.RotatedFailedException;
import com.univteam.tetris.engine.point.Point;

/**
 *                    口                         口 口
 *                    口               口           口       口 口 口
 *                    口 口      口 口 口           口       口
 * @Author fyp
 * @Description 俄罗斯方框游戏：L形状的方块,L向右
 * @Date Created at 2018/3/5 15:57
 * @Project com.univteam.tetris
 */
public class LetterLRightBlock extends AbstractBlock{

    @Override
    public boolean rotatable() {
        return true;
    }

    @Override
    protected void build(Point startPoint) {
        points[0] = startPoint;
        points[1] = startPoint.down();
        points[2] = points[1].down();
        points[3] = points[2].right();
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.RIGHT;
    }

    @Override
    public void doRotate() throws RotatedFailedException {
        switch (blockStatus){
            case RIGHT:
                points[0] = points[3].up();
                points[3] = points[0].down();
                points[2] = points[3].left();
                points[1] = points[2].left();

                blockStatus = BlockStatus.UP;
                break;
            case UP:
                points[0] = points[0].left();
                points[1] = points[0].right();
                points[2] = points[1].down();
                points[3] = points[2].down();

                blockStatus = BlockStatus.LEFT;
                break;
            case LEFT:
                points[0] = points[2].left();
                points[1] = points[0].right();
                points[2] = points[1].right();
                points[3] = points[0].down();

                blockStatus = BlockStatus.DOWN;
                break;
            case DOWN:
                points[0] = points[0].up();
                points[1] = points[0].down();
                points[2] = points[1].down();
                points[3] = points[2].right();

                blockStatus = BlockStatus.RIGHT;
                break;
        }

    }
}
