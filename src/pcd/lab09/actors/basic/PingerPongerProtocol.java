package pcd.lab09.actors.basic;

import akka.actor.typed.ActorRef;

public interface PingerPongerProtocol {

	static public class PongMsg implements PingerPongerProtocol {
		public final long count;
		public final ActorRef<PingerPongerProtocol> ponger;
		public PongMsg(long count, ActorRef<PingerPongerProtocol> ponger) {
			this.count = count;
			this.ponger = ponger;
		}
	}	
	
	static public class PingMsg implements PingerPongerProtocol {
		public final long count;
		public final ActorRef<PingerPongerProtocol> pinger;
		public PingMsg(long count, ActorRef<PingerPongerProtocol> pinger) {
			this.count = count;
			this.pinger = pinger;
		}
	}	

	static public class BootMsg implements PingerPongerProtocol {
		public final ActorRef<PingerPongerProtocol> ponger;
		public BootMsg(ActorRef<PingerPongerProtocol> ponger) {
			this.ponger = ponger;
		}
	}	

}
