package security;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleRead {
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String string;
		while((string=br.readLine()) !=null&&string.trim().length() > 0){
			System.out.println(string);
		}
		System.exit(0);
	}
}
