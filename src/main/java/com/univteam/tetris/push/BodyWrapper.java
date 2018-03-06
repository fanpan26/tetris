package com.univteam.tetris.push;

import cn.hutool.json.JSONUtil;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsResponse;

import java.io.UnsupportedEncodingException;

/**
 * @Author fyp
 * @Description 俄罗斯方框游戏：
 * @Date Created at 2018/3/6 16:14
 * @Project com.univteam.tetris
 */
public class BodyWrapper {
    /**
     * 构造返回消息体
     * */
    public static WsResponse createBody(Object body) {
        WsResponse response = new WsResponse();
        if (body != null) {
            String json = JSONUtil.toJsonStr(body);
            try {
                byte[] bytes = json.getBytes("utf-8");
                response.setBody(bytes);
            } catch (UnsupportedEncodingException e) {
                response.setBody(null);
            }
            response.setWsBodyText(json);
            response.setWsBodyLength(response.getWsBodyText().length());
            response.setWsOpcode(Opcode.TEXT);
        }
        return response;
    }
}