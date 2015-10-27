package com.senic.nuimo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.net.Inet4Address;

/**
 * Created by je on 8/19/15.
 */
public class WebProxyServer  extends AbstractVerticle {

    private Logger logger;

    // Called when verticle is deployed
    public void start() {
        logger = LoggerFactory.getLogger("WebProxyServer");
        int port = config().getInteger("WebProxyPort");

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route("/api").handler(routingContext -> {

            MultiMap params = routingContext.request().params();
            if(params.get("webSocketAddress") != null){
                try{
                    routingContext.response().end(Inet4Address.getLocalHost().getHostAddress().toString() + ":" + config().getInteger("WebSocketPort"));
                }catch (Exception e){
                    //NOTHING TO DO
                }finally {
                    return;
                }
            }else if(params.get("send") != null) {
                String message = params.get("send");
                if(message != null && message.length() > 0){
                    EventBus eb = vertx.eventBus();
                    eb.publish("message", message);
                    routingContext.response().end("Send - "+message);
                    return;
                }
            }
            routingContext.response().end("WrongQuery");
        });
        router.route().handler(StaticHandler.create().setCachingEnabled(false));

        server.requestHandler(router::accept).listen(port);

        try{
            logger.info("WebProxyServer is now listening : " + Inet4Address.getLocalHost().getHostAddress().toString() + ":" + port );
        }catch (Exception e){
            //NOTHING TO DO
        }
    }

    // Optional - called when verticle is undeployed
    public void stop() {
    }
}
