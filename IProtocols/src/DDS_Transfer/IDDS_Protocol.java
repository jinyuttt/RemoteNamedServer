package DDS_Transfer;



public interface IDDS_Protocol {
/**
 * 发送数据
 * @param adress 服务端地址
 * @param data 发送的数据
 * @return
 */
public boolean SendData(String adress,byte[]data);

/**
 * 接收数据
 * @param topic 主题
 * @param Address 接收地址
 * @param rec 回发端口
 */
public void RecData(String Address,IRecMsg rec);

/**
 * 接收数据

 */
public void Close();

public boolean Connect();



/**
 * 获取传送地址
 * @return
 */
public String GetBindAddress();

/**
 * 设置绑定地址 (作废）
 * @param addr
 */
public void SetBindAddress(String addr);

//返回使用的地址
public  String GetLocalAddress();

}
