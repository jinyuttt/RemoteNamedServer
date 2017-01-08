package TestPro;

import java.io.IOException;
import java.util.HashMap;

import DataProcess.DataCenter;
import DataProcess.RecData;
import nameServerFrame.ServiceRegistry;
import nameServerInterface.IServer;


public class TestMainServer {

	public static void main(String[] args) throws IOException {
	
		DataCenter.call=new ProcessData();
		IServer obj=new RecData(); 
		
		String url="Test:-h 127.0.0.1 -p 4444 -t udp";
		HashMap<String, String> hamp=new HashMap<String, String>();
		
		ServiceRegistry.AddServers(obj, url, hamp);


	
		System.in.read();
     
	}

}
