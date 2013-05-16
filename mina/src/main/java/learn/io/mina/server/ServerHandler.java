package learn.io.mina.server;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler implements IoHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("session created");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session opened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session closed");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		LOGGER.warn("Session {} has been idle with status {}", new Object[]{session, status});
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOGGER.error("failure occured for session :"+session, cause);
		boolean immediately = true;
		closeSession(session, immediately);
	}


	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println(message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("message sent");
	}
	
	private void closeSession(final IoSession session, boolean immediately) throws InterruptedException {
		CloseFuture future = session.close(immediately);
		future.addListener(new IoFutureListener<IoFuture>() {
			@Override
			public void operationComplete(IoFuture future) {
				// TODO: do any clean up work after close
				LOGGER.debug("Session {} successfuly closed", new Object[] { session });
			}
		});
	}

}
