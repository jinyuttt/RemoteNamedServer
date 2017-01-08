package RequestServerInfo;

import ProxyExchange.MulticastSend;

/**
 * @author jinyu
 * 发送服务信息请求
 */
public class ReqInfoSend {
	
/**
 * 发送服务请求信息
 */
public void send()
{
	
	MulticastSend client=new MulticastSend();
	client.port=224;
    client.SendData("initServerInfo".getBytes());
}
}
