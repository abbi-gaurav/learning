package security.certs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ibm.security.util.DerValue;
import com.ibm.security.x509.CRLDistributionPointsExtension;
import com.ibm.security.x509.DistributionPoint;
import com.ibm.security.x509.GeneralName;
import com.ibm.security.x509.GeneralNameInterface;
import com.ibm.security.x509.GeneralNames;
import com.ibm.security.x509.GeneralNamesException;

public class TestCRLVerification {
	private static final String CRL_DATA_STREAM = "/home/gaurav_abbi/official/meg/identity/data/CRLDataStream";
	private static final String URI_NAME = "URIName:";
	private static final String CERT_FILE = "/home/gaurav_abbi/official/meg/identity/data/DigiNotarCyberCA.der";

	public static void main(String[] args) throws CertificateException, IOException, CRLException, GeneralNamesException {
		TestCRLVerification worker = new TestCRLVerification();
		X509Certificate x509Certificate = worker.getX509Cert();
//		String uriValue = worker.getUri(x509Certificate);
		String uriValue = worker.getUri2(x509Certificate).get(0);
		
		System.out.println(uriValue);
		InputStream crlIns = worker.getCRLInputStream(uriValue);
		worker.verifyCertificate(crlIns, x509Certificate);
	}

	private void verifyCertificate(InputStream crlIns, X509Certificate cert) {
		// TODO use java security APIs to verify
		try{
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509CRL crl = (X509CRL) cf.generateCRL(crlIns);
			if(crl.isRevoked(cert)){
				String err = "cert is revoked";
				System.out.println(err);
				throw new RuntimeException(err);
			}else{
				System.out.println("cert is not revoked");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}finally{
			//TODO: is inputStream to be closed
		}
		
	}

	private X509Certificate getX509Cert() throws FileNotFoundException,
			CertificateException {
		FileInputStream fis = new FileInputStream(CERT_FILE);
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(fis);
		return x509Certificate;
	}

	private String getUri(X509Certificate x509Certificate)
			throws IOException {
		byte[] crlUrlBytes = x509Certificate.getExtensionValue("2.5.29.31");
		System.out.println(Arrays.toString(crlUrlBytes));
		System.out.println(new String(crlUrlBytes));
		byte[] generalNameArray = null;
		for(int i =0; i< crlUrlBytes.length;i++){
			if(crlUrlBytes[i] == -122){
				generalNameArray = new byte[crlUrlBytes.length - i];
				System.arraycopy(crlUrlBytes, i, generalNameArray, 0, crlUrlBytes.length-i);
			}
		}
		DerValue derValue = new DerValue(generalNameArray);
		GeneralName gn = new GeneralName(derValue);
		GeneralNameInterface name = gn.getName();
		String uriValue = name.toString();
		System.out.println(uriValue);
		//TODO: return null if URINAME: is not present
		String httpUriVal = extractURL(uriValue);
		System.out.println(httpUriVal);
		//TODO: return null if http:// is not present
		return httpUriVal;
	}

	private String extractURL(String uriValue) {
		if(uriValue.startsWith("[")){
			uriValue = uriValue.replaceFirst("[", "");
		}
		if(uriValue.endsWith("]")){
			uriValue = uriValue.replace("]", "");
		}
		String uriAfterPreficx = uriValue.substring(URI_NAME.length()).trim();
		return uriAfterPreficx;
	}
	
	private List<String> getUri2(X509Certificate cert) throws IOException, GeneralNamesException{
		List<String> generalNames = new ArrayList<String>();
		byte[] crlUrlBytes = cert.getExtensionValue("2.5.29.31");
		DerValue derValue = new DerValue(crlUrlBytes);
		CRLDistributionPointsExtension ce = new CRLDistributionPointsExtension(false,derValue.getOctetString());
		DistributionPoint[] dpts = ce.getDistributionPoints();
		for(DistributionPoint dp: dpts){
			GeneralNames gns = (GeneralNames) dp.getName();
			GeneralName gName = gns.getGeneralName(GeneralNameInterface.NAME_URI);
			GeneralNameInterface uriName = gName.getName();
			String uriNameStr = uriName.toString();
			System.out.println(uriNameStr);
			String urlValue = extractURL(uriNameStr);
			generalNames.add(urlValue);
		}
		return generalNames;
	}
	
	private InputStream getCRLInputStream(String url ){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			response.getEntity().writeTo(new FileOutputStream(CRL_DATA_STREAM));
			//return response.getEntity().getContent();
			return new FileInputStream(CRL_DATA_STREAM);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
