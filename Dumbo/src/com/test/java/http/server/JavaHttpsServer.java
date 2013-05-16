package com.test.java.http.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class JavaHttpsServer extends JavaHttpServer{
	private static final String SSL_CONTEXT_ALGO = "TLSv1";
	private static final String PASSWORD = "password";
	private static final String KEY_STORE_FILE = "/home/gaurav-abbi/official/development/meg/httpclient/ssl/data/server/httpServer.jks";
	
	public JavaHttpsServer(int port, String keyStoreFile, String password, final boolean clientAuth) throws Exception {
		super(HttpsServer.create(new InetSocketAddress("localhost", port), 100));
		configureSSL(clientAuth, keyStoreFile, password);
	}

	private void configureSSL(final boolean clientAuth, String keyStoreFile, String password)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException,
			UnrecoverableKeyException, KeyManagementException {
		char[] passphrase = password.toCharArray();
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(keyStoreFile), passphrase);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, passphrase);

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);

		setSSLContext(clientAuth, kmf, tmf);
	}

	private void setSSLContext(final boolean clientAuth, KeyManagerFactory kmf,
			TrustManagerFactory tmf) throws NoSuchAlgorithmException,
			KeyManagementException {
		SSLContext sslCtx = SSLContext.getInstance(SSL_CONTEXT_ALGO);
		sslCtx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		
		((HttpsServer)server).setHttpsConfigurator(new HttpsConfigurator(sslCtx) {
			public void configure (HttpsParameters params) {

				SSLContext sslContext = getSSLContext();
				// get the default parameters
				SSLParameters sslparams = sslContext.getDefaultSSLParameters();
				sslparams.setNeedClientAuth(clientAuth);
				params.setSSLParameters(sslparams);
				// statement above could throw IAE if any params invalid.
				// eg. if app has a UI and parameters supplied by a user.

			}
	    });
	}
	
	public static void main(String[] args) throws Exception {
		new JavaHttpsServer(25001, KEY_STORE_FILE, PASSWORD, false).start();
	}
}
