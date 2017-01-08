package ProxyExchange;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSend {
	
public  String host = "237.1.1.1";//多播地址
public int port = 223;
/**
 * 组播发送
 * @param data
 */
public  void SendData(byte[]data)
{
   
     try {
         InetAddress group = InetAddress.getByName(host);
         MulticastSocket s = new MulticastSocket();
         //加入多播组
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
