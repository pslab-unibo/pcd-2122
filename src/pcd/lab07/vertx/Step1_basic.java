package pcd.lab07.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class Step1_basic {

	public static void main(String[] args) {
		
		Vertx  vertx = Vertx.vertx();

		FileSystem fs = vertx.fileSystem();    		

		log("started");
		
		/* version 4.0 - future (promise) based API */
		
		Future<Buffer> fut = fs.readFile("build.gradle");
		fut.onComplete((AsyncResult<Buffer> res) -> {
			log("BUILD \n" + res.result().toString().substring(0,160));
		});

		/* old versions - callback style, still supported */
		
		fs.readFile("settings.gradle", (AsyncResult<Buffer> res) -> {
			log("SETTINGS \n" + res.result().toString().substring(0,160));
		});
		
		log("done");
	}
	
	private static void log(String msg) {
		System.out.println("" + Thread.currentThread() + " " + msg);
	}
}

