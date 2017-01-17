  
/**    
* 文件名：RequestModel.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package RequestServerInfo;

  
/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：RequestModel    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午8:42:55    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午8:42:55    
* 修改备注：    
* @version     
*     
*/
public class RequestModel {
  public String  serverName;
  public byte[] dataParam;
  public byte[]  dataReturn;
  public ServerMangerType   managerType=ServerMangerType.ClientDirectMode;
 
}
