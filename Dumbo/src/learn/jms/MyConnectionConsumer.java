package learn.jms;

import java.util.concurrent.CountDownLatch;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.naming.NamingException;

public class MyConnectionConsumer extends AsynchronousConsumer{


	public MyConnectionConsumer(String destinationName,
			boolean isTransactional, CountDownLatch latch)
			throws NamingException {
		super(destinationName, isTransactional, latch);
	}
	
	@Override
	public void run(){
		ConnectionConsumer connectionConsumer = null;
		MyServerSessionPool serverSessionPool = null;
		try {
			 serverSessionPool = new AnotherMyServerSessionPool(7);
													//new MyServerSessionPool(7), 
			connectionConsumer = BasicJMS.getConnection().createConnectionConsumer
						(destination,SynchronousConsumer.MESSAGE_SELECTOR, 
								serverSessionPool,
								7);
			BasicJMS.getConnection().start();
		} catch (JMSException e) {
			MyLogger.LOGGER.error("Error during ConnectionConsumer run", e);
			Thread.currentThread().interrupt();
		}finally{
			sleepForProducer();
			if(connectionConsumer != null){
				try {
					connectionConsumer.close();
					serverSessionPool.close();
				} catch (JMSException e) {
					Thread.currentThread().interrupt();
					MyLogger.LOGGER.error("Error closing connection consumer", e);
				}
			}
			latch.countDown();
		}
	}

}
