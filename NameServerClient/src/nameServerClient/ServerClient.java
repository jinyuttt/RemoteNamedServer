package nameServerClient;

import com.Tools.MsgPackTool;

import nameServerInterface.IPoxyObj;
import nameServerInterface.NetData;


 /**
 * @author jinyu
 * 服务通信客户端代理实例类
 */
class ServerClient implements IPoxyObj{

	public ServerPorxy proxy;
	public String ServerName="";
	@Override
	public void GetData(byte[] data) {
	
		
		byte[]dataStream=NetStream("GetData",data);
		proxy.GetData(dataStream);
	}

	@Override
	public void SetData(byte[] data) {
	
		byte[]dataStream=NetStream("SetData",data);
		proxy.SetData(dataStream);
	}

	@Override
	public void CallData(byte[] data) {
		
		byte[]dataStream=NetStream("CallData",data);
		proxy.SetData(dataStream);
	}
   private byte[] NetStream(String name,byte[]data)
   {
	    StringBuilder error=new StringBuilder();
	    NetData curData=new NetData();
	    curData.fun_Name=name;
	    curData.serverName=ServerName;
		curData.data=data;
		MsgPackTool tool=new MsgPackTool();
	    byte[] curBytes=	tool.Serialize(curData, error);
	    return curBytes;
   }
}
