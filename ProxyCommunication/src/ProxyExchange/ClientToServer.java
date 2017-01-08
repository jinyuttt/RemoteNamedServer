package ProxyExchange;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;

/**
 * 接收客户端调用
 * 
 * 暂时采用jeromq
 * @author jinyu
 *
 */
public class  ClientToServer {

	 //Context context = ZMQ.context(1);
	 //IDataCall thecall=null;
	 public String ip;
	 public int port;
	 public String type_Name;
	 IDDS_Protocol curObj=null;
	 DataRecvice rec=new DataRecvice();
	  public void InitServer(IDataCall call, StringBuilder error) {
		 // thecall=call;
		  rec.calldata=call;
		  try {
			  Object obj=TransferProtocolObj.getInstance().CreateObject(type_Name);
			  if(obj!=null)
			  {
				   curObj = (IDDS_Protocol)obj;
				  
				  curObj.RecData(ip+":"+port, rec);
				
			  }	
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		  
//		Thread rec1=new Thread(new Runnable()
//		{
//	public void run() {
//	
//		      Socket frontend = context.socket(ZMQ.ROUTER);
//			  Socket backend=context.socket(ZMQ.DEALER);
//			  String tcpCon="tcp://"+ip+":"+port;
//			  frontend.bind(tcpCon);
//			  backend.bind("inproc://rec");
//			  
//			  InitWork();
//	          ZMQ.proxy(frontend, backend, null);
//	        //
//	         
//		
//	}
//
//
//		});
//rec1.setDaemon(true);
//rec1.setName("recNetData");
//rec1.start();
	}
//	private void InitWork() {
//		Thread rec1=new Thread(new Runnable()
//		
//		{
//			@Override
//			public void run() {
//				
//				 Socket responder = context.socket(ZMQ.REP);
//		          responder.connect("inproc://rec");
//		          while(true)
//		          {
//		        	byte[]data=  responder.recv();
//		        	responder.send("end");
//		        	try
//		        	{
//		        	CallData("",0,data);
//		        	}
//		        	catch(Exception ex)
//		        	{
//		        		ex.printStackTrace();
//		        	}
//		          }
//			}
//
//			private void CallData(String string, int i, byte[] data) {
//			  
//				thecall.RecData(data);
//				
//			}
//		}
//);
//rec1.setDaemon(true);
//rec1.setName("recWorkData");
//rec1.start();	
//		
//	}
}
