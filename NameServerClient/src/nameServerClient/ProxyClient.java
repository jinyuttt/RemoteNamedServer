package nameServerClient;


import nameServerInterface.IPoxyObj;
import serverStruct.ServerBinds;


/**
 * 客户端代理
 * @author jinyu
 *
 */
public class ProxyClient {
	
/**
 * 获取服务端代理
 * @param name 服务名称
 * @param error 错误信息
 * @return 服务代理
 */
public static IPoxyObj CastObj(String name,StringBuilder error)

{
	error=new StringBuilder();
	if(name==null)
	{
		error.append("名称不能为空");
		return null;
		
	}
	//
	ServerBinds serverinfo=ServerSave.GetServerInfo().GetCur(name);
	if(serverinfo!=null)
	{
		ServerPorxy tempPorxy=new ServerPorxy(serverinfo.address,serverinfo.port,serverinfo.communicationType);
	    ServerClient client=new ServerClient();
		client.proxy=tempPorxy;
		client.ServerName=name;
	    return client;
	}
	else
	{
		error.append("没有该服务");
		return null;
	}
}
}
