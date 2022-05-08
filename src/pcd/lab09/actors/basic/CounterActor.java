package pcd.lab09.actors.basic;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


public class CounterActor extends AbstractBehavior<CounterMsg> {

	private int count;
	
	/* constructor called indirectly */

	private CounterActor(ActorContext<CounterMsg> context) {
		super(context);
		count = 0;
	}

	@Override
	public Receive<CounterMsg> createReceive() {
		return newReceiveBuilder()
				.onMessage(IncMsg.class, this::onIncMsg)
				.onMessage(GetValueMsg.class, this::onGetValueMsg)
				.build();
		
	}

	private Behavior<CounterMsg> onIncMsg(IncMsg msg) {
		this.getContext().getLog().info("inc");
		count++;
		return this;
	}

	private Behavior<CounterMsg> onGetValueMsg(GetValueMsg msg) {
		this.getContext().getLog().info("getValue");
		msg.replyTo.tell(new CounterValueMsg(count));
		return this;
	}

	/* messages */
	
	static public class IncMsg implements CounterMsg {}
	
	static public class GetValueMsg implements CounterMsg {
		public final ActorRef replyTo;
		public GetValueMsg(ActorRef replyTo) {
			this.replyTo = replyTo;
		}
	}

	static public class CounterValueMsg implements CounterUserMsg {
		public final int value;
		public CounterValueMsg(int value) {
			this.value = value;
		}
	}

	
	/* public factory to create the actor */

	public static Behavior<CounterMsg> create() {
		return Behaviors.setup(CounterActor::new);
	}
}
