package com.vma;


import com.vma.assist.wraps.LogWrap;
import com.vma.boost.rbac.EnableVmaBoostRbac;
import com.vma.core.EnableVmaCore;
import com.vma.mybatis.EnableVmaMybatis;
import com.vma.redis.EnableVmaRedis;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;

/**
 * Spring Boot应用类
 *
 * @author hhd
 */
@SpringBootApplication
@EnableVmaBoostRbac
@EnableVmaCore
@EnableVmaRedis
@EnableVmaMybatis
@MapperScan("com.vma.**.dao")
public class Application {

    private static Logger logger = LogWrap.getLogger(Application.class);

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        LogWrap.getLogger(Application.class).info("app-business start completion");
        for (int i = 0; i < 20; i++) {
            SocketTest socketTest = new SocketTest(i);
            socketTest.run();
        }
    }

    private static int count = 1;
    private static int failCount = 1;

    /**
     *
     */
    static class SocketTest {

        private Socket mSocket;
        private long startTime;
        private int i;

        /**
         * @param i
         */
        SocketTest(int i) {
            this.i = i;
        }

        /**
         *
         */
        public void run() {
            try {
                mSocket = IO.socket("http://127.0.0.1:57001/pipe");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length > 0) {
                        logger.info(Socket.EVENT_CONNECT_ERROR + "  " + args[0] + "  i:" + i);
                    } else {
                        logger.info(Socket.EVENT_CONNECT_ERROR + "  i:" + i);
                    }
                }
            }).on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    if (args.length > 0) {
                        logger.info(Socket.EVENT_CONNECT + args[0].toString());
                    }
                    logger.info((count++) + "   connect: " + mSocket.connected() + "，cost time：" + (System.currentTimeMillis()
                            - startTime) + "  i:" + i);
                    mSocket.emit("handle", "AAA");
                }

            }).on("onConnect", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length > 0) {
                        logger.info("onConnect: " + args[0]);
                    } else {
                        logger.info("onConnect: ");
                    }
                }
            }).on("getMsg", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    if (args.length > 0) {
                        logger.info("getMsg" + args[0]);
                    } else {
                        logger.info("getMsg");
                    }
//          mSocket.disconnect();
                }

            }).on("ClientGetOrNotMessage", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length > 0) {
                        logger.info("ClientGetOrNotMessage" + args[0]);
                    } else {
                        logger.info("ClientGetOrNotMessage: ");
                    }
                }
            })
                    .on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                        @Override
                        public void call(Object... args) {
                            if (args.length > 0) {
                                logger.info("disconnect  ---- " + args[0]);
                            } else {
                                logger.info("disconnect: ");
                            }
                            logger.info((failCount++) + "，cost time：" + (System.currentTimeMillis() - startTime) + "   i:" + i);
                        }

                    });
            mSocket.io().timeout(30000L);
            mSocket.connect();
            startTime = System.currentTimeMillis();

//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          try {
//            Thread.sleep(3000);
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
//          if (mSocket.connected()) {
//            L.d(TAG, "run: ");
//            mSocket.emit("handle", "AAA");
//          }
//        }
//      }).start();
        }
    }
}



