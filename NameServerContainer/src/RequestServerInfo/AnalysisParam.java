  
/**    
* 文件名：AnalysisParam.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package RequestServerInfo;

import java.util.Arrays;

import Tools.NumberToTool;

/**    
*     
* 项目名称：NameServerContainer    
* 类名称：AnalysisParam    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午9:02:44    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午9:02:44    
* 修改备注：    
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
	
	 //名称长度
	 int len=0;
	 byte[] lenbyte= NumberToTool.intToByte(len);
	 lenbyte=Arrays.copyOfRange(data, 1, lenbyte.length);
	 len=NumberToTool.byteToInt(lenbyte);
	 //
	 byte[]name=Arrays.copyOfRange(data, 1+lenbyte.length, len);
	 String serverName=new String(name);
	 curModel.serverName=serverName;
	 // 获取参数
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
