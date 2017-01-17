  
/**    
* �ļ�����ProceRequest.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��17��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
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
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�ProceRequest    
* ��������     �������͵�����
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��17�� ����8:40:15    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��17�� ����8:40:15    
* �޸ı�ע��    
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
