package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class GetEncoding {
	public static void main(String[] args) throws FileNotFoundException {
		for(String file:args){
			InputStreamReader isr = new InputStreamReader
								(new FileInputStream(file));
			System.out.println(isr.getEncoding());
		}
	}
}
