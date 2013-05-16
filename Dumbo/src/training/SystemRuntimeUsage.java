package training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SystemRuntimeUsage {
	public static void main(String[] args) throws IOException {
		System.in.read();
		System.out.println("After reading input\n");
		System.err.println("This is used to print error");
		
		System.out.println("Current time in milliseconds is["+System.currentTimeMillis()+"]");
		int[] src = {1,2,3,4,5,6};
		int[] dest = new int[3];
		System.arraycopy(src, 1, dest, 0, 3);
		System.out.println("Copied array is"+Arrays.toString(dest));
		
		Process process = Runtime.getRuntime().exec("ls -l /home/gauravabbi/trainingDir/dummyFile");
		printResult(process);
		
		System.out.println(System.getProperty("os.name"));
	}

	public static void printResult(Process process) throws IOException {
		InputStream inputStream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String str = null;
		while((str = reader.readLine()) != null && str.trim().length() > 0){
			System.out.println(str);
		}
		inputStream.close();
	}
}
