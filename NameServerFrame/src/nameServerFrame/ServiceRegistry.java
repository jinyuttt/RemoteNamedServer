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
	 * ע�����
	 * hamp ������Ϣ
	 * @param server ����ʵ��
	 * @param connect ������Ϣ�� -h 127.0.0.1 -p 223 t tcp
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
		// ����������ܿͻ��˵���
		Sever_BindsInfo binds = AnalysisConnection.Aysy(connect);
		ServerPorxy proxy = new ServerPorxy(binds.address, binds.port,binds.t_type);
		
		proxy.InitServerThread(binds.name,server);
		
       //�������ͨ�Ŵ���
		ServerInfo server_Info = new ServerInfo();
		server_Info.porxy = proxy;
		server_Info.server = server;
        
		//��ͻ���ע����Ϣ
		ServerBinds  info=new ServerBinds();
		info.address=binds.address;
		info.port=binds.port;
		info.name=binds.name;
		info.sid= UUID.randomUUID().toString();
		info.communicationType=binds.t_type;
		
	    plugin.NoticeServerInfo(info);
	     //���������Ϣ�������������ڿͻ�������
	    ServerInfoSave saveFrame=new ServerInfoSave();
	    saveFrame.Add(info);
		 //��ʱ�䱣��
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
		//���ӽ�ȥ�����еķ������������ʱ��Ӧ�����Ӻ�
		ServerBoxSet.AddServer(boxName, serverName, server, hamp);
	
	}
	
	

}