package nameServerInterface;

import java.util.HashMap;

/**
 * 服务
 * @author jinyu
 *
 */
public interface IServer{
	
	 /**
	  * 获取数据
	 * @param data
	 */
	public void GetData(byte[]data);
	
	
	 /**
	  * 添加数据
	 * @param data
	 */
	public void SetData(byte[]data);
	
	 /**
	  * 返回数据
	 * @param data
	 */
	public void  CallData(byte[]data);
	
	 /**
	  * 启动服务
	 * @param name
	 * @param hamp
	 */
	public void  Start(String name,HashMap<String,String> hamp);
}
