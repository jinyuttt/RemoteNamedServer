  
/**    
* �ļ�����RecviceClientRequest.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��12��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceClientRequest    
* ��������    ���ܿͻ�������Ĵ���
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��12�� ����12:45:30    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��12�� ����12:45:30    
* �޸ı�ע��    
* @version     
*     
*/
public class RecviceClientRequest implements IRecMsg {
	IDDS_Protocol  protocol=null;
	
	
/**
 *  ���մ�����
 */
	@Override
	public void RecData(String address, byte[] data) {
	
		ProceRequest  proce=new ProceRequest();
	byte[] param=	proce.RespondClient(address, data);
	if(param!=null)
	{
		if(protocol!=null)
		{
			protocol.ServerSocketSend(param);
			protocol.ClientSocketClose();  
		}
	}
	}

	  
	/**
	 * ����ͨѶʵ��
	 */
	@Override
	public void SaveInstance(Object call) {
		protocol=(IDDS_Protocol)call;
		
	}

}

