package RequestServerInfo;

import ProxyExchange.MulticastSend;

/**
 * @author jinyu
 * ���ͷ�����Ϣ����
 */
public class ReqInfoSend {
	
/**
 * ���ͷ���������Ϣ
 */
public void send()
{
	
	MulticastSend client=new MulticastSend();
	client.port=224;
    client.SendData("initServerInfo".getBytes());
}
}
