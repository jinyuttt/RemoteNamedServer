package nameServerInterface;

import java.util.HashMap;

/**
 * ����
 * @author jinyu
 *
 */
public interface IServer{
	
	 /**
	  * ��ȡ����
	 * @param data
	 */
	public void GetData(byte[]data);
	
	
	 /**
	  * �������
	 * @param data
	 */
	public void SetData(byte[]data);
	
	 /**
	  * ��������
	 * @param data
	 */
	public void  CallData(byte[]data);
	
	 /**
	  * ��������
	 * @param name
	 * @param hamp
	 */
	public void  Start(String name,HashMap<String,String> hamp);
}
