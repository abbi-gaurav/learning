package training.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client().start();
	}

	private void start() throws UnknownHostException, IOException {
		Socket clientSocket = new Socket("127.0.1.1 ", 5002);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		new Reader(reader).start();
		
		readFromConsole(clientSocket);
		
	}
	
	private void readFromConsole(Socket clientSocket) throws IOException {
		String input;
		PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
		
		while((input = System.console().readLine()) != null && input.trim().length() > 0){
			System.out.println("Sending to server["+input+"]");
			printWriter.println(input);
			printWriter.flush();
		}
	}

	public static class Reader extends Thread{

		private BufferedReader bufferedReader;

		public Reader(BufferedReader reader) {
			this.bufferedReader = reader;
		}
		
		@Override
		public void run() {
			String message;
			try {
				while((message = bufferedReader.readLine()) != null && message.trim().length() > 0){
					System.out.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
