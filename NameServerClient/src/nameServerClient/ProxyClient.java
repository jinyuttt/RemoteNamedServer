package nameServerClient;


import nameServerInterface.IPoxyObj;
import serverStruct.ServerBinds;


/**
 * �ͻ��˴���
 * @author jinyu
 *
 */
public class ProxyClient {
	
/**
 * ��ȡ����˴���
 * @param name ��������
 * @param error ������Ϣ
 * @return �������
 */
public static IPoxyObj CastObj(String name,StringBuilder error)

{
	error=new StringBuilder();
	if(name==null)
	{
		error.append("���Ʋ���Ϊ��");
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
		error.append("û�и÷���");
		return null;
	}
}
}
