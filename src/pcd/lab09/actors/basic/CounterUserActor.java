package pcd.lab09.actors.basic;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import pcd.lab09.actors.basic.CounterActor.CounterValueMsg;
import pcd.lab09.actors.basic.CounterActor.GetValueMsg;
import pcd.lab09.actors.basic.CounterActor.IncMsg;

public class CounterUserActor extends AbstractBehavior<CounterUserMsg> {

	private ActorRef<CounterMsg> counter;
	
	/* constructor called indirectly */

	private CounterUserActor(ActorContext<CounterUserMsg> context) {
		super(context);
	}

	@Override
	public Receive<CounterUserMsg> createReceive() {
		return newReceiveBuilder()
				.onMessage(StartMsg.class, this::onStartMsg)
				.onMessage(CounterValueMsg.class, this::onCounterValueMsg)
				.build();
		
	}

	private Behavior<CounterUserMsg> onStartMsg(StartMsg msg) {
		this.getContext().getLog().info("started");
		msg.counter.tell(new IncMsg());
		msg.counter.tell(new IncMsg());
		msg.counter.tell(new GetValueMsg(this.getContext().getSelf()));
		return this;
	}

	private Behavior<CounterUserMsg> onCounterValueMsg(CounterValueMsg msg){
		this.getContext().getLog().info("value: " + msg.value);
		return this;
	}
	
	static public class StartMsg implements CounterUserMsg {
		public final ActorRef<CounterMsg> counter;
		public StartMsg(ActorRef<CounterMsg> counter) {
			this.counter = counter;
		}
	}	
	
	/* public factory to create the actor */

	public static Behavior<CounterUserMsg> create() {
		return Behaviors.setup(CounterUserActor::new);
	}
}
