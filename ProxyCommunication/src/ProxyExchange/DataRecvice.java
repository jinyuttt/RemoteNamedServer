package ProxyExchange;

import DDS_Transfer.IRecMsg;

//��������
public class DataRecvice implements IRecMsg {
    public 	IDataCall calldata=null;
	@Override
	public void RecData(String address, byte[] data) {
		if(calldata!=null)
		{
			calldata.RecData(address, data);
		}
           
	}

}
