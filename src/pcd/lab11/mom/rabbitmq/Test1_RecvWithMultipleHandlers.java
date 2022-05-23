package pcd.lab11.mom.rabbitmq;
import com.rabbitmq.client.*;

public class Test1_RecvWithMultipleHandlers {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [x] Received A '" + message + "' by thread "+Thread.currentThread().getName());
        try {
      	  		Thread.sleep(1000);
        } catch (Exception ex) {
      	  	ex.printStackTrace();
        }
    };

    channel.basicConsume(QUEUE_NAME, true, deliverCallback1, consumerTag -> {});
    System.out.println("Consumer #1 configured.");
    
    DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [x] Received B '" + message + "' by thread "+Thread.currentThread().getName());
    };

    channel.basicConsume(QUEUE_NAME, true, deliverCallback2, t -> {});
    System.out.println("Consumer #2 configured.");

  }
}
