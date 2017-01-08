package Protocols;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;



import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import DDS_Transfer.ProtocolType;



@ProtocolType(Name="tcp")
public class TCPProtocols implements IDDS_Protocol{
	
	//保存接收数据的注册
	
	String curBindAddress="";
	String curLocalAddress="";
	IRecMsg curRec=null;
	public void SetBindAddress(String addr)
	{
		//curBindAddress=addr;
	}
private void SendMsg(String ip,int port,byte[]data) throws IOException, Exception
{
	  Socket client = new Socket(ip , port);
      OutputStream out = client.getOutputStream();
      out.write(data);
      out.flush();
      out.close();
      curLocalAddress= client.getLocalAddress().toString()+":"+client.getLocalPort();
      client.close();
     
}

@Override
public boolean SendData(String adress, byte[] data) {
	String[] addr=adress.split(":");
	if(addr.length==2)
	{
		String serverIP=addr[0];
		int port=Integer.valueOf(addr[1]);
		// TCP发送
		try {
			
			SendMsg(serverIP,port,data);
		} catch (IOException e) {
		  System.out.println("数据通信失败,IP:"+serverIP+",端口："+port);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	return true;
}

@Override
public void RecData(String Address, IRecMsg rec) {
	 System.out.println(Address);
         curRec=rec;
		//避免同一实例启动
	  
 	    SocketRec(Address);
	

	
}

/**
 * 开启网络接收
 * @param Address
 */
private void SocketRec(String Address)
{
	Thread rec1=new Thread(new Runnable()
			{
		
		
		@SuppressWarnings("resource")
		public void run() {
			String[] addr=Address.split(":");
			curBindAddress=Address;
			 System.out.println("准备绑定地址");
			if(addr.length==2)
			{
				
				String serverIP=addr[0];
				int port=Integer.valueOf(addr[1]);
				 ServerSocket listen = null;
				if(serverIP.equals("*"))
				{   
					
					   try {
						listen = new ServerSocket();
						listen.setReuseAddress(true);
					    listen.bind(new InetSocketAddress(port));
						  System.out.println("绑定端口:"+port);
					} 
					   catch (IOException ex) 
					   {
						
						   System.out.println(ex.getLocalizedMessage());
					}
					
				}
				else
				{
			      
					try {
						 listen = new ServerSocket();
						 listen.setReuseAddress(true);
						 SocketAddress endpoint = new InetSocketAddress(serverIP, port); 
						 System.out.println("绑定端口IP:"+port+";"+serverIP);
						 listen.bind(endpoint);
						 
					} catch (IOException ex) {
					
						System.out.println(ex.getLocalizedMessage());
					}
			    }
				
				while(true)
				{
					byte[]datas=new byte[1024];  
		             Socket server = null;
				
					try {
						server = listen.accept();
						InputStream in = null;
						
						in = server.getInputStream();
						String ip=server.getInetAddress().getHostAddress();
			            	int remoteport=server.getPort();
							int r=  in.read(datas);
							 byte[]realData=new byte[r];
							 System.arraycopy(datas, 0, realData, 0, r);
							 //没有考虑分包或接收不全
							 CallData(ip,remoteport,realData);
					} catch (IOException e) {
						 System.out.println(e.getLocalizedMessage());
						e.printStackTrace();
					}
			
		           
				}
		
			}
		}
			});
	rec1.setDaemon(true);
	rec1.setName("recTCPDDSData");
	rec1.start();
}
/**
 * 解析数据
 * @param src
 * @param port
 * @param data
 */
private void CallData(String src,int port,byte[]data)
{
	if(curRec!=null)
	{
		String address=src+":"+port;
		curRec.RecData(address, data);
	}
	
//	DDSData curdata=AnalyzeData.AsyData(data);
//	//
//	List<IRecMsg> waitRec=hmap.get(curdata.topicName);
//	if(waitRec!=null)
//	{
//		int num=waitRec.size();
//	for(int i=0;i<num;i++)
//	{
//		IRecMsg tmp=waitRec.get(i);
//		tmp.RecData(src, curdata.topicName, curdata.data);
//	}
//	}
}

@Override
public String GetBindAddress() {

	return curBindAddress;
}

	

@Override
public String GetLocalAddress() {
	return curLocalAddress;
}
  
/**  
* (non-Javadoc)    
* @see DDS_Transfer.IDDS_Protocol#Close()    
*/  

@Override
public void Close() {
	// TODO Auto-generated method stub
	
}
  
/**  
* (non-Javadoc)    
* @see DDS_Transfer.IDDS_Protocol#Connect()    
*/  

@Override
public boolean Connect() {
	// TODO Auto-generated method stub
	return false;
}


}
