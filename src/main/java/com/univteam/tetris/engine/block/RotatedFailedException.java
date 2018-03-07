package com.univteam.tetris.engine.block;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/7 14:10
 * @Project com.univteam.tetris
 */
public class RotatedFailedException extends RuntimeException {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public RotatedFailedException(String message){
        this.message = message;
    }
}
