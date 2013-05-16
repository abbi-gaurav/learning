package com.test.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestHttpSenderServlet extends HttpServlet {
	public static final int BUF_SIZE = 32768;
	public static final String QUERY_PARAM_NAME = "key";
	 
    private static final long serialVersionUID = 6632516933276299624L;

    private static final Logger LOGGER = LoggerFactory.getLogger( TestHttpSenderServlet.class );

    private static final Map<String, String> database = new HashMap<String, String>();

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        String id = getId( req );
        if ( id != null && database.containsKey( id ) ) {
            String value = database.get( id );
            outputStream.write( value.getBytes() );
        } else {
            outputStream.write( TestJettyHttpSenderServlet.SAMPLE_RESPONSE_GET.getBytes() );
        }
        outputStream.flush();
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String id = getId( req );
        String requestString = getStringFromInputStream( req.getInputStream() );
        if ( id != null ) {
            LOGGER.debug( "Request body as String is {} and adding it to Map with id {}", new Object[] { requestString, id } );
            database.put( id, requestString );
        } else {
            LOGGER.debug( "Request body as String is {} and not adding it to Map since no ID present",
                new Object[] { requestString } );
        }
    }

    @Override
    protected void doDelete( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String id = getId( req );
        if ( id != null && ( database.remove( id ) != null ) ) {
            resp.setStatus( 200 );
        }
    }

    private String getId( HttpServletRequest req ) {
        return req.getParameter( QUERY_PARAM_NAME );
    }
    
    private static String getStringFromInputStream( InputStream is ) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[BUF_SIZE];
            int bytesRead = 0;
            while ( ( bytesRead = is.read( buf ) ) != -1 ) {
                bos.write( buf, 0, bytesRead );
            }
            String str = new String( bos.toByteArray() );
            return str;
        } catch ( Exception e ) {
            throw new RuntimeException( e );
        } finally {
            if ( is != null ) {
                try {
                    is.close();
                } catch ( IOException e ) {
                    // ignore
                }
            }
        }
    }
}
