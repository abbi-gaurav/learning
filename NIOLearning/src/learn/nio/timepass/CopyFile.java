package learn.nio.timepass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFile {
	private static final int COUNT = 10000;

	public static void main(String[] args) throws IOException {
		for(int i =0; i < 10;i++){
			test();
		}
	}

	private static void test() throws FileNotFoundException, IOException {
		long start = System.currentTimeMillis();
		for(int i = 0; i < COUNT; i++) {
			doCopy(false);
		}
		System.out.println("Slow took["+(System.currentTimeMillis() - start)+"]");
		
		
		start = System.currentTimeMillis();
		for(int i = 0; i < COUNT; i++) {
			doCopy(true);
		}
		System.out.println("fast took["+(System.currentTimeMillis() - start)+"]");
	}

	private static void doCopy(boolean isFast) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(Basics.WRITE_FILE);
		FileInputStream fis = new FileInputStream(Basics.READ_FILE);
		FileChannel readChannel = fis.getChannel();
		FileChannel writeChannel = fos.getChannel();
		try{
			ByteBuffer buffer = isFast?ByteBuffer.allocateDirect(1024):ByteBuffer.allocate(1024);
			while(readChannel.read(buffer) != -1){
				buffer.flip();
				writeChannel.write(buffer);
				buffer.clear();
			}
		}finally{
			closeIfNotNull(readChannel);
			closeIfNotNull(writeChannel);
			
		}
	}

	private static void closeIfNotNull(FileChannel channel) {
		if(channel != null){
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
