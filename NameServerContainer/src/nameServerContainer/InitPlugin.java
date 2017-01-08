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
  * 获取服务信息
 * @param name 服务名称
 * @return 服务组件信息
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
		//启用负载均衡
		cirALL cir=new cirALL();
		Object[] objarry=listObj.toArray();
		CopyOnWriteArrayList<Object> cal = new CopyOnWriteArrayList<Object>(objarry);
	    Object obj=	cir.GetCurNodeInfo(cal);
	    ServerBinds info=(ServerBinds)obj; 
	    return info;
	}
		
	
	}
	
	/**
	 * 初始化接收服务信息
	 * 
	 */
	public void InitRecSeverInfo()
	{
		serverAdd.AddServer();
	}
	
	
	/**
	 * 服务端把新添加的服务发送出去
	 * @param info
	 */
	public void NoticeServerInfo(ServerBinds info)
	{
		serverAdd.SendInfo(info);
	}
	
/**
 * 客户端启动时发送请求
 */
public void ReqServerInfo()
{
	ReqInfoSend send=new ReqInfoSend();
 	send.send();
}
}
