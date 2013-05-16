package com.test.java.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestHttpSenderServletHelper {
    private static final Logger LOGGER = Logger.getLogger( TestHttpSenderServletHelper.class.getCanonicalName() );

    protected static final Map<String, String> database = new HashMap<String, String>();
    
    public void get(String id, OutputStream outputStream) throws IOException{
        if ( id != null && database.containsKey( id ) ) {
            String value = database.get( id );
            outputStream.write( value.getBytes() );
        } else {
            outputStream.write( "SAMPLE_RESPONSE_GET".getBytes());
        }
        outputStream.flush();
        outputStream.close();
    }
    
    public void post(String id, InputStream inputStream){
        String requestString = Helper.getStringFromInputStream( inputStream );
        if ( id != null ) {
            LOGGER.log( Level.FINE, "Request body as String is {0} and adding it to Map with id {0}", new Object[] { requestString, id } );
            database.put( id, requestString );
        } else {
            LOGGER.log( Level.FINE, "Request body as String is {0} and not adding it to Map since no ID present",
                new Object[] { requestString } );
        }
    }
    
    public boolean delete(String id){
        if ( id != null ){
            return database.remove( id ) != null;
        }
        return false;
    }
}
