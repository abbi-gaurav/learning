package com.test.java.http.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Helper {
	private static final int BUF_SIZE = 1024;

	public static String getStringFromInputStream( InputStream is ) {
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
