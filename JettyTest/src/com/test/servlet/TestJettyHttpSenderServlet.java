package com.test.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.util.Helper;

public class TestJettyHttpSenderServlet extends HttpServlet {
	public static final String DOCS_DIR = "DOCS_DIR";
	
	private static final String NO_DOCUMENT_DIRECTORY_AVAILABLE = "No Document directory available";

	private static final long serialVersionUID = 6632516933276299624L;

	public static final String SAMPLE_RESPONSE_GET = "Sending sample response for Get";

	private static final String NO_DOCUMENT_AVAILABLE = "No document avalable";

	private static final String QUERY_PARAM_NAME = "key";

	private final File docsDir;

//	private final String osDir = "/ais_local/share2/gabbi";
	
	private final String osDir = "/home/gaurav-abbi/official/development/meg/httpclient/testing/jettyServerOsDir";
	
	public TestJettyHttpSenderServlet() {
		this.docsDir = initializeServerDocsDir();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		config.getServletContext().setAttribute(DOCS_DIR, docsDir.getAbsolutePath());

	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String id = getId( req );
		if ( id != null ) {
			try{
				sendDoc(resp.getOutputStream(), id);
				resp.getOutputStream().flush();
				return;
			}catch (MyException e) {
				System.out.println("got my exception");
				e.printStackTrace();
			}
		} 
		resp.getOutputStream().write( SAMPLE_RESPONSE_GET.getBytes() );
		resp.getOutputStream().flush();
	}



	private void handleMyException(HttpServletResponse resp, MyException e) throws IOException {
		System.out.println("got exception");
		e.printStackTrace();
		resp.getOutputStream().write(e.getLocalizedMessage().getBytes());
		resp.setStatus(500);
	}



	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		String id = getId( req );
		if ( id != null ) {
			try{
				streamToFile(req.getInputStream(), id);
			}catch (MyException e) {
				handleMyException(resp, e);
			}
		} else {
			System.out.println( "Request body not adding it to file since no id specified");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = getId(req);
		if(id != null){
			try{
				deleteFile(id);
			}catch (MyException e) {
				handleMyException(resp, e);
			}
		}
	}
	
	private void deleteFile(String id) throws MyException {
		File document = getDocument(id);
		if(!(document.delete())){
			throw new MyException("Delete failed");
		}
	}



	private void streamToFile( ServletInputStream inputStream, String id ) throws IOException, MyException {
		File docsDir = getDocsDir();
		if(docsDir != null){
			File document = new File(docsDir, id);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(document);
				byte[] bytes = new byte[Helper.BUFFER_SIZE];
				int bytesRead = 0;
				while ( (bytesRead = inputStream.read( bytes )) != -1 ) {
					fos.write( bytes, 0, bytesRead );
				}
			} finally {
				if ( fos != null ) {
					fos.close();
				}
			}
		}
	}

	private String getId( HttpServletRequest req ) {
		return req.getParameter( QUERY_PARAM_NAME );
	}

	private void sendDoc(ServletOutputStream outputStream, String id)
	throws IOException, MyException {
		Helper.readFileToOS(getDocument(id),outputStream);
		return;
	}

	private File getDocsDir() throws MyException {
		String docsDir = (String) getServletContext().getAttribute(DOCS_DIR);
		File docsDirFile = new File(docsDir);

		if(!(docsDirFile.exists() || !(docsDirFile.isDirectory()))){
			throw new MyException( NO_DOCUMENT_DIRECTORY_AVAILABLE );
		}
		
		return docsDirFile;
	}
	
	private File getDocument(String fileName) throws MyException{
		File docsDirFile = getDocsDir();
		File document = new File(docsDirFile, fileName);
		
		if(!(document.exists())){
			throw new MyException(NO_DOCUMENT_AVAILABLE);
		}
		
		return document;
	}

	private File initializeServerDocsDir(){
String dirPath = osDir+"/jettyDocs";
    	File docsDir = new File(dirPath);
    	if(docsDir.exists()){
    		if(docsDir.isDirectory()){
    			System.out.println("Directory already exists");
    			return docsDir;
    		}
    		throw new IllegalStateException(dirPath+" is not a directory");
    	}
    	
    	docsDir.mkdir();
    	return docsDir;
    }
	
}

class MyException extends Exception{
	
	private static final long serialVersionUID = 3597404348074671469L;

	public MyException(String reason) {
		super(reason);
	}
}
