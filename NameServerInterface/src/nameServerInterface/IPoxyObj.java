package nameServerInterface;



/**
 * �ͻ��˴���ӿ�
 * @author jinyu
 *
 */
public interface IPoxyObj {
	
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

 
}
