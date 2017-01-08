package nameServerContainer;

import java.util.ArrayList;
import java.util.Iterator;


import serverStruct.ServerBinds;


/**
 * 添加服务
 * @author jinyu
 *
 */
public class ServerInfoSave {
	
  /**
 * @param server
 * 添加服务保持
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
		//保存
		ArrayList<ServerBinds> listObj=new ArrayList<ServerBinds>();
		listObj.add(server);
		ServersContains.servers.put(server.name, listObj);
	}
	  
  }


/**
 * 移出服务
 * @param name  名称
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
 * 获取当前服务信息
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
