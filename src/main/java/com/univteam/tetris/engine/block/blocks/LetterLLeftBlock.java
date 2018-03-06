package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.block.AbstractBlock;

/**
 *                    口                    口 口
 *                    口     口             口         口 口 口
 *                 口 口     口 口 口       口               口
 *
 * @Author fyp
 * @Description 俄罗斯方框游戏：L形状的方块,L向左
 * @Date Created at 2018/3/5 15:48
 * @Project com.univteam.tetris
 */
public class LetterLLeftBlock extends AbstractBlock {

    @Override
    protected void build(Point startPoint) {
        points[0] = startPoint;
        points[1] = startPoint.down();
        points[2] = points[1].down();
        points[3] = points[2].left();
    }

    @Override
    public boolean rotatable() {
        return true;
    }

    /**
     * 根据当前的方向进行转换
     * */
    @Override
    public void doRotate() {
        switch(blockStatus){
            case LEFT:
                points[0] = points[0].up();
                points[1] = points[0].down();
                points[2] = points[1].right();
                points[3] = points[2].right();
                blockStatus = BlockStatus.UP;
                break;
            case UP:
                points[1] = points[0].right();
                points[2] = points[1].down();
                points[3] = points[2].down();
                blockStatus = BlockStatus.RIGHT;
                break;
            case RIGHT:
                points[0] = points[3];
                points[1] = points[0].right();
                points[2] = points[1].right();
                points[3] = points[2].down();
                blockStatus = BlockStatus.DOWN;
                break;
            case DOWN:
                points[0] = points[2].up();
                points[1] = points[0].down();
                points[2] = points[1].down();
                points[3] = points[2].left();
                blockStatus = BlockStatus.LEFT;
                break;
        }
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.LEFT;
    }
}
