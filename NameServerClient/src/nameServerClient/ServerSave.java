package nameServerClient;

import nameServerContainer.InitPlugin;

/**
 * ������Ϣ
 * @author jinyu
 *
 */
public class ServerSave {
 static	InitPlugin plugin=null;

   /**
    * ��ȡ������Ϣ
 * @return  ���������Ϣ
 */
public static InitPlugin GetServerInfo()
   {
	   if(plugin==null)
	   {
		   plugin=InitPlugin.GetInstance();
		   plugin.InitRecSeverInfo();
		   plugin.ReqServerInfo();
		   try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   return plugin;
   }
	
}
