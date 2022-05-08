package pcd.lab09.actors.basic;

import akka.actor.typed.ActorSystem;
import pcd.lab09.actors.basic.CounterUserActor.StartMsg;

public class TestCounter {
  public static void main(String[] args) throws Exception  {

	  final ActorSystem<CounterMsg> counter =
			    ActorSystem.create(CounterActor.create(), "myCounter");

	  final ActorSystem<CounterUserMsg> counterUser =
			    ActorSystem.create(CounterUserActor.create(), "myCounterUser");

	  counterUser.tell(new StartMsg(counter));
  }
}
