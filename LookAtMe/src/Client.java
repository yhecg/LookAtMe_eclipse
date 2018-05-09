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
					// 앱 로그인시 회원의 정보를 리스트에 담아준다.
					case "member_information":
						String login_member_email = (String)jsonObject.get("login_member_email");
						SocketServer.clientList.put(login_member_email, this);
						System.out.println(login_member_email+" 접속함");
						break;
					// 채팅 메세지일 경우
					case "chatting_message": 
						sendMessage(data);
						break;
					// 채팅 방 관련
					case "room": 
						String room_type = (String)jsonObject.get("room_type");
						switch(room_type){
							case "create": // 회원이 채팅방을 생성하여 다른 회원들을 초대.
								room.createRoom(data);
								break;
							case "invite": // 기존에 있던 채팅방의 유저가 새로운 회원을 채팅방에 초대.
								room.inviteRoom(data);
								break;
							case "exit": // 채팅방을 나간 회원이 있는 경우.
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
	
	// 채팅 메세지
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
