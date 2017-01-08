package XMPPClient;




import org.jivesoftware.smack.AbstractXMPPConnection;



import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import org.jivesoftware.smackx.workgroup.WorkgroupInvitation;


public class Client {
public void Connetct()
{
	AbstractXMPPConnection connection = new XMPPTCPConnection("mtucker", "password", "jabber.org");  
	try {
			connection.connect();
		CharSequence user=new StringBuilder();
		connection.login(user, "");
		ChatManager chatmanager = ChatManager.getInstanceFor(connection);
		
		//Presence presence = new Presence(Presence.Type.unavailable);
		//presence.setStatus("Gone fishing");
		
		//connection.sendStanza(presence);
		
		Chat newChat = chatmanager.createChat("", new ChatMessageListener()
				{

					@Override
					public void processMessage(Chat arg0, Message arg1) {
						
						
					}
			
				}
				);
			
		Message msg=new Message("mmm","sss");
		newChat.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
public void WorkGroup()
{
	AbstractXMPPConnection connection = new XMPPTCPConnection("mtucker", "password", "jabber.org");  
	try {
			connection.connect();
		CharSequence user=new StringBuilder();
		connection.login(user, "");
		ChatManager chatmanager = ChatManager.getInstanceFor(connection);
		//ChatMessageListener new ChatMessageListener(){
		WorkgroupInvitation group=new WorkgroupInvitation("jid", "group", "workgroup", "sessID", "msgBody", "from");
	
		Chat newChat = chatmanager.createChat("", new ChatMessageListener()
				{

					@Override
					public void processMessage(Chat arg0, Message arg1) {
						
						
					}
			
				}
				);
			
		Message msg=new Message("mmm","sss");
		newChat.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
