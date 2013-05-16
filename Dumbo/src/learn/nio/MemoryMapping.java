package learn.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MemoryMapping {
	public static void main(String[] args) throws IOException {
		read();
		write();
	}

	private static void write() throws IOException {
		RandomAccessFile file = new RandomAccessFile(Basics.WRITE_FILE, "rw");
		FileChannel channel = file.getChannel();
		MappedByteBuffer buffer = channel.map(MapMode.READ_WRITE, 0, 1024);
		buffer.put((byte) (7));
		buffer.put((byte) (16));
		file.close();
	}

	private static void read() throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(Basics.READ_FILE);
		FileChannel readChannel = fis.getChannel();
		MappedByteBuffer mappedBuffer = readChannel.map(MapMode.READ_ONLY, 0, 1024);
		byte[] array = new byte[1024];
		mappedBuffer.get(array);
		System.out.println(new String(array));
		readChannel.close();
	}
}
