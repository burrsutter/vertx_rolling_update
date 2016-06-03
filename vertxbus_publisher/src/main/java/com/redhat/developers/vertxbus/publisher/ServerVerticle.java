package com.redhat.developers.vertxbus.publisher;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

// A publisher does not require a Web API
// but we are adding one for the OpenShift readinessProbe

import io.vertx.ext.web.Router;
// import io.vertx.ext.web.handler.StaticHandler;
// import io.vertx.ext.web.handler.sockjs.BridgeOptions;
// import io.vertx.ext.web.handler.sockjs.PermittedOptions;
// import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class ServerVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    String hostname = System.getenv().getOrDefault("HOSTNAME", "unknown");
    
    Router router = Router.router(vertx);
    
    // Start the web server and tell it to use the router to handle requests.
    vertx.createHttpServer().requestHandler(router::accept).listen(8080);

    // Health Check
    router.get("/api/health").handler(ctx -> {
        // ? need to see if the eventbus is ready to rock (clustered)
        ctx.response().end("I'm ok");
    });

    // Now get chatty on the EventBus
    EventBus eb = vertx.eventBus();
    
    vertx.setPeriodic(1000l, t -> {
      // Create a timestamp string
      String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
      System.out.println(hostname + " sending " + timestamp);
      eb.send("servertopic1", new JsonObject().put("now", hostname + "*-* " + timestamp));
    });
    
  }
}