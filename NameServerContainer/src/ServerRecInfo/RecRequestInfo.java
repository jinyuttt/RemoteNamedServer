package ServerRecInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;



import ProxyExchange.IDataCallBack;
import nameServerContainer.ServersContains;
import serverStruct.ServerBinds;

/**
 * 接收客户端请求把服务信息发送出去
 * @author jinyu
 *
 */
public class RecRequestInfo implements IDataCallBack {

	@Override
	public void DataRec(String src, byte[] data) {
		
		SendInfo(data);
	}

	/**
	 * 发送当前的服务信息
	 * @param byteData
	 */
	private void SendInfo(byte[] byteData)
	{
		//
		String  req=new String(byteData);
		if(req.equals("initServerInfo"))
		{
			AddServerInstance register=new AddServerInstance();
			for(Entry<String, ArrayList<ServerBinds>> entry :ServersContains.servers.entrySet())
			{
				ArrayList<ServerBinds> temp=entry.getValue();
				Iterator<ServerBinds> it=	temp.iterator();
				while(it.hasNext())
				{
				  register.SendInfo(it.next());
				}
			}
		}
	}



}
