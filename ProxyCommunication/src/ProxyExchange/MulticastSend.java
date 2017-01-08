package ProxyExchange;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSend {
	
public  String host = "237.1.1.1";//�ಥ��ַ
public int port = 223;
/**
 * �鲥����
 * @param data
 */
public  void SendData(byte[]data)
{
   
     try {
         InetAddress group = InetAddress.getByName(host);
         MulticastSocket s = new MulticastSocket();
         //����ಥ��
         s.joinGroup(group);
         DatagramPacket dp = new DatagramPacket(data,data.length,group,port);
         s.send(dp);
         s.close();
     } catch (UnknownHostException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
}
}
