package h2DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class H2DBHelper {
	private String port = "8082";
   // private String dbDir = "jdbc:h2:tcp://192.168.0.36/~/test";
    private String dbConnec="";
    private String DBName="h2DB";
    
    private String dbPath="~";
    private String user = "sa";
    private String password = "";
    private String host = "localhost";
    DBModel theModel;
    Connection conn;
    Server server;
   public H2DBHelper(DBModel model)
   {
	   theModel=model;
   }
     public void Connect()
   {
	switch(theModel)
	{
	case Embedded:
		dbConnec="jdbc:h2:file:"+dbPath+"/"+DBName;
		break;
	case server:
		dbConnec="jdbc:h2:tcp://"+host+":"+port+"/"+dbPath+"/"+DBName;
		break;
	case memory:
		dbConnec="jdbc:h2:mem:"+DBName;
		break;
	}
	//内嵌 
	//dbConnec="jdbc:h2:file:"+dbPath+"/"+DBName;
	//内存
	//dbConnec="jdbc:h2:mem:"+DBName;
	//服务
	//dbConnec="jdbc:h2:tcp://"+host+":"+port+"/"+dbPath+"/"+DBName;
	 try {
         Class.forName("org.h2.Driver");
          conn = DriverManager.getConnection(dbConnec,
                 user, password);

     } catch (Exception e) {
       
         e.printStackTrace();
     }
   }
	 public boolean ExeSql(String sql)
	 {
		 try
		 {
		 if(conn==null||conn.isClosed())
		 {
			 Connect();
		 }
		 if(conn==null)
		 {
			 return false;
		 }
		 Statement stat = conn.createStatement();
     
        return stat.execute(sql);
		 }
		 catch(Exception ex)
		 {
			 return false;
		 }
	 }
	 public ResultSet QuerySql(String sql)
	 {
		 try
		 {
		 if(conn==null||conn.isClosed())
		 {
			 Connect();
		 }
		 if(conn==null)
		 {
			 return null;
		 }
		 Statement stat = conn.createStatement();
     
        return stat.executeQuery(sql);
		 }
		 catch(Exception ex)
		 {
			 return null;
		 }
	 }
	 public void Close()
	 {
		 try
		 {
		 if(conn==null||conn.isClosed())
		 {
			return;
		 }
		 conn.close();
		 }
		 catch(Exception ex)
		 {
			 
		 }
	 }
	 public void StartServer()
	 {
		 try {
	            System.out.println("正在启动h2...");
	             server = Server.createTcpServer(
	                    new String[] { "-tcpPort", port }).start();
	        } catch (SQLException e) {
	            System.out.println("启动h2出错：" + e.toString());
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }
	 }
    public void StopServer()
    {
    	if (server != null) {
            System.out.println("正在关闭h2...");
            server.stop();
            System.out.println("关闭成功.");
        }
    }
}
