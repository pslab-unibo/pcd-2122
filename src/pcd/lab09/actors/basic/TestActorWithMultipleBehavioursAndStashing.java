package pcd.lab09.actors.basic;

import akka.actor.typed.ActorSystem;

public class TestActorWithMultipleBehavioursAndStashing {
  public static void main(String[] args) throws Exception  {

	  final ActorSystem<ActorWithMultipleBehaviorsBaseMsg> myActor =
			    ActorSystem.create(ActorWithMultipleBehaviorsAndStashing.create(0), "myActor");

	  /* 
	   * The following two msgs are not handled in the initial behaviour.
	   * However they are not lost, they are stashed
	   *  
	   */	  
	  myActor.tell(new ActorWithMultipleBehaviorsAndStashing.MsgOne());  
	  myActor.tell(new ActorWithMultipleBehaviorsAndStashing.MsgTwo());  
	  
	  /* Give time to the logging system to setup */
	  Thread.sleep(1000);

	  /* 
	   * The next msg will cause a transition to the new behaviour, 
	   * where the stashed msgs will be unstashed and processed.  
	   */
	  myActor.tell(new ActorWithMultipleBehaviorsAndStashing.MsgZero()); 
  
	  System.out.println("done");
  }
}
