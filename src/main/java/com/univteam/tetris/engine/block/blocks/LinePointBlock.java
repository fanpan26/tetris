package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.block.RotatedFailedException;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.block.AbstractBlock;

/**
 *              口            口                     口
 *           口 口 口      口 口      口 口 口       口 口
 *                            口         口          口
 * @Author fyp
 * @Description 俄罗斯方框游戏：一个点一个横线形状的方块
 * @Date Created at 2018/3/5 15:50
 * @Project com.univteam.tetris
 */
public class LinePointBlock extends AbstractBlock {
    @Override
    protected void build(Point startPoint) {
        points[0] = startPoint;
        points[2] = startPoint.down();
        points[1] = points[2].left();
        points[3] = points[2].right();
    }

    @Override
    public boolean rotatable() {
        return true;
    }

    @Override
    public void doRotate() throws RotatedFailedException {
        switch (blockStatus){
            case UP:
                points[2] = points[0].down();
                points[1] = points[2].left();
                points[3] = points[2].down();

                blockStatus = BlockStatus.LEFT;
                break;
            case LEFT:
                points[0] = points[1];
                points[1] = points[0].right();
                points[2] = points[1].right();
                points[3] = points[1].down();

                blockStatus = BlockStatus.DOWN;
                break;
            case DOWN:
                points[0] = points[1].up();
                points[2] = points[1].right();
                points[3] = points[1].down();

                blockStatus = BlockStatus.RIGHT;
                break;
            case RIGHT:
                points[2] = points[0].down();
                points[1] = points[2].left();
                points[3] = points[2].right();

                blockStatus = BlockStatus.UP;
                break;
        }
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.UP;
    }
}
