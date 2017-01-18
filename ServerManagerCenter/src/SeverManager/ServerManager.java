package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProtocolsManager.ProtocolManager;
import nameServerContainer.InitPlugin;

/**    
* 文件名：ServerManager.java    
*    
* 版本信息：    
* 日期：2017年1月12日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/

/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：ServerManager    
* 类描述：     管理服务
* 创建人：jinyu    
* 创建时间：2017年1月12日 上午12:04:01    
* 修改人：jinyu    
* 修改时间：2017年1月12日 上午12:04:01    
* 修改备注：    
* @version     
*     
*/
public class ServerManager {
	 IDDS_Protocol curObj =null;
	 IRecMsg recService =null;
	 IRecMsg recClientRequest =null;
	 IDDS_Protocol recClientReq=null;
	 IDDS_Protocol recServerRsp=null;
	/**
	 * 
	 *   初始化接受服务端信息  
	* @param   name    
	   
	* @param  @return       
	   
	* @return String    DOM对象    
	   
	* @Exception 异常对象    
	   
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
public  void InitServiceRec(String ip,int port,String typeName)
{
	//InitPlugin.GetInstance().InitRecSeverInfo();
	 try {
		 recService=new RecviceService();
		 recServerRsp=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject("UDP");
		 recServerRsp.RecData("127.0.0.1", recService);
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
/**
 * 
   
* InitClientRequest  初始化接受客户端请求   
   
* TODO(这里描述这个方法适用条件 – 可选)    
  
   
* @param   name    
   
  
* @return String    DOM对象    
   
* @Exception 异常对象    
   
* @since  CodingExample　Ver(编码范例查看) 1.1
 */
public void InitClientRequest(String ip,int port,String typeName)
{
	
	//InitPlugin.GetInstance().ReqServerInfo();
	try {
		recClientRequest=new RecviceClientRequest();
		 recClientReq=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject("UDP");
		 recClientReq.RecData("127.0.0.1", recClientRequest);
		
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}
