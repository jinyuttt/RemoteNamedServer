package nameServerFun;

import java.util.concurrent.ConcurrentHashMap;

import com.Tools.MsgPackTool;


import ProxyExchange.IDataCall;
import nameServerInterface.IServer;

import nameServerInterface.NetData;

/**
 * 接收客户端的信息
 * @author jinyu
 *
 */
public class RecDataCall implements IDataCall {
	
	IServer theIServer_;
	ConcurrentHashMap<String,IServer> serverBox=new ConcurrentHashMap<String,IServer>();
	
public RecDataCall(String serverName,IServer server)
{
	//theIServer_=server;
	serverBox.put(serverName, server);
}

/**
 * 添加服务信息
 * 专门为服务盒子准备
 * @param serverName
 * @param server
 */
public void AddServer(String serverName,IServer server)
{
	serverBox.put(serverName, server);
}
	@Override
	public byte[] RecData(String src,byte[] data) {
	
		DataSys(data);
		return null;
	}
    private void DataSys(byte[]data)
    {
    	StringBuilder error=null;
    	MsgPackTool tool=new MsgPackTool();
    	NetData  netData=tool.Deserialize(data, NetData.class, error);
    	theIServer_=serverBox.get(netData.serverName);
    	if(theIServer_==null)
    	  return;
    	if(netData!=null)
    	{
    		switch(netData.fun_Name)
    		{
    		case "GetData":
    			theIServer_.GetData(netData.data);
    			break;
    		case "SetData":
    			theIServer_.SetData(netData.data);
    			break;
    		case "CallData":
    			theIServer_.CallData(netData.data);
    			break;
    			
    		}
    	}
    }
}
