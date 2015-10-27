package com.senic.nuimo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;


public class NuimoMain {

  static final int WebSocketPort = 9999;
  static final int WebProxyPort = 8888;

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();
    JsonObject jsonOption = new JsonObject();
    jsonOption.put("WebSocketPort", WebSocketPort);
    jsonOption.put("WebProxyPort", WebProxyPort);
    DeploymentOptions option = new DeploymentOptions().setConfig(jsonOption);

    Verticle webSocketServer = new WebSocketServer();
    vertx.deployVerticle(webSocketServer, option);

    Verticle webProxyServer = new WebProxyServer();
    vertx.deployVerticle(webProxyServer, option);
  }

}
