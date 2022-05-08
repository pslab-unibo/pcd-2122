package pcd.lab09.actors.basic;

import akka.actor.typed.ActorSystem;
import pcd.lab09.actors.basic.PingerPongerProtocol.BootMsg;

public class TestPingerPonger {
  public static void main(String[] args) throws Exception  {

	  final ActorSystem<PingerPongerProtocol> pinger =
			    ActorSystem.create(PingerActor.create(), "pinger");

	  final ActorSystem<PingerPongerProtocol> ponger =
			    ActorSystem.create(PongerActor.create(), "ponger");

	  pinger.tell(new BootMsg(ponger));
  }
}
