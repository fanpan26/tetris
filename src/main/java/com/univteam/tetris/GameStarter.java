package com.univteam.tetris;

import com.univteam.tetris.engine.game.GameEngine;
import com.univteam.tetris.pages.TetrisHttpServerStarter;
import com.univteam.tetris.push.TetrisWsServerStarter;

/**
 * @Author fyp
 * @Description
 * @Date Created at 2018/3/5 12:02
 * @Project com.univteam.pushserver
 */
public class GameStarter {
    private static GameEngine engine ;
    private static TetrisWsServerStarter wsServerStarter;
    public static GameEngine getEngine(){
        return engine;
    }
    public static TetrisWsServerStarter getWsServerStarter(){
        return wsServerStarter;
    }

    public static void main(String[] args) throws Exception{
        engine = new GameEngine();
        engine.start();

        new TetrisHttpServerStarter(8885).start();
        System.out.println("TetrisHttpServer start.... http://127.0.0.1:8885/");
        wsServerStarter = new TetrisWsServerStarter(8886);
        wsServerStarter.start();
        System.out.println("TetrisWsServer start....ws://127.0.0.1:8886/");


    }
}
