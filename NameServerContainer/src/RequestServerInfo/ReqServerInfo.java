package RequestServerInfo;

import ProxyExchange.IDataCallBack;
import ProxyExchange.MulticastRec;
import ServerRecInfo.RecRequestInfo;



/**
 * @author jinyu
 * ��ʼ�����տͻ�������ʱ�ķ�������
 */
public class ReqServerInfo {
	IDataCallBack callData=new RecRequestInfo();
	
/**
 * ��ʼ��������Ϣ
 * ���տͻ��˻�ȡ������Ϣ������
 */
public void RecReqInfo()
{
	MulticastRec server=new MulticastRec();
	server.port=224;
	
	server.Start(callData);
}
}
