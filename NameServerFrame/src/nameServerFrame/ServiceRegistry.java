package nameServerFrame;

import java.util.HashMap;
import java.util.UUID;

import RequestServerInfo.ReqServerInfo;
import nameServerContainer.InitPlugin;
import nameServerContainer.ServerInfoSave;
import nameServerInterface.IServer;

import serverStruct.ServerBinds;

public class ServiceRegistry {
	  static InitPlugin plugin = null;
     static ReqServerInfo reqInfo=null;
	public ServiceRegistry() {
		
	}

	/**
	 * 注册服务
	 * hamp 服务信息
	 * @param server 服务实例
	 * @param connect 连接信息， -h 127.0.0.1 -p 223 t tcp
	 */
	
	public static void AddServers(IServer server, String connect,HashMap<String,String> hamp) {
		//
		
		if(reqInfo==null)
		{
			reqInfo=new ReqServerInfo();
			reqInfo.RecReqInfo();
	    }
		if(plugin==null)
		{
		 plugin=InitPlugin.GetInstance();
		}
		// 启动服务接受客户端调用
		Sever_BindsInfo binds = AnalysisConnection.Aysy(connect);
		ServerPorxy proxy = new ServerPorxy(binds.address, binds.port,binds.t_type);
		
		proxy.InitServerThread(binds.name,server);
		
       //保存服务及通信代理
		ServerInfo server_Info = new ServerInfo();
		server_Info.porxy = proxy;
		server_Info.server = server;
        
		//向客户端注册信息
		ServerBinds  info=new ServerBinds();
		info.address=binds.address;
		info.port=binds.port;
		info.name=binds.name;
		info.sid= UUID.randomUUID().toString();
		info.communicationType=binds.t_type;
		
	    plugin.NoticeServerInfo(info);
	     //将服务端信息保存起来，用于客户端请求
	    ServerInfoSave saveFrame=new ServerInfoSave();
	    saveFrame.Add(info);
		 //长时间保持
	    ServerInstances.servers.put(info.name, server_Info);
	    
	    if(hamp!=null)
	    {
	    	server.Start(info.name, hamp);
	    }
	}
	public static void AddServeBox(String boxName,String serverName, IServer server,HashMap<String,String> hamp) {
		//
		if(reqInfo==null)
		{
			reqInfo=new ReqServerInfo();
			reqInfo.RecReqInfo();
	    }
		if(plugin==null)
		{
		 plugin=InitPlugin.GetInstance();
		// plugin.InitRecSeverInfo();
		}
		//添加进去，所有的服务盒子在启动时都应该添加好
		ServerBoxSet.AddServer(boxName, serverName, server, hamp);
	
	}
	
	

}
