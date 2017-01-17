package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;

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
	InitPlugin.GetInstance().InitRecSeverInfo();
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
	
	InitPlugin.GetInstance().ReqServerInfo();

}
}
