package com.univteam.tetris.engine.block.blocks;

import com.univteam.tetris.engine.block.BlockStatus;
import com.univteam.tetris.engine.block.RotatedFailedException;
import com.univteam.tetris.engine.point.Point;
import com.univteam.tetris.engine.block.AbstractBlock;

/**
 *                                   口
 *                  口 口 口 口       口
 *                                   口
 *                                   口
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/5 15:28
 * @Project com.univteam.tetris
 */
public class LineBlock extends AbstractBlock {

    /**
     * 方块不支持旋转，旋转过后是自己
     * */
    @Override
    public boolean rotatable() {
        return true;
    }

    /**
     * 旋转 以第二个为轴旋转
     * */
    @Override
    public void doRotate() throws RotatedFailedException {
        if (blockStatus == BlockStatus.HORIZONTAL){
            points[0] = points[1].up();
            points[2] = points[1].down();
            points[3] = points[2].down();
            blockStatus = BlockStatus.VERTICAL;
        }else{
            points[0] = points[1].left();
            points[2] = points[1].right();
            points[3] = points[2].right();
            blockStatus = BlockStatus.HORIZONTAL;
        }
    }

    /**
     * 线条方块构造
     * 最左侧为第一个，默认生成横的
     * 1,1 1,2 1,3 1,4
     * */
    @Override
    protected void build(Point startPoint) {
        blockStatus = BlockStatus.HORIZONTAL;

        points[0] = startPoint;
        points[1] = startPoint.right();
        points[2] = points[1].right();
        points[3] = points[2].right();
    }

    @Override
    protected BlockStatus getDefaultStatus() {
        return BlockStatus.HORIZONTAL;
    }

}
