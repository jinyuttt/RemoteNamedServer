package ProxyExchange;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;



/**
 * java �鲥����
 * @author jinyu
 *
 */
public class MulticastRec {
public	String IP="237.1.1.1";
public	int port=223;

/**
 * ��������
 * ���ݻش��˿�
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
          
         //����ಥ��ַ
         InetAddress group = null;
		group = InetAddress.getByName(IP);
	    ms.joinGroup(group);
	    System.out.println("�����˿ڣ�"+port);
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
 recdata.setName("UDP�鲥���շ���");
 recdata.start();
}
}
