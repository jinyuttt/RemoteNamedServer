package nameServerFrame;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import nameServerContainer.InitPlugin;
import nameServerContainer.ServerInfoSave;
import nameServerInterface.IServer;
import serverStruct.ServerBinds;


/**
 * 服务盒子集合
 * @author jinyu
 *
 */
public class ServerBoxSet {
 static	ConcurrentHashMap<String,ServerBox> serverBox=new ConcurrentHashMap<String,ServerBox>();

 
 /**
  * 添加服务盒子
 * @param boxName 盒子名称
 * @param strconnect 盒子连接字符串
 */
public static void AddServerBox(String boxName,String strconnect)
 {
	 
	 Sever_BindsInfo binds = AnalysisConnection.Aysy(strconnect);
	 ServerBox box=new ServerBox(binds);
	 serverBox.put(boxName, box);
 }

 /**
  * 向服务盒子添加服务
 * @param boxName 盒子名称
 * @param name 服务名称
 * @param obj 服务实例
 * 返回fase则没有改服务盒子
 */
public static boolean AddServer(String boxName,String name,IServer obj,HashMap<String,String> hamp)
 {
	 ServerBox cur= serverBox.get(boxName);
	 if(cur!=null)
	 {
		  cur.AddServer(name, obj);
		  //向客户端注册信息
			ServerBinds  info=new ServerBinds();
			info.address=cur.ProxyInfo.address;
			info.port=cur.ProxyInfo.port;
			info.name=name;
			info.sid= UUID.randomUUID().toString();
			info.communicationType=cur.ProxyInfo.t_type;
			
			InitPlugin.GetInstance().NoticeServerInfo(info);
		     //将服务端信息保存起来，用于客户端请求
		    ServerInfoSave saveFrame=new ServerInfoSave();
		    saveFrame.Add(info);
		    if(hamp!=null)
		    {
		    	obj.Start(info.name, hamp);
		    }
		   return true;
	 }
	 else
	 {
		 return false;
	 }
		    
 }
}
