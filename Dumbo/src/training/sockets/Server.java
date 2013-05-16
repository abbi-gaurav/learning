package training.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private static final int PORT = 5002;
	
	public static void main(String[] args) throws IOException {
		new Server().start();
	}

	private void start() throws IOException {
//		ServerSocket serverSocket = new ServerSocket(PORT);
		InetAddress localHost = InetAddress.getLocalHost();
		System.out.println(localHost.getHostName());
		ServerSocket serverSocket = new ServerSocket(PORT,-1,localHost);
		System.out.println("Client waiting for connecctions");
		while(true){
			Socket clientSocket = serverSocket.accept();
			Handler runnable = new Handler(clientSocket);
			new Thread(runnable).start();
			System.out.println("new connection started");
		}
	}
	
	public static class Handler implements Runnable{
		private static final List<Socket> clientSockets = new ArrayList<Socket>();
		private final Socket clientSocket;
		private final BufferedReader bufferedReader;
		
		public Handler(Socket clientSocket) throws IOException {
			this.clientSocket = clientSocket;
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			clientSockets.add(clientSocket);
		}
		@Override
		public void run() {
			String message;
			try{
				while ((message = bufferedReader.readLine()) != null /*&& message.trim().length() > 0*/) {
					System.out.println("Server Got message["+message+"]");
					broadcast(message);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				clientSockets.remove(clientSocket);
			}
		}
		public String read() throws IOException {
			return bufferedReader.readLine();
		}
		private void broadcast(String message) throws IOException {
			for(Socket socket:clientSockets){
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Server replies["+message+"]");
				printWriter.flush();
			}
		}
		
	}
}
