package com.univteam.tetris.pages;

import org.tio.http.common.HttpConfig;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;

import java.io.IOException;

/**
 * @Author fyp
 * @Description
 * @Date Created at 2018/3/5 12:03
 * @Project com.univteam.pushserver
 */
public class TetrisHttpServerStarter {
    private int port;

    public TetrisHttpServerStarter(int port){
        this.port = port;
    }
    /**
     * 启动page服务器
     * */
    public void start() throws IOException{
        HttpConfig httpConfig = new HttpConfig(port,null,null,null);
        httpConfig.setPageRoot("classes/page");
        //httpConfig.setPageRoot("classpath:page");
        HttpServerStarter starter = new HttpServerStarter(httpConfig,new DefaultHttpRequestHandler(httpConfig,null));
        starter.start();
    }
}
