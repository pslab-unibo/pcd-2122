package pcd.lab09.actors.basic;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


public class ActorWithMultipleBehaviors extends AbstractBehavior<ActorWithMultipleBehaviorsBaseMsg> {

	public static class MsgZero implements ActorWithMultipleBehaviorsBaseMsg {}
	public static class MsgOne implements ActorWithMultipleBehaviorsBaseMsg {}
	public static class MsgTwo implements ActorWithMultipleBehaviorsBaseMsg {}

	private int initialState;
	
	private ActorWithMultipleBehaviors(ActorContext<ActorWithMultipleBehaviorsBaseMsg> context, int initialState) {
		super(context);
		this.initialState = initialState;
	}
	
	public static Behavior<ActorWithMultipleBehaviorsBaseMsg> create(int initialState) {
		return Behaviors.setup(context -> new ActorWithMultipleBehaviors(context, initialState));
	}


	@Override
	public Receive<ActorWithMultipleBehaviorsBaseMsg> createReceive() {
		return newReceiveBuilder()
				.onMessage(MsgZero.class,this::onMsgZero)
				.build();
	}

	private Behavior<ActorWithMultipleBehaviorsBaseMsg> onMsgZero(MsgZero msg) {
		this.getContext().getLog().info("msgZero - state: " + initialState);		
		return Behaviors.setup(context -> new BehaviourA(context, initialState + 1));
	}

	/* behaviour A */
	
	class BehaviourA extends AbstractBehavior<ActorWithMultipleBehaviorsBaseMsg> {

		private int localState;
		
		private BehaviourA(ActorContext<ActorWithMultipleBehaviorsBaseMsg> context, int localState) {
			super(context);
			this.localState = localState;
		}

		@Override
		public Receive<ActorWithMultipleBehaviorsBaseMsg> createReceive() {
			return newReceiveBuilder()
					.onMessage(MsgOne.class, this::onMsgOne)
					.build();
		}	

		private Behavior<ActorWithMultipleBehaviorsBaseMsg> onMsgOne(MsgOne msg) {
			this.getContext().getLog().info("msgOne - state: " + localState);		
			return Behaviors.setup(context -> new BehaviourB(context, localState + 1));
		}
	}

	/* behaviour B */
	
	class BehaviourB extends AbstractBehavior<ActorWithMultipleBehaviorsBaseMsg> {

		private int localState;

		private BehaviourB(ActorContext<ActorWithMultipleBehaviorsBaseMsg> context, int localState) {
			super(context);
			this.localState = localState;
		}

		@Override
		public Receive<ActorWithMultipleBehaviorsBaseMsg> createReceive() {
			return newReceiveBuilder()
					.onMessage(MsgTwo.class, this::onMsgTwo)
					.build();
		}	

		private Behavior<ActorWithMultipleBehaviorsBaseMsg> onMsgTwo(MsgTwo msg) {
			this.getContext().getLog().info("msgTwo - state: " + localState);		
			return Behaviors.stopped();
		}
	}
	
	
}
