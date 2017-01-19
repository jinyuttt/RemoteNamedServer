  
/**    
* 文件名：ProceRequest.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;




import RequestServerInfo.AnalysisParam;
import RequestServerInfo.RequestModel;
import Tools.MsgPackTool;
import nameServerClient.ProxyClient;
import nameServerClient.ServerSave;
import nameServerInterface.IPoxyObj;
import serverStruct.ServerBinds;


/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：ProceRequest    
* 类描述：     解析传送的内容
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午8:40:15    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午8:40:15    
* 修改备注：    
* @version     
*     
*/
public class ProceRequest {
	
	/**
	 * 
	* @Name: RespondClient 
	* @Description: 接收客户端数据，处理客户端请求 
	* @param address 数据来源IP
	* @param data  数据
	
	* @return byte[]   返回给客户端的数据
	* @throws
	 */
public byte[]   RespondClient (String address,byte[]data)
{
	byte[] param=null;
	RequestModel model=AnalysisParam.GetParam(data);
	switch(model.managerType)
	{
	case ClientDirectMode:
	   break;
	case ServerDirectMode:
		ServerFun(model.dataParam);
		break;
	case ServerIndirectMode:
		param=ClientFun(model.dataParam);
		break;
	default:
		break;
	}
	return param;
}

/**
 * 
* @Name: ServerFun 
* @Description: 获取代理传递数据 
* @param data  参数说明 
* void    返回类型 
* @throws
 */
public void ServerFun(byte[]data)
{
	StringBuilder error=new StringBuilder();
    IPoxyObj proxy=	ProxyClient.CastObj("Test", error);
    if(proxy!=null)
    {
        proxy.SetData(data);
    }
}

/**
 * 
* @Name: ClientFun 
* @Description:  获取服务信息，转出地址
* @param name  传来的参数
* @return  参数说明 
* @return byte[]    服务信息
* @throws
 */
public byte[] ClientFun(byte[] name)
{
	String serverName=new String(name);
	 StringBuilder error=new StringBuilder();
	 ServerBinds serverinfo=ServerSave.GetServerInfo().GetCur(serverName);
	 MsgPackTool tool=new MsgPackTool();
	 byte[]bytes= tool.Serialize(serverinfo, error);
	 return bytes;
	
}
}
