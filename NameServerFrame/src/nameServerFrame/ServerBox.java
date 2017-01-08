package nameServerFrame;

import java.util.concurrent.ConcurrentHashMap;

import nameServerInterface.IServer;



/**
 * 服务盒子
 * 一个盒子一个中间代理，一个中间代理启动一个接收
 * @author jinyu
 *
 */
public class ServerBox {
	public ServerBox(Sever_BindsInfo proxyInfo)
	{
		if(serverProxy==null)
		{
			serverProxy=new ServerPorxy(proxyInfo.address,proxyInfo.port,proxyInfo.t_type);
			ProxyInfo=proxyInfo;
		}
	}
  private	ConcurrentHashMap<String,IServer> serverBox=new ConcurrentHashMap<String,IServer>();
  private	ServerPorxy serverProxy=null;
  
  /**
 * 服务端代理信息（IP,Port,传输）
 */
    public  Sever_BindsInfo ProxyInfo=null;
	public void AddServer(String serverName,IServer server)
	{
		serverBox.put(serverName, server);
		serverProxy.InitServerThread(serverName, server);
	}
	public IServer GetServer(String serverName)
	{
		return serverBox.get(serverName);
	}
}
