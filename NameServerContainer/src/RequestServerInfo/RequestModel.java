  
/**    
* �ļ�����RequestModel.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��17��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package RequestServerInfo;

  
/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RequestModel    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��17�� ����8:42:55    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��17�� ����8:42:55    
* �޸ı�ע��    
* @version     
*     
*/
public class RequestModel {
  public String  serverName;
  public byte[] dataParam;
  public byte[]  dataReturn;
  public ServerMangerType   managerType=ServerMangerType.ClientDirectMode;
 
}
