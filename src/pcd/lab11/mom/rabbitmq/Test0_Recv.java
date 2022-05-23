package pcd.lab11.mom.rabbitmq;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Test0_Recv {

	  private final static String QUEUE_NAME = "hello";

	  public static void main(String[] argv) throws Exception {
	    
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        String message = new String(delivery.getBody(), "UTF-8");
	        System.out.println(" [x] Received '" + message + "' by thread: "+Thread.currentThread().getName());
	    };
	    String consumerTag = channel.basicConsume(QUEUE_NAME, true, deliverCallback, /* cancellation callback */ consTag -> { });
	    
	    System.out.println("Consumer configured - tag: " + consumerTag);
	  }
}
