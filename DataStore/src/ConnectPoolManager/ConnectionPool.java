package ConnectPoolManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class ConnectionPool  implements IConnectionPool {
	 // 连接池配置属性  
    private DBbean dbBean;  
    private boolean isActive = false; // 连接池活动状态  
    
    /**
     * 记录创建的总的连接数  
     */
    private volatile  int contActive = 0;// 记录创建的总的连接数  
      
    // 空闲连接  
    private CopyOnWriteArrayList <Connection> freeConnection = new CopyOnWriteArrayList <Connection>();  
    // 活动连接  
    private CopyOnWriteArrayList <Connection> activeConnection = new CopyOnWriteArrayList <Connection>();  
    // 将线程和连接绑定，保证事务能统一执行  
   // private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); 
    private ConcurrentHashMap <String,ThreadGroup> threadGroup = new ConcurrentHashMap <String,ThreadGroup>();  
    /**
     * 为每个线程创建连接
     */
    public ConcurrentHashMap<Long,Connection>  threadConnected=new  ConcurrentHashMap<Long,Connection>();
    
   public Object lock_Obj=new  Object();
   
    boolean iSMemory=false;
    /**
     * 构造函数
     * @param config
     */
    public ConnectionPool(DBbean config)
    {
    	 super();  
         this.dbBean = config;  
         this.iSMemory=config.getisMemory();
         init();  
         cheackPool(); 
    }
    // 初始化  
    public void init() {  
        try {  
        	
        	
            Class.forName(dbBean.getDriverName());  
            for (int i = 0; i < dbBean.getInitConnections(); i++) {  
                Connection conn;  
                conn =newConnection();  
                // 初始化最小连接数  
                if (conn != null) {  
                    freeConnection.add(conn);  
                    contActive++;  
                }  
                if(dbBean.getisMemory())
            	{
            	   break;	
            	}
            }  
            isActive = true;  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
 // 判断连接是否可用  
    private boolean isValid(Connection conn) {  
        try {  
            if (conn == null || conn.isClosed()) {  
                return false;  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return true;  
    }  
  
  
    
    /**
     * 创建新的连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private synchronized Connection newConnection()  
            throws ClassNotFoundException, SQLException {  
        Connection conn = null;  
        if (dbBean != null) {  

            conn = DriverManager.getConnection(dbBean.getUrl(),  
                    dbBean.getUserName(), dbBean.getPassword());  
        }  
        return conn;  
    }  
    
    
	@Override
	public   Connection getConnection() {
		 Connection conn = null;  
	        try {  
	        	
	        	
	              Long curID= Thread.currentThread().getId();
	              ThreadGroup curGroup=Thread.currentThread().getThreadGroup();
	              conn=threadConnected.get(curID);
	            	if(conn==null)
	            	{
	            		 if (freeConnection.size() > 0) {  
	 	                     conn = freeConnection.get(0);  
	 	                     if(iSMemory)
	 	                    	 
	 	                     {
	 	                    	  return conn;  
	 	                     }
	 	                    if (conn != null) {  
	 	                        
	 	                    	threadGroup.put(curGroup.getName(), curGroup);
	 	                    
	 	                      threadConnected.put(curID, conn);
	 	                    }  
	 	                    freeConnection.remove(0);  
	 	                } else {  
	 	                	 // 判断是否超过最大连接数限制  
	 	                	synchronized(lock_Obj)
	 	                	{
	 	                	if(contActive < this.dbBean.getMaxActiveConnections())
	 	                	{
	 	                     conn = newConnection();  
	 	                     threadConnected.put(curID, conn);
	 	                	}
	 	                	else
	 	                	{
	 	                		 // 继续获得连接,直到从新获得连接
	 	                		 lock_Obj.wait(this.dbBean.getConnTimeOut());  
	 	    	                 conn = getConnection();  
	 	                	}
	 	                	}
	 	                }  
	            	}
	             if (isValid(conn)) {  
	                activeConnection.add(conn);  
	                contActive ++;  
	            }  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	        return conn;  
		
	}

	@Override
	public Connection getCurrentConnecton() {
		   // 默认线程里面取  
		 Long curID= Thread.currentThread().getId();
		 Connection	 conn=threadConnected.get(curID);
        if(!isValid(conn)){  
            conn = getConnection();  
        }  
        return conn;  
		
	}

	@Override
	public void releaseConn(Connection conn) throws SQLException {
		 if (isValid(conn)&& !(freeConnection.size() > dbBean.getMaxConnections())) {  
	            freeConnection.add(conn);  
	            activeConnection.remove(conn);  
	            contActive --;  
	           // threadLocal.remove();  
	            // 唤醒所有正待等待的线程，去抢连接  
	            synchronized(lock_Obj)
	            {
	           
	            	lock_Obj.notifyAll();  
	           
	            }
	        }  
		
	}

	/* (non-Javadoc)
	 * 销毁所有连接
	 * @see ConnectPoolManager.IConnectionPool#destroy()
	 */
	
	@Override
	public synchronized void destroy() {
		for (Connection conn : freeConnection) {  
            try {  
                if (isValid(conn)) {  
                    conn.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        for (Connection conn : activeConnection) {  
            try {  
                if (isValid(conn)) {  
                    conn.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        isActive = false;  
        contActive = 0;  
		
	}

	@Override
	public boolean isActive() {
		 return isActive;  
	}

	@Override
	public void cheackPool() {
		 if(dbBean.isCheakPool()){  
	            new Timer().schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	            // 1.对线程里面的连接状态  
	            // 2.连接池最小 最大连接数  
	            // 3.其他状态进行检查，因为这里还需要写几个线程管理的类，暂时就不添加了  
	            System.out.println("空线池连接数："+freeConnection.size());  
	            System.out.println("活动连接数：："+activeConnection.size());  
	            System.out.println("总的连接数："+contActive); 
	             //
	           
	            Enumeration<String> groupname=threadGroup.keys();
	            ArrayList<Long> allThreadID=new ArrayList<Long>();
	           
	             while(groupname.hasMoreElements())
	             {
	            	  String name=groupname.nextElement();
	            	  ThreadGroup temp= threadGroup.get(name);
	            	  Thread tga[] = new Thread[temp.activeCount()];
	            	  temp.enumerate(tga);
	            	  for (int i = 0; i < tga.length; i++) {  
	                      Thread child=tga[i];
	                      Long id=child.getId();
	                      if(threadConnected.containsKey(id))
	                      {
	                    	  //遍历所有线程，线程活动并且已经分配连接的保存下来；
	                    	  if(child.isAlive())
	                    	  {
	                    	     allThreadID.add(id);
	                    	  }
	                      }
	                  } 
	             }
	          Enumeration<Long> eCol = threadConnected.keys();
	           while(eCol.hasMoreElements())
	           {
	        	   Long id=eCol.nextElement();
	        	   if(!allThreadID.contains(id))
	        	   {
	        		   Connection conn=threadConnected.get(id);
	        		  if(conn!=null)
	        		  {
	        			  freeConnection.add(conn);  
	      	              activeConnection.remove(conn);  
	      	              contActive --;  
	        		  }
	        		   threadConnected.remove(id);
	        		   
	        	   }
	           }
	            
	                }  
	            },dbBean.getLazyCheck(),dbBean.getPeriodCheck());  
	        }  
		
	}
	@Override
	public void releaseCurConn()  {
		 Long curID= Thread.currentThread().getId();
		 Connection	 conn=threadConnected.get(curID);
       if (isValid(conn)) {  
	            freeConnection.add(conn);  
	            activeConnection.remove(conn);  
	            contActive --;  
	           
	            // 唤醒所有正待等待的线程，去抢连接  
	            synchronized(lock_Obj)
	            {
	            	lock_Obj.notifyAll();  
	            }
	        }  
		
	}

}
