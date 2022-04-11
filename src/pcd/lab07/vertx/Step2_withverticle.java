package pcd.lab07.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

class MyReactiveAgent extends AbstractVerticle {
	
	public void start() {
		log("started");
		
		FileSystem fs = getVertx().fileSystem();    		
		
		Future<Buffer> f1 = fs.readFile("build.gradle");

		f1.onComplete((AsyncResult<Buffer> res) -> {
			log("BUILD \n" + res.result().toString().substring(0,160));
		});
	
		fs
		.readFile("settings.gradle")
		.onComplete((AsyncResult<Buffer> res) -> {
			log("SETTINGS \n" + res.result().toString().substring(0,160));
		});
		
		try {
			Thread.sleep(3000);
		} catch (Exception ex) {};
		
		log("done");
	}

	private void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

public class Step2_withverticle {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		vertx.deployVerticle(new MyReactiveAgent());
	}
}

