package DDS_Transfer;



public interface IDDS_Protocol {
/**
 * ��������
 * @param adress ����˵�ַ
 * @param data ���͵�����
 * @return
 */
public boolean SendData(String adress,byte[]data);

/**
 * ��������
 * @param topic ����
 * @param Address ���յ�ַ
 * @param rec �ط��˿�
 */
public void RecData(String Address,IRecMsg rec);

/**
 * ��������

 */
public void Close();

public boolean Connect();



/**
 * ��ȡ���͵�ַ
 * @return
 */
public String GetBindAddress();

/**
 * ���ð󶨵�ַ (���ϣ�
 * @param addr
 */
public void SetBindAddress(String addr);

//����ʹ�õĵ�ַ
public  String GetLocalAddress();

}