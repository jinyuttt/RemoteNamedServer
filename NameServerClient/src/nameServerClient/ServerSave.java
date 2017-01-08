package nameServerClient;

import nameServerContainer.InitPlugin;

/**
 * 服务信息
 * @author jinyu
 *
 */
public class ServerSave {
 static	InitPlugin plugin=null;

   /**
    * 获取服务信息
 * @return  服务组件信息
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
