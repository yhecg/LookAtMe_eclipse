import java.io.DataOutputStream;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Room {
	
	// ȸ���� ä�ù��� �����Ͽ� �ٸ� ȸ������ �ʴ�.
	public void createRoom(String data){
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(data);
			
			String chattingRoom_accept_member_email = (String)jsonObject.get("chattingRoom_accept_member_email");
			JSONArray jsonArray_accept_member_email = (JSONArray)jsonParser.parse(chattingRoom_accept_member_email);
			
			for(int i=0; i<jsonArray_accept_member_email.size(); i++){
				JSONObject jsonObject_member_email = (JSONObject)jsonArray_accept_member_email.get(i);
				String member_email = (String)jsonObject_member_email.get("member_email");
				Client client = SocketServer.clientList.get(member_email);
				DataOutputStream dataOutputStream = client.dataOutputStream;
				dataOutputStream.writeUTF(data);
				dataOutputStream.flush();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ä�ù濡 ������ �ִ� ȸ���� ���ο� ȸ���� ���� ä�ù濡 �ʴ�.
	public void inviteRoom(String data){
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(data);
			
			String data_send_member_email = (String)jsonObject.get("data_send_member_email");
			JSONArray jsonArray = (JSONArray)jsonParser.parse(data_send_member_email);
			
			for(int i=0; i<jsonArray.size(); i++){
				JSONObject jsonObject_member_email = (JSONObject)jsonArray.get(i);
				String member_email = (String)jsonObject_member_email.get("member_email");
				Client client = SocketServer.clientList.get(member_email);
				DataOutputStream dataOutputStream = client.dataOutputStream;
				dataOutputStream.writeUTF(data);
				dataOutputStream.flush();
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// ä�ù� ���� ȸ���� �ִ� ���.
	public void exitRoom(String data){
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(data);
			
			String chattingRoom_accept_member_email = (String)jsonObject.get("chattingRoom_accept_member_email");
			JSONArray jsonArray = (JSONArray)jsonParser.parse(chattingRoom_accept_member_email);
			
			for(int i=0; i<jsonArray.size(); i++){
				JSONObject jsonObject_member_email = (JSONObject)jsonArray.get(i);
				String member_email = (String)jsonObject_member_email.get("member_email");
				Client client = SocketServer.clientList.get(member_email);
				DataOutputStream dataOutputStream = client.dataOutputStream;
				dataOutputStream.writeUTF(data);
				dataOutputStream.flush();
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
