package com.senic.nuimo;

import com.sun.corba.se.spi.activation.Server;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.net.Inet4Address;
import java.util.ArrayList;

/**
 * Created by je on 8/19/15.
 */
public class WebSocketServer extends AbstractVerticle {

    private Logger logger;
    private ArrayList<ServerWebSocket> webSockets = new ArrayList<>();

    // Called when verticle is deployed
    public void start() {
        logger = LoggerFactory.getLogger("WebSocketServer");
        logger.info("Start WebSocket Server");

        int port = config().getInteger("WebSocketPort");

        HttpServer server = vertx.createHttpServer();
        server.websocketHandler( webSocket -> {
            webSockets.add(webSocket);
            logger.info("New WebSocket - "+webSocket.remoteAddress());
            logger.info("Connected Websockets - " + webSockets.size());
            webSocket.writeFinalTextFrame("Connected");
            webSocket.handler(buffer -> {
               logger.info("["+webSocket.remoteAddress()+"]" + buffer);
            });
            webSocket.closeHandler(Void -> {
                logger.info("Closed WebSocket - " + webSocket.remoteAddress());
                webSockets.remove(webSocket);
                logger.info("Connected Websockets - " + webSockets.size());
            });
        });
        server.listen(port);

        EventBus eb = vertx.eventBus();
        eb.consumer("message", message -> {
            logger.info("Message : "+message.body().toString());
            for (ServerWebSocket socket : webSockets){
                socket.writeFinalTextFrame(message.body().toString());
            }
        });


        try{
            logger.info("WebSocketServer is now listening : " + Inet4Address.getLocalHost().getHostAddress().toString() + ":" + port );
        }catch (Exception e){
            //NOTHING TO DO
        }
    }

    // Optional - called when verticle is undeployed
    public void stop() {
    }

}
