package pcd.lab07.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

class SimpleServer extends AbstractVerticle {

	private int numRequests;
	private int port;

	public SimpleServer(int port) {
		numRequests = 0;
		this.port = port;
	}
	
	public void start() {
		vertx.createHttpServer().requestHandler(req -> {
			numRequests++;
			
			String fileName = req.path().substring(1);
			log("request " + numRequests + " arrived for file: " + fileName);

			this.getVertx().fileSystem().readFile(fileName)
			.onComplete(result -> {
				log("result ready");
				if (result.succeeded()) {
					log(result.result().toString());
					req.response().putHeader("content-type", "text/plain").end(result.result().toString());
				} else {
					log("Oh oh ..." + result.cause());
					req.response().putHeader("content-type", "text/plain").end("File not found");
				}
			});
			
		}).listen(port);
	}

	private  void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}

}

public class Step7_SimpleServer {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		SimpleServer myVerticle = new SimpleServer(8081);
		vertx.deployVerticle(myVerticle);
	}
}
