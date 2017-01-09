package TestPro;

import java.util.HashMap;

import nameServerInterface.IServer;

public class ServerCount implements IServer{

	@Override
	public void GetData(byte[] data) {
		
		
	}

	@Override
	public void SetData(byte[] data) {
		
		
	}

	@Override
	public void CallData(byte[] data) {
	 String ss=new String(data);
	 System.out.println(ss);
		
	}

	@Override
	public void Start(String name, HashMap<String, String> hamp) {
	
		
	}

}
