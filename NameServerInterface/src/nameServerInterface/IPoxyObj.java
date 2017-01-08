package nameServerInterface;



/**
 * 客户端代理接口
 * @author jinyu
 *
 */
public interface IPoxyObj {
	
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

 
}
