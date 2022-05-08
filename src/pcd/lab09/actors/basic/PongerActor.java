package pcd.lab09.actors.basic;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import pcd.lab09.actors.basic.PingerPongerProtocol.*;

public class PongerActor extends AbstractBehavior<PingerPongerProtocol> {

	private PongerActor(ActorContext<PingerPongerProtocol> context) {
		super(context);
	}

	@Override
	public Receive<PingerPongerProtocol> createReceive() {
		return newReceiveBuilder()
				.onMessage(PingMsg.class, this::onPingMsg)
				.build();
		
	}

	private Behavior<PingerPongerProtocol> onPingMsg(PingMsg msg) {
		log("got ping " + msg.count + " => pong " + (msg.count + 1));
		msg.pinger.tell(new PongMsg(msg.count + 1, this.getContext().getSelf()));
		return this;
	}

	
	/* public factory to create the actor */

	public static Behavior<PingerPongerProtocol> create() {
		return Behaviors.setup(PongerActor::new);
	}
	
	private void log(String msg) {
		System.out.println("[CounterUserActor] " + msg);
	}
	
}
