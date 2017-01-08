package DataProcess;

import java.util.HashMap;

import com.Tools.MsgPackTool;

import DataModel.TreansferModel;
import nameServerInterface.IServer;

public class RecData implements IServer{

	@Override
	public void GetData(byte[] data) {
	
		
	}

	@Override
	public void SetData(byte[] data) {
		
		StringBuilder error=null;
		MsgPackTool tool=new MsgPackTool();
		TreansferModel model=tool.Deserialize(data, TreansferModel.class, error);
		DataCenter.AddData(model);
	}

	@Override
	public void CallData(byte[] data) {
		
		
	}

	@Override
	public void Start(String name, HashMap<String, String> hamp) {
		
		
	}

}
