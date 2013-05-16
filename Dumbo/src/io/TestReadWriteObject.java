package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestReadWriteObject {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		MySerializable obj = MySerializable.getInstance();
		System.out.println("Created["+obj+"]");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		Thread.sleep(1000);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
		MySerializable recreated = (MySerializable) ois.readObject();
		System.out.println("Recreated["+recreated+"]");
	}
}
