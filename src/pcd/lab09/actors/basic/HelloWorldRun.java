package pcd.lab09.actors.basic;

import akka.actor.typed.ActorSystem;

public class HelloWorldRun {

	public static void main(String[] args) {
		final ActorSystem<HelloWorldMain.SayHello> system =
			    ActorSystem.create(HelloWorldMain.create(), "hello");

			system.tell(new HelloWorldMain.SayHello("World"));
			system.tell(new HelloWorldMain.SayHello("Akka"));	}
	
}
