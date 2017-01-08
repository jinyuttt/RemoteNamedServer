package RequestServerInfo;

import ProxyExchange.IDataCallBack;
import ProxyExchange.MulticastRec;
import ServerRecInfo.RecRequestInfo;



/**
 * @author jinyu
 * 初始化接收客户端启动时的服务请求
 */
public class ReqServerInfo {
	IDataCallBack callData=new RecRequestInfo();
	
/**
 * 初始化接收信息
 * 接收客户端获取服务信息的请求
 */
public void RecReqInfo()
{
	MulticastRec server=new MulticastRec();
	server.port=224;
	
	server.Start(callData);
}
}
