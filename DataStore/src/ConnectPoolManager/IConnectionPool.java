package ConnectPoolManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jinyu
 *
 */
/**
 * @author jinyu
 *
 */
public interface IConnectionPool {
	 // �������  
	
    /**
     * �������  
     * @return
     */
    public Connection  getConnection();  
    // ��õ�ǰ����  
    
    /**
     * ��õ�ǰ����  
     * @return
     */
    public Connection getCurrentConnecton();  
    // ��������  
    
    /**
     * ��������  
     * @param conn
     * @throws SQLException
     */
    public void releaseConn(Connection conn) throws SQLException; 
    
    public void releaseCurConn() throws SQLException;
    
    // �������  
    
    /**
     * �������
     */
    public void destroy();  
    // ���ӳ��ǻ״̬  
    
    /**
     * ���ӳ��ǻ״̬ 
     * @return
     */
    public boolean isActive();  
    
    // ��ʱ����������ӳ�  
    
    /**
     * ��ʱ����������ӳ�  
     */
    public void cheackPool();
}
