package httpclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test {
	private static final class DummyHostNameVerifier implements
			X509HostnameVerifier {
		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}

		@Override
		public void verify(String host, String[] cns, String[] subjectAlts)
				throws SSLException {
			
		}

		@Override
		public void verify(String host, X509Certificate cert) throws SSLException {
			
		}

		@Override
		public void verify(String host, SSLSocket ssl) throws IOException {
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) 
	throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try{
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream fis = new FileInputStream("/home/gauravabbi/official/issues/cipherIssue/testHttpsKS");
			try{
				trustStore.load(fis, "password".toCharArray());
			}finally{
				try{
					fis.close();
				}catch (Exception e) {
					System.err.println(e.getStackTrace());
				}
			}
			
			SSLSocketFactory sslSocketFactory = new SSLSocketFactory(trustStore);
			
			Scheme scheme = new Scheme("https", 443, sslSocketFactory);
			httpclient.getConnectionManager().getSchemeRegistry().register(scheme);

			HttpGet getCall = new HttpGet("https://10.11.20.98:52080/test");
			HttpResponse response = httpclient.execute(getCall);

			System.out.println(response);
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
	}
}
