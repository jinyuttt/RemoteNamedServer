  
/**    
* �ļ�����AnalysisParam.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��17��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package RequestServerInfo;

import java.util.Arrays;

import Tools.NumberToTool;

/**    
*     
* ��Ŀ���ƣ�NameServerContainer    
* �����ƣ�AnalysisParam    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��17�� ����9:02:44    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��17�� ����9:02:44    
* �޸ı�ע��    
* @version     
*     
*/
public class AnalysisParam {
 public static RequestModel GetParam(byte[]data)
 {
	 //
	 RequestModel curModel=new RequestModel();
	 int cur=data[0];
	 ServerMangerType  curType=Get(cur);
	 if(curType==null)
	 {
		 return null;
	 }
	 else
	 {
		 curModel.managerType=(ServerMangerType) curType;
	 }
	
	 //���Ƴ���
	 int len=0;
	 byte[] lenbyte= NumberToTool.intToByte(len);
	 lenbyte=Arrays.copyOfRange(data, 1, lenbyte.length);
	 len=NumberToTool.byteToInt(lenbyte);
	 //
	 byte[]name=Arrays.copyOfRange(data, 1+lenbyte.length, len);
	 String serverName=new String(name);
	 curModel.serverName=serverName;
	 // ��ȡ����
	 //
	 int index=1+lenbyte.length+len;
	 curModel.dataParam=Arrays.copyOfRange(data, index, data.length-index+1);
	 
    return curModel;
	 
 }
 
 private static ServerMangerType Get(int index)
 {
	 ServerMangerType[] values= ServerMangerType.values();
	 for(ServerMangerType cur:values)
	 {
		 if(cur.ordinal()==index)
		 {
			 return cur;
		 }
	 }
	 return null;
 }
}
