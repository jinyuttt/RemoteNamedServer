package ServerRecInfo;

import ProxyExchange.IDataCallBack;
import ProxyExchange.MulticastRec;
import ProxyExchange.MulticastSend;
import Tools.MsgPackTool;
import serverStruct.ServerBinds;


public class AddServerInstance {
	IDataCallBack  sinfo=new RecServerInfo();

  /**
 *  ���շ������Ϣ����
 */
public void AddServer()
  {
	//��ʼ�����շ���˵ķ�����Ϣ������223�˿�
	MulticastRec  rec=new MulticastRec();
	rec.port=223;
	
	rec.Start(sinfo);
  }
  /**
   * ��������ӷ�����Ϣ���ͻ���
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
