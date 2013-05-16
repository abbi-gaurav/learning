package io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;

public class MySerializable implements Serializable{
	private MySerializable(){}
	private static final long serialVersionUID = 247582545974023260L;
	private transient Date dynamicDate = new Date();
	private Date serializedDate = new Date();
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
		ois.defaultReadObject();
		dynamicDate = new Date();
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
	}

	public Date getDynamicDate() {
		return dynamicDate;
	}

	public Date getSerializedDate() {
		return serializedDate;
	}
	
	@Override
	public String toString() {
		return "DD: "+dynamicDate+"----SD["+serializedDate+"]";
	}
	public static MySerializable getInstance(){
		return SingletonHolder.getInstance();
	}
	
	private Object readResolve() throws ObjectStreamException{
		return MySerializable.getInstance();
	}
	
	private Object writeReplace() throws ObjectStreamException{
		return MySerializable.getInstance();
	}
	
	private static class SingletonHolder{
		private static final MySerializable SINGLETON = new MySerializable();

		private static MySerializable getInstance() {
			return SINGLETON;
		}
	}
}