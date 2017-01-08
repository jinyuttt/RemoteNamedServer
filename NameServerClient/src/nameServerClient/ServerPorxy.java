package nameServerClient;

import ProxyExchange.BridgeClient;

/**
 * @author jinyu
 * 用于连接服务端
 * 中间代理类
 */
public class ServerPorxy {

	BridgeClient client=new BridgeClient();
	public ServerPorxy(String address, int port,String type_name) {
		
		client.IP=address;
		client.port=port;
		client.type_name=type_name;
		client.Connect();
	}

	public byte[] GetData(byte[] para) {
	
	byte[]data=	client.RecData(para);
	return data;
	}

	public void SetData(byte[] data) {
	
		client.SendData(data);
		
	}

}
