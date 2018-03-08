package com.univteam.tetris.engine.player;

import cn.hutool.core.util.RandomUtil;
import org.tio.utils.SystemTimer;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/8 11:36
 * @Project com.univteam.tetris
 */
public class PlayerCreator {
    private static final String[] images = {"gonggong","huafei","huanghou","huangshang","meijiejie","xiaoniao","xiaopanzi","zhenhuan"};
    private static final String[] names = {"公公","华妃","皇后","皇上","梅姐姐","安陵容","小盘子","甄嬛"};

    /**
     * 随机创建一个用户
     * */
    public static Player create(String userId){
       int index =  RandomUtil.randomInt(0,7);
       Player player = new Player();
       player.setId(userId);
       player.setName(names[index]);
       player.setPhoto(String.format("%s%s%s","/images/photo/",images[index],".jpg"));
       return player;
    }
}
