package pcd.lab07.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.*;
import io.vertx.ext.web.handler.BodyHandler;

class WebService extends AbstractVerticle {

	private int numRequests;
	private int port;

	public WebService(int port) {
		numRequests = 0;
		this.port = port;
	}
	
	public void start() {
		log("Service initializing...");
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		router.route().handler(BodyHandler.create());

		router.get("/api/numRequests")
		.respond(request -> {	
			log("new request arrived: " + request.currentRoute().getPath());
			JsonObject reply = new JsonObject();
			reply.put("numRequests", numRequests);
			return request
				.response()
			    .putHeader("Content-Type", "application/json")
			    .end(reply.toString());
		});

		router.route(HttpMethod.GET, "/api/things/:thingId/state")
		.handler( request -> {
			log("new request arrived: " + request.currentRoute().getPath());

			JsonObject reply = new JsonObject();
			reply
			.put("id", request.pathParam("thingId"))
			.put("state", Math.random());			

			sendReply(request, reply);
		});
		
		
		router
		.route(HttpMethod.POST, "/api/task/inc")
		.handler(request -> {
			log("new request arrived: " + request.currentRoute().getPath());
			JsonObject msgReq = request.getBodyAsJson();
			double value = msgReq.getDouble("value");
			double result = value + 1;			
			JsonObject reply = new JsonObject();
			numRequests++;
			
			reply
			.put("numReq", numRequests)
			.put("result", result);				
			log("reply: " + reply.encodePrettily());

			sendReply(request, reply);
		  });

		server
		.requestHandler(router)
		.listen(port);
		
		log("Service ready - port: " + port);
	}

	private void sendReply(RoutingContext request, JsonObject reply) {
		HttpServerResponse response = request.response();
		response.putHeader("content-type", "application/json");
		response.end(reply.toString());
	}
	
	private  void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

public class Step8_WebService {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		AbstractVerticle myVerticle = new WebService(8081);
		vertx.deployVerticle(myVerticle);
	}
}
