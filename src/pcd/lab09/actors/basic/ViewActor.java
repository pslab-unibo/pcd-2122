package pcd.lab09.actors.basic;
import akka.actor.AbstractActor;

/**
 *
 * This example is based on the previous Akka API 
 * 
 * @author aricci
 *
 */
public class ViewActor extends AbstractActor {
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(PressedMsg.class, msg -> {
			System.out.println("Pressed!");
		}).build();
	}
}
