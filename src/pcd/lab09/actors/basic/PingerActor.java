package pcd.lab09.actors.basic;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import pcd.lab09.actors.basic.PingerPongerProtocol.*;

public class PingerActor extends AbstractBehavior<PingerPongerProtocol> {

	/* constructor called indirectly */

	private PingerActor(ActorContext<PingerPongerProtocol> context) {
		super(context);
	}

	@Override
	public Receive<PingerPongerProtocol> createReceive() {
		return newReceiveBuilder()
				.onMessage(BootMsg.class, this::onBootMsg)
				.onMessage(PongMsg.class, this::onPongMsg)
				.build();
		
	}

	private Behavior<PingerPongerProtocol> onPongMsg(PongMsg msg) {
		log("got pong " + msg.count + " => ping " + (msg.count + 1));
		msg.ponger.tell(new PingMsg(msg.count + 1, this.getContext().getSelf()));
		return this;
	}

	private Behavior<PingerPongerProtocol> onBootMsg(BootMsg msg) {
		log("booting.");
		msg.ponger.tell(new PingMsg(0, this.getContext().getSelf()));
		return this;
	}

	/* public factory to create the actor */

	public static Behavior<PingerPongerProtocol> create() {
		return Behaviors.setup(PingerActor::new);
	}
	
	private void log(String msg) {
		System.out.println("[CounterUserActor] " + msg);
	}
	
}
