  
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
public byte[]   ask(String address,byte[]data)
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
public void ServerFun(byte[]data)
{
	StringBuilder error=new StringBuilder();
    IPoxyObj proxy=	ProxyClient.CastObj("Test", error);
    if(proxy!=null)
    {
        proxy.SetData(data);
    }
}
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
