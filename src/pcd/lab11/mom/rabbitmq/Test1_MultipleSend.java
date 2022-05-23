package pcd.lab11.mom.rabbitmq;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Test1_MultipleSend {

  private final static String QUEUE_NAME = "hello";
  private final static String NO_EXCHANGE_USED = "";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
   
    for (int i = 0; i < 6; i++){
        String message = "Hello World! "+i;
    		channel.basicPublish(NO_EXCHANGE_USED, QUEUE_NAME, null, message.getBytes("UTF-8"));
    		System.out.println(" [x] Sent '" + message + "'");
    }
    
    channel.close();
    connection.close();
  }
}
