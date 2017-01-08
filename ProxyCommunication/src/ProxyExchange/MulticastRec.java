package ProxyExchange;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;



/**
 * java 组播接收
 * @author jinyu
 *
 */
public class MulticastRec {
public	String IP="237.1.1.1";
public	int port=223;

/**
 * 开启接收
 * 数据回传端口
 * @param rec
 */
public void Start(IDataCallBack rec)
{
 Thread recdata=new Thread(new Runnable()
    		   {   
	@Override
	public void run() {
		       int length = 1024;
	          byte[] buf = new byte[length];
		        MulticastSocket ms = null;
		      DatagramPacket dp = null;
		 try {
			ms = new MulticastSocket(port);
		
         dp = new DatagramPacket(buf, length);
          
         //加入多播地址
         InetAddress group = null;
		group = InetAddress.getByName(IP);
	    ms.joinGroup(group);
	    System.out.println("开启端口："+port);
		   while(true)
	       {
	            
				ms.receive(dp);
		     
			
	          if(rec!=null)
	          {
	        	  String src=  dp.getAddress().toString();
	        	  byte[]data=new byte[dp.getLength()];
	        	  System.arraycopy(dp.getData(),0, data, 0, data.length);
	        	  rec.DataRec(src, data);
	          }
	       }
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	}
	
    		   });	 
 recdata.setDaemon(true);
 recdata.setName("UDP组播接收服务");
 recdata.start();
}
}
