package learn.jms;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class BasicJMS {
	private static final String DURABLE_TOPIC = "durableTopic";

	private static Context JNDI_CONTEXT;
	private static ConnectionFactory CONNECTION_FACTORY;

	private static Connection CONNECTION;
	public static final CountDownLatch DURABLE_LATCH = new CountDownLatch(1);
	static{
		try {
			JNDI_CONTEXT = new InitialContext();
			CONNECTION_FACTORY = (ConnectionFactory) JNDI_CONTEXT.lookup("ConnectionFactory");
			CONNECTION = CONNECTION_FACTORY.createConnection();
			//CONNECTION.setClientID(DurableSubscriber.DURABLE_SUBSCRIBER_CLIENT_ID);
			CONNECTION.setExceptionListener(new ExceptionListener() {
				@Override
				public void onException(JMSException exception) {
					MyLogger.LOGGER.error("Connection Problem as notified by JMS Provider",exception);
				}
			});
		} catch (NamingException e) {
			MyLogger.LOGGER.error("Naming error",e);
		} catch (JMSException e) {
			MyLogger.LOGGER.error("Connection CreationError",e);
		}
	}
	
	
	private BasicJMS() {}

	public static void main(String[] args) {
		try{
			for(int i = 1;i<args.length;i++){
				for(Scenario scenario:Scenario.values()){
					if(scenario == Scenario.DURABLE_SUBSCRIPTION) continue;
					long time = System.currentTimeMillis();
					runTest(args[0],args[i],scenario);
					System.out.println("Scenario["+scenario.toString()+"] took ["+((System.currentTimeMillis()-time)/1000)+"]");
				}
			}
			runTest(args[0], args[1], Scenario.DURABLE_SUBSCRIPTION);
		}
		finally{
			try {
				System.out.println("Produce - Consume =["+(Producer.getCount() - (SynchronousConsumer.getCount()+MyMessageListener.getCOUNT()))+"]");
				CONNECTION.close();
				DURABLE_LATCH.countDown();
			} catch (JMSException e) {
				MyLogger.LOGGER.error("Error while closing "+BasicJMS.class.getName()+" connection",e);
			}
		}
	}

	protected static void runTest(String runTime,String destination,Scenario scenario) {
		ExecutorService workers = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(2);

		try {
			long runTimeperiod;
			try{
				runTimeperiod = Integer.valueOf(runTime) * 1000;
			}catch (NumberFormatException e) {
				MyLogger.LOGGER.error("Invalid run Period", e);
				runTimeperiod = 60 * 1000;
			}
			//Producer
			workers.submit(new Producer(destination,runTimeperiod,scenario == Scenario.TRANSACTIONAL,latch));
			//Consumer
			workers.submit(getConsumer(destination, scenario, latch));
			latch.await();
		} catch (NamingException e) {
			MyLogger.LOGGER.error("Naming error",e);
		} catch (InterruptedException e) {
			MyLogger.LOGGER.error("Error during sleep",e);
		} finally{
			workers.shutdown();
		}
	}

	protected static SynchronousConsumer getConsumer(String destination,
			Scenario scenario, CountDownLatch latch) throws NamingException {
		switch (scenario) {
		case ASYNC:
			return new AsynchronousConsumer(destination,false,latch);
		case CONNECTION_CONSUMER:
			return new MyConnectionConsumer(destination,false,latch);
		case SYNC:
		case TRANSACTIONAL:
			return new SynchronousConsumer(destination,scenario == Scenario.TRANSACTIONAL,latch);
		case DURABLE_SUBSCRIPTION:
			return new DurableSubscriber(DURABLE_TOPIC,false,latch);
		default:
			return null;
		}
	}
	
	public static Context getJndiContext() {
		return JNDI_CONTEXT;
	}

	public static Connection getConnection() {
		return CONNECTION;
	}
	
	enum Scenario{
		ASYNC
		,SYNC
		,TRANSACTIONAL
		,CONNECTION_CONSUMER
		,DURABLE_SUBSCRIPTION
	}

	public static ConnectionFactory getCONNECTION_FACTORY() {
		return CONNECTION_FACTORY;
	}
}

