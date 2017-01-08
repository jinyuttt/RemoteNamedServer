package nameServerContainer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import RequestServerInfo.ReqInfoSend;
import ServerRecInfo.AddServerInstance;
import hashingAlgorithm.cirALL;
import serverStruct.ServerBinds;



public class InitPlugin  {

      AddServerInstance serverAdd=new AddServerInstance(); 
	private final static InitPlugin sigle=new InitPlugin();
	public static InitPlugin GetInstance()
	{
		return  sigle;
	}
   private InitPlugin()
   {
	   
   }
	
 /**
  * ��ȡ������Ϣ
 * @param name ��������
 * @return ���������Ϣ
 */
public ServerBinds GetCur(String name) {
		
	ServerInfoSave  temp=new ServerInfoSave();
    ArrayList<ServerBinds> listObj=temp.GetCur(name);
	if(listObj==null||listObj.size()==0)
	{
		return null;
	}
	else if(listObj.size()==1)
	{
		return listObj.get(0);
	}
	else
	{
		//���ø��ؾ���
		cirALL cir=new cirALL();
		Object[] objarry=listObj.toArray();
		CopyOnWriteArrayList<Object> cal = new CopyOnWriteArrayList<Object>(objarry);
	    Object obj=	cir.GetCurNodeInfo(cal);
	    ServerBinds info=(ServerBinds)obj; 
	    return info;
	}
		
	
	}
	
	/**
	 * ��ʼ�����շ�����Ϣ
	 * 
	 */
	public void InitRecSeverInfo()
	{
		serverAdd.AddServer();
	}
	
	
	/**
	 * ����˰�����ӵķ����ͳ�ȥ
	 * @param info
	 */
	public void NoticeServerInfo(ServerBinds info)
	{
		serverAdd.SendInfo(info);
	}
	
/**
 * �ͻ�������ʱ��������
 */
public void ReqServerInfo()
{
	ReqInfoSend send=new ReqInfoSend();
 	send.send();
}
}
