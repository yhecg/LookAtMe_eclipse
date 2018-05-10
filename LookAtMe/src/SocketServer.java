import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServer {
	
	/**
	 * serverSocket : ���� ����
	 * clientSocket : ����ڰ�(Client) ����Ǵ� ����.
	 * port : ��Ʈ ��ȣ.
	 */
	ServerSocket serverSocket;
	Socket clientSocket;
	int port = 5000;
	
	// ������ ����ڸ� ���� ����Ʈ.
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
				Client client = new Client(clientSocket); // �ϳ��� �����
				client.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
