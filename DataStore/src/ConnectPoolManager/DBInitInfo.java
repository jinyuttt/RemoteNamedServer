package ConnectPoolManager;

import java.util.ArrayList;
import java.util.List;

public class DBInitInfo {
	 public  static List<DBbean>  beans = null;  
	    static{  
	        beans = new ArrayList<DBbean>();  
	        // 这里数据 可以从xml 等配置文件进行获取  
	     //org.sqlite.JDBC
	      //jdbc:sqlite:filename
	        String Host = "127.0.0.1";
	        String Port = "5432";
	        String DBName = "PeostgreDB";
	        String UserName = "postgres";
	        String Password = "1234";
	        //postgresql
	        String DBType="jdbc:postgresql";
	        String DriverName="org.postgresql.Driver";
	        //
	        String PoolName="testpool";
	        DBbean beanOracle = new DBbean(); 
	        String url = DBType+"://"+Host+":"+Port+"/"+DBName;
	        //sqlite
	         url="jdbc:sqlite:/d:/mydatabase.sqlite";
	         url="jdbc:sqlite:memory";
	         DriverName="org.sqlite.JDBC";
	          UserName = "";
	          Password = "";
	          //
	          //H2
		          url="jdbc:h2:file:d:/mydatabase.db";//磁盘模式
		          url="jdbc:h2:tcp://IP:port/~/DBName";//服务模式模式
		          url="jdbc:h2:mem:"+DBName;//内存模式
		          DriverName="org.h2.Driver";
		          UserName = "";
		          Password = "";
		          //
	        beanOracle.setDriverName(DriverName);  
	        beanOracle.setUrl(url);  
	        beanOracle.setUserName(UserName);  
	        beanOracle.setPassword(Password);  
	          
	        beanOracle.setMinConnections(5);  
	        beanOracle.setMaxConnections(100);  
	          
	        beanOracle.setPoolName(PoolName);  
	        beanOracle.setMemory(false);
	        beans.add(beanOracle);  
	    } 
	    
	    public  static  void Add(DBbean dbconfig)
	    {
	    	beans.add(dbconfig);
	    }
}
