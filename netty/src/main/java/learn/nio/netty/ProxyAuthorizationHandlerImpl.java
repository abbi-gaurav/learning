package learn.nio.netty;

import org.apache.commons.codec.binary.Base64;
import org.littleshoot.proxy.ProxyAuthorizationHandler;

public class ProxyAuthorizationHandlerImpl implements ProxyAuthorizationHandler {

	private final String userName;
	private final String password;
	
	public ProxyAuthorizationHandlerImpl(String user, String password) {
		this.userName = user;
		this.password = password;
		
		System.out.println("proxy handler create with user::"+this.userName+"::"+this.password);
	}

	@Override
	public boolean authenticate(String userName, String password) {
		System.out.println("userName::"+userName+":::password::"+password);
		return userName.equals(this.userName) && password.equals(this.password);
	}
	
	public static void main(String[] args) {
		System.out.println(new String(Base64.decodeBase64("YWRtaW46cGFzc3dvcmQ=")));
	}

}
