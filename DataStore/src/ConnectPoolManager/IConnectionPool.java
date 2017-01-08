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
	 // 获得连接  
	
    /**
     * 获得连接  
     * @return
     */
    public Connection  getConnection();  
    // 获得当前连接  
    
    /**
     * 获得当前连接  
     * @return
     */
    public Connection getCurrentConnecton();  
    // 回收连接  
    
    /**
     * 回收连接  
     * @param conn
     * @throws SQLException
     */
    public void releaseConn(Connection conn) throws SQLException; 
    
    public void releaseCurConn() throws SQLException;
    
    // 销毁清空  
    
    /**
     * 销毁清空
     */
    public void destroy();  
    // 连接池是活动状态  
    
    /**
     * 连接池是活动状态 
     * @return
     */
    public boolean isActive();  
    
    // 定时器，检查连接池  
    
    /**
     * 定时器，检查连接池  
     */
    public void cheackPool();
}
