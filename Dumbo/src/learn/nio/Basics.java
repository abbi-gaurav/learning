package learn.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Basics {
	public static final String READ_FILE = "/home/gauravabbi/learning/nio/test/read";
	public static final String WRITE_FILE = "/home/gauravabbi/learning/nio/test/write";

	public static void main(String[] args) throws IOException {
		read();
		write();
	}

	private static void write() throws IOException {
		FileOutputStream fos = new FileOutputStream(WRITE_FILE);
		FileChannel channel = fos.getChannel();
		byte[] message = "SomeMessage".getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(message);
		buffer.flip();
		channel.write(buffer);
	}

	public static void read() throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(READ_FILE);
		FileChannel channel = fis.getChannel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		System.out.println(channel.read(buffer));
	}
}
