package nameServerFrame;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import nameServerContainer.InitPlugin;
import nameServerContainer.ServerInfoSave;
import nameServerInterface.IServer;
import serverStruct.ServerBinds;


/**
 * ������Ӽ���
 * @author jinyu
 *
 */
public class ServerBoxSet {
 static	ConcurrentHashMap<String,ServerBox> serverBox=new ConcurrentHashMap<String,ServerBox>();

 
 /**
  * ��ӷ������
 * @param boxName ��������
 * @param strconnect ���������ַ���
 */
public static void AddServerBox(String boxName,String strconnect)
 {
	 
	 Sever_BindsInfo binds = AnalysisConnection.Aysy(strconnect);
	 ServerBox box=new ServerBox(binds);
	 serverBox.put(boxName, box);
 }

 /**
  * ����������ӷ���
 * @param boxName ��������
 * @param name ��������
 * @param obj ����ʵ��
 * ����fase��û�иķ������
 */
public static boolean AddServer(String boxName,String name,IServer obj,HashMap<String,String> hamp)
 {
	 ServerBox cur= serverBox.get(boxName);
	 if(cur!=null)
	 {
		  cur.AddServer(name, obj);
		  //��ͻ���ע����Ϣ
			ServerBinds  info=new ServerBinds();
			info.address=cur.ProxyInfo.address;
			info.port=cur.ProxyInfo.port;
			info.name=name;
			info.sid= UUID.randomUUID().toString();
			info.communicationType=cur.ProxyInfo.t_type;
			
			InitPlugin.GetInstance().NoticeServerInfo(info);
		     //���������Ϣ�������������ڿͻ�������
		    ServerInfoSave saveFrame=new ServerInfoSave();
		    saveFrame.Add(info);
		    if(hamp!=null)
		    {
		    	obj.Start(info.name, hamp);
		    }
		   return true;
	 }
	 else
	 {
		 return false;
	 }
		    
 }
}
