package SeverManager;

import DDS_Transfer.IRecMsg;
import ProxyExchange.IDataCallBack;
import ServerRecInfo.RecServerInfo;


public class RecviceService implements IRecMsg {
	IDataCallBack  sinfo=new RecServerInfo();
	@Override
	public void RecData(String address, byte[] data) {
		
		sinfo.DataRec(address, data);
	}

	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IRecMsg#SaveInstance(java.lang.Object)    
	*/  
	
	@Override
	public void SaveInstance(Object call) {
		// TODO Auto-generated method stub
		
	}

}
