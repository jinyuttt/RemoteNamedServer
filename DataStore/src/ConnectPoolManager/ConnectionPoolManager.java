package ConnectPoolManager;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPoolManager {
	  // ���ӳش��  
     ConcurrentHashMap<String,IConnectionPool> pools = new ConcurrentHashMap<String, IConnectionPool>();  
      
    // ��ʼ��  
    public ConnectionPoolManager(){  
        init();  
    }  
    // ����ʵ��  
    public static ConnectionPoolManager getInstance(){  
        return Singtonle.instance;  
    }  
    private static class Singtonle {  
        private static ConnectionPoolManager instance =  new ConnectionPoolManager();  
    }  
      
      
    // ��ʼ�����е����ӳ�  
    public void init(){  
        for(int i =0;i<DBInitInfo.beans.size();i++){  
            DBbean bean = DBInitInfo.beans.get(i);  
            ConnectionPool pool = new ConnectionPool(bean);  
            if(pool != null){  
                pools.put(bean.getPoolName(), pool);  
                System.out.println("Info:Init connection successed ->" +bean.getPoolName());  
            }  
        }  
    }  
      
    // �������,�������ӳ����� �������  
    public Connection  getConnection(String poolName){  
        Connection conn = null;  
        if(pools.size()>0 && pools.containsKey(poolName)){  
            conn = getPool(poolName).getConnection();  
        }else{  
            System.out.println("Error:Can't find this connecion pool ->"+poolName);  
        }  
        return conn;  
    }  
      
    // �رգ���������  
    public void close(String poolName,Connection conn){  
            IConnectionPool pool = getPool(poolName);  
            try {  
                if(pool != null){  
                    pool.releaseConn(conn);  
                }  
            } catch (SQLException e) {  
                System.out.println("���ӳ��Ѿ�����");  
                e.printStackTrace();  
            }  
    } 
    public void close(String poolName){  
        IConnectionPool pool = getPool(poolName);  
        try {  
            if(pool != null){  
                pool.releaseCurConn();  
            }  
        } catch (SQLException e) {  
            System.out.println("���ӳ��Ѿ�����");  
            e.printStackTrace();  
        }  
}
      
    // ������ӳ�  
    public void destroy(String poolName){  
        IConnectionPool pool = getPool(poolName);  
        if(pool != null){  
            pool.destroy();  
        }  
    }  
      
    // ������ӳ�  
     IConnectionPool getPool(String poolName){  
        IConnectionPool pool = null;  
        if(pools.size() > 0){  
             pool = pools.get(poolName);  
        }  
        return pool;  
    }  
}
