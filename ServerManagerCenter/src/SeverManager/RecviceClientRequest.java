  
/**    
* 文件名：RecviceClientRequest.java    
*    
* 版本信息：    
* 日期：2017年1月12日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;

/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：RecviceClientRequest    
* 类描述：    接受客户端请求的处理
* 创建人：jinyu    
* 创建时间：2017年1月12日 上午12:45:30    
* 修改人：jinyu    
* 修改时间：2017年1月12日 上午12:45:30    
* 修改备注：    
* @version     
*     
*/
public class RecviceClientRequest implements IRecMsg {
	IDDS_Protocol  protocol=null;
	
	
/**
 *  接收处理处理
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
	 * 保存通讯实例
	 */
	@Override
	public void SaveInstance(Object call) {
		protocol=(IDDS_Protocol)call;
		
	}

}

