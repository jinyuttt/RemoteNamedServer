package nameServerContainer;

import java.util.ArrayList;
import java.util.Iterator;


import serverStruct.ServerBinds;


/**
 * ��ӷ���
 * @author jinyu
 *
 */
public class ServerInfoSave {
	
  /**
 * @param server
 * ��ӷ��񱣳�
 */
public void Add(ServerBinds server)
  {
	if(ServersContains.servers.containsKey(server.name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(server.name);
		Iterator<ServerBinds>	 it=listObj.iterator();
		while(it.hasNext())
		{
			if(it.next().sid==server.sid)
			{
				return ;
			}
		}
		//
		listObj.add(server);
	}
	else
	{
		//����
		ArrayList<ServerBinds> listObj=new ArrayList<ServerBinds>();
		listObj.add(server);
		ServersContains.servers.put(server.name, listObj);
	}
	  
  }


/**
 * �Ƴ�����
 * @param name  ����
 * @param sid 
 */
public void Remove(String name, String  sid)
{
	if(ServersContains.servers.containsKey(name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(name);
		Iterator<ServerBinds>it=listObj.iterator();
		while(it.hasNext())
		{
			if(it.next().sid==sid)
			{
				it.remove();
				break;
			}
		}
		//
		
	}
}
/**
 * ��ȡ��ǰ������Ϣ
 * @param name
 * @return
 */
public ArrayList<ServerBinds> GetCur(String name)
{
	if(ServersContains.servers.containsKey(name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(name);
		return listObj;
		//
	}
	else
	{
		return null;
	}
}
}
