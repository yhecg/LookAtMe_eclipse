import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServer {
	
	/**
	 * serverSocket : 소켓 서버
	 * clientSocket : 사용자가(Client) 연결되는 소켓.
	 * port : 포트 번호.
	 */
	ServerSocket serverSocket;
	Socket clientSocket;
	int port = 5000;
	
	// 접속한 사용자를 담은 리스트.
	public static HashMap<String, Client> clientList = new HashMap<String,Client>();

	public static void main(String[] args) {
		new SocketServer();
	}
	
	public SocketServer(){
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("server start");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true){
			try {
				clientSocket = serverSocket.accept();
				Client client = new Client(clientSocket); // 하나의 사용자
				client.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
