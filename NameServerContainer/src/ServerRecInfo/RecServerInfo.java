package ServerRecInfo;

import com.Tools.MsgPackTool;

import ProxyExchange.IDataCallBack;
import nameServerContainer.ServerInfoSave;
import serverStruct.ServerBinds;

/**
 * �����鲥
 * ���շ����ķ�����Ϣ
 * @author jinyu
 *
 */
public class RecServerInfo implements IDataCallBack{

	@Override
	public void DataRec(String src, byte[] data) {
	  //
	 
		AddServerInfo(data);
	}
	  /**
	   * ������յķ�����Ϣ
	 * @param data
	 */
	private void  AddServerInfo(byte[]data)
	  {
		try
		{
			StringBuilder error=new StringBuilder();
		    ServerInfoSave obj=new ServerInfoSave();
			MsgPackTool tool=new MsgPackTool();
	 		ServerBinds info=tool.Deserialize(data, ServerBinds.class, error);
			obj.Add(info);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	  }


}
