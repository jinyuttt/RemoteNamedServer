package ServerRecInfo;

import ProxyExchange.IDataCallBack;
import ProxyExchange.MulticastRec;
import ProxyExchange.MulticastSend;
import Tools.MsgPackTool;
import serverStruct.ServerBinds;


public class AddServerInstance {
	IDataCallBack  sinfo=new RecServerInfo();

  /**
 *  接收服务端信息发送
 */
public void AddServer()
  {
	//初始化接收服务端的服务信息，监听223端口
	MulticastRec  rec=new MulticastRec();
	rec.port=223;
	
	rec.Start(sinfo);
  }
  /**
   * 发送新添加服务信息给客户端
 * @param info
 */
public void SendInfo(ServerBinds info)
  {
	  StringBuilder error=new StringBuilder();
	  byte[]data=null;
	  MsgPackTool tool=new MsgPackTool();
	  data=tool.Serialize(info, error);
	  MulticastSend sendClient=new MulticastSend();
	  sendClient.SendData(data);
	
  }



}
