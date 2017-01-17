package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;

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
	InitPlugin.GetInstance().InitRecSeverInfo();
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
	
	InitPlugin.GetInstance().ReqServerInfo();

}
}
