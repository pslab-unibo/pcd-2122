package pcd.lab11.mom.rabbitmq;
import com.rabbitmq.client.*;

public class Test1_Receive {

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
        System.out.println(" [x] Received '" + message + "' by thread "+Thread.currentThread().getName());
        // while (true){}
        try {
        		Thread.sleep(2000);
        } catch (Exception ex) {}
      };

    channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
  }
}
