import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client extends Thread{
	
	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;
	
	Room room;
	
	public Client(Socket socket){
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			room = new Room();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			String data = dataInputStream.readUTF();
			while(data != null){
//				System.out.println(data);
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
				String data_type = (String) jsonObject.get("data_type");
				
				switch (data_type) {
					// �� �α��ν� ȸ���� ������ ����Ʈ�� ����ش�.
					case "member_information":
						String login_member_email = (String)jsonObject.get("login_member_email");
						SocketServer.clientList.put(login_member_email, this);
						System.out.println(login_member_email+" ������");
						break;
					// ä�� �޼����� ���
					case "chatting_message": 
						sendMessage(data);
						break;
					// ä�� �� ����
					case "room": 
						String room_type = (String)jsonObject.get("room_type");
						switch(room_type){
							case "create": // ȸ���� ä�ù��� �����Ͽ� �ٸ� ȸ������ �ʴ�.
								room.createRoom(data);
								break;
							case "invite": // ������ �ִ� ä�ù��� ������ ���ο� ȸ���� ä�ù濡 �ʴ�.
								room.inviteRoom(data);
								break;
							case "exit": // ä�ù��� ���� ȸ���� �ִ� ���.
								room.exitRoom(data);
								break;
						
						}
						break;
				}
				data = dataInputStream.readUTF();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// ä�� �޼���
	private void sendMessage(String data){
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(data);
			String chattingRoom_accept_member_email = (String)jsonObject.get("chattingRoom_accept_member_email");
			JSONArray jsonArray_accept_member_email = (JSONArray)jsonParser.parse(chattingRoom_accept_member_email);
			
			for(int i=0; i<jsonArray_accept_member_email.size(); i++){
				JSONObject jsonObject_member_email = (JSONObject)jsonArray_accept_member_email.get(i);
				String member_email = (String)jsonObject_member_email.get("member_email");
				Client client = SocketServer.clientList.get(member_email);
				if(!client.equals(this)){
					DataOutputStream dataOutputStream = client.dataOutputStream;
					dataOutputStream.writeUTF(data);
					dataOutputStream.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
