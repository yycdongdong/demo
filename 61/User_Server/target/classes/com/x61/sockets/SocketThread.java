package com.x61.sockets;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

@Slf4j
public class SocketThread {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    //第一个方法，用来循环监听inputStream
    public void preStart() throws IOException, ClassNotFoundException, InterruptedException {
        while (true) {
            //处理
            start();

            //服务器不关闭socket，客户端关闭即可
            //socket.close();

            if (socket == null || socket.isClosed()) {
                log.info("socket为null或被关闭，停止循环");
                break;
            }
        }
    }

    //第二个方法，如果有消息就处理；如果没有消息，就sleep，然后返回
    private void start() throws IOException, ClassNotFoundException, InterruptedException {
        if (null == inputStream || socket.isInputShutdown()) {
            inputStream = socket.getInputStream();
        }
        int available = inputStream.available();
        //如果输入流不可用，说明本次循环没有收到消息，返回
        if (0 == available) {
            Thread.sleep(500);
            return;
        }

        if (null == outputStream || socket.isOutputShutdown()) {
            outputStream = socket.getOutputStream();
        }
        //得到消息
        JSONObject receive = receive(inputStream);
        //进行处理
        boolean deal = deal(receive);
        //返回响应报文
        backMsg(outputStream, deal, receive);
    }

    //从流中获取消息，封装成JSONObject对象
    private JSONObject receive(InputStream input) throws IOException {
        //消息格式为字节数组，type(1位)+datalength(4位)+data(datalength中指明的位数)
        byte t[] = new byte[1];
        byte dl[] = new byte[4];
        //从流中读取1位，保存到t中
        input.read(t, 0, 1);
        char type = (char) t[0];

        //从流中读取4位
        input.read(dl, 0, 4);
        ByteBuffer dlb = ByteBuffer.wrap(dl);
        int datalength = dlb.getInt();

        //准备获得data
        byte[] db = new byte[datalength];
        input.read(db, 0, datalength);
        String dataStr = new String(db, "utf-8");
        JSONObject data = JSONObject.parseObject(dataStr);

        //转为JSONObject对象，并返回
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("datalength", datalength);
        json.put("data", data);
        return json;
    }

    //处理逻辑，主要内容省略
    private boolean deal(JSONObject json) {
        //标志位，如果处理成功，再改为true
        boolean bool = false;
        //省略处理逻辑......
        return bool;
    }

    //返回响应报文
    private void backMsg(OutputStream outputStream, boolean bool, JSONObject receive) throws IOException {
        JSONObject backJson = new JSONObject();
        if (bool) {
            backJson.put("status", "1");
        } else {
            backJson.put("status", "-1");
        }
        //   backJson.put("dataId",receive.get("data").get("id"));

        byte[] backJsonByte = backJson.toString().getBytes();
        //规定，返回的byte数组格式为，json长度(int格式，占4位)+json的byte数组
        int totalLength = 4 + backJsonByte.length;
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        buffer.putInt(backJsonByte.length);
        buffer.put(backJsonByte);
        outputStream.write(buffer.array());
        outputStream.flush();

    }
}
