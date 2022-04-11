package pcd.lab07.vertx;

import java.util.List;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

class TestChain extends AbstractVerticle {
	
	public void start() {
		FileSystem fs = vertx.fileSystem();    		

		Future<Buffer> f1 = fs.readFile("build.gradle");
		
		Future<Buffer> f2 = f1.compose((Buffer buf) -> {
			log("1 - BUILD \n" + buf.toString().substring(0,160));
			return fs.readFile("settings.gradle");
		});

		Future<List<String>> f3 = f2.compose((Buffer buf) -> {
			log("2 - SETTINGS \n" + buf.toString().substring(0,160));
			return fs.readDir("src");
		});
		
		f3.onComplete((AsyncResult<List<String>> list) -> {
			log("3 - DIR: " + list.result().size());
		});
				
	}

	private void log(String msg) {
		System.out.println("[REACTIVE AGENT] " + msg);
	}
}

public class Step3_chaining {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		vertx.deployVerticle(new TestChain());
	}
}

