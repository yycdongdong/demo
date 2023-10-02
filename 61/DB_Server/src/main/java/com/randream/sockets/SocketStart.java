package com.randream.sockets;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class SocketStart {
    private ExecutorService executorService;

    public SocketStart() throws IOException {
        executorService = new ThreadPoolExecutor(100,//核心线程数量
                100,//最大线程数量
                1,//空闲线程最大存活时间
                TimeUnit.MILLISECONDS,//
                new SynchronousQueue<>(),//任务队列
                Executors.defaultThreadFactory(),//线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy());//绕过线程池直接调用run方法

        ServerSocket ss = new ServerSocket(9999);
        while (true) {
            Socket accept = ss.accept();
            log.info("ServerSocket收到请求");
            CompletableFuture.runAsync(() -> {
                try {
                    SocketThread socketThread = new SocketThread(accept);
                    socketThread.preStart();
                } catch (Exception e1) {
                    log.error("ServerSocket报错", e1);
                    try {
                        //如果报错，说明这个socket连接无法继续处理了，关闭（后续客户端可以重新建立一个socket连接）
                        accept.close();
                    } catch (Exception e2) {
                        log.error("报错后，关闭socket失败");
                    }
                }
            }, executorService);
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
