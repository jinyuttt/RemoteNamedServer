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
	 // ���ӳ���������  
    private DBbean dbBean;  
    private boolean isActive = false; // ���ӳػ״̬  
    
    /**
     * ��¼�������ܵ�������  
     */
    private volatile  int contActive = 0;// ��¼�������ܵ�������  
      
    // ��������  
    private CopyOnWriteArrayList <Connection> freeConnection = new CopyOnWriteArrayList <Connection>();  
    // �����  
    private CopyOnWriteArrayList <Connection> activeConnection = new CopyOnWriteArrayList <Connection>();  
    // ���̺߳����Ӱ󶨣���֤������ͳһִ��  
   // private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); 
    private ConcurrentHashMap <String,ThreadGroup> threadGroup = new ConcurrentHashMap <String,ThreadGroup>();  
    /**
     * Ϊÿ���̴߳�������
     */
    public ConcurrentHashMap<Long,Connection>  threadConnected=new  ConcurrentHashMap<Long,Connection>();
    
   public Object lock_Obj=new  Object();
   
    boolean iSMemory=false;
    /**
     * ���캯��
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
    // ��ʼ��  
    public void init() {  
        try {  
        	
        	
            Class.forName(dbBean.getDriverName());  
            for (int i = 0; i < dbBean.getInitConnections(); i++) {  
                Connection conn;  
                conn =newConnection();  
                // ��ʼ����С������  
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
 // �ж������Ƿ����  
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
     * �����µ�����
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
	 	                	 // �ж��Ƿ񳬹��������������  
	 	                	synchronized(lock_Obj)
	 	                	{
	 	                	if(contActive < this.dbBean.getMaxActiveConnections())
	 	                	{
	 	                     conn = newConnection();  
	 	                     threadConnected.put(curID, conn);
	 	                	}
	 	                	else
	 	                	{
	 	                		 // �����������,ֱ�����»������
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
		   // Ĭ���߳�����ȡ  
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
	            // �������������ȴ����̣߳�ȥ������  
	            synchronized(lock_Obj)
	            {
	           
	            	lock_Obj.notifyAll();  
	           
	            }
	        }  
		
	}

	/* (non-Javadoc)
	 * ������������
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
	            // 1.���߳����������״̬  
	            // 2.���ӳ���С ���������  
	            // 3.����״̬���м�飬��Ϊ���ﻹ��Ҫд�����̹߳�����࣬��ʱ�Ͳ������  
	            System.out.println("���߳���������"+freeConnection.size());  
	            System.out.println("�����������"+activeConnection.size());  
	            System.out.println("�ܵ���������"+contActive); 
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
	                    	  //���������̣߳��̻߳�����Ѿ��������ӵı���������
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
	           
	            // �������������ȴ����̣߳�ȥ������  
	            synchronized(lock_Obj)
	            {
	            	lock_Obj.notifyAll();  
	            }
	        }  
		
	}

}
