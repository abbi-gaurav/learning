package com.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Helper {
	public static final int BUFFER_SIZE = 128 * 1024;

	public static void readFileToOS(File document, OutputStream os)
	throws IOException {
		try (FileInputStream fis = new FileInputStream(document)) {
			byte[] bytes = new byte[Helper.BUFFER_SIZE];
			int bytesRead = 0;
			while ((bytesRead = fis.read(bytes)) != -1) {
				os.write(bytes, 0, bytesRead);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
