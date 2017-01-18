package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProtocolsManager.ProtocolManager;
import nameServerContainer.InitPlugin;

/**    
* �ļ�����ServerManager.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��12��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�ServerManager    
* ��������     �������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��12�� ����12:04:01    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��12�� ����12:04:01    
* �޸ı�ע��    
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
	 *   ��ʼ�����ܷ������Ϣ  
	* @param   name    
	   
	* @param  @return       
	   
	* @return String    DOM����    
	   
	* @Exception �쳣����    
	   
	* @since  CodingExample��Ver(���뷶���鿴) 1.1
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
   
* InitClientRequest  ��ʼ�����ܿͻ�������   
   
* TODO(����������������������� �C ��ѡ)    
  
   
* @param   name    
   
  
* @return String    DOM����    
   
* @Exception �쳣����    
   
* @since  CodingExample��Ver(���뷶���鿴) 1.1
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
