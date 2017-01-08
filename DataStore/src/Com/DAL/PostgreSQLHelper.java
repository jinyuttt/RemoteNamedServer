package Com.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostgreSQLHelper extends BasisDAL{

	Connection allcon=null;


	@Override
	public Connection GetConnect(String strcon,String user, String password) {
	
		try
		{
		 Class.forName("org.postgresql.Driver").newInstance();
	     
	     Connection con = DriverManager.getConnection(strcon, user, password);
	     return con;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public Connection GetConnect(String host,String port, String database, String user, String password) {
		try
		{
		  Class.forName( " org.postgresql.Driver " ).newInstance();
		 
	       String url = " jdbc:postgresql://"+host+":"+port+"/"+database;
		   Connection con = DriverManager.getConnection(url, user ,password);
		   return con;
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}


	@Override
	public Statement GetStatement(Connection con) {
		 try {
			Statement st = con.createStatement();
			return st;
		} catch (SQLException e) {
			
			return null;
		}
		
	}

	@Override
	public ResultSet executeQuery(String sql) {
		
		try
		{
			Statement st=  this.GetStatement(Host, Port, DBName,UserName,Password);
		    ResultSet rs= st.executeQuery(sql);
		 return rs;
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}

	@Override
	public int executeUpdate(String sql) {
		try
		{
			Statement st=  this.GetStatement(Host, Port, DBName,UserName,Password);
		    int IsSucess= st.executeUpdate(sql);
		    return  IsSucess;
		}
		catch(Exception ex)
		{
			return 0;
		}
		
		
	}

	@Override
	public Statement GetStatement(String strcon, String user, String password) {
      
		try
		 {
		Class.forName("org.postgresql.Driver").newInstance();
	     
	     Connection con = DriverManager.getConnection(strcon, user, password);
	     Statement  st= con.createStatement();
	     return st;
		 }
		catch(Exception ex)
		{
		return null;
		}
	}

	@Override
	public Statement GetStatement(String host,String port, String database, String user, String password) {
		try
		{
		  if(allcon==null)
		  {
			  allcon=this.GetConnect(host, port, database, user, password);
		  }
		   Statement st=allcon.createStatement();
		   return st;
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}

	@Override
	public void closeAll(Connection conn, PreparedStatement prsts, ResultSet rs) {
		 if (rs != null) {
             try {
                      rs.close();
             } catch (SQLException e) {
                      e.printStackTrace();
             }
    }
    if (prsts != null) {
             try {
                      prsts.close();
             } catch (SQLException e) {
                      e.printStackTrace();
             }
    }
    if (conn != null) {
             try {
                      conn.close();
             } catch (SQLException e) {
                      e.printStackTrace();
             }
    }

		
	}

	@Override
	public int executeUpdate(String sql, Object[] param, int[] type) {
		int rows = 0;
        Connection conn = this.GetConnect(Host, Port, DBName, UserName, Password);
        PreparedStatement prsts = null;
        try {
                 prsts = conn.prepareStatement(sql);
                 for (int i = 1; i <= param.length; i++) {
                          prsts.setObject(i, param[i - 1], type[i - 1]);
                 }
                 rows = prsts.executeUpdate();
        } catch (SQLException e) {
                 e.printStackTrace();
        } finally {
                 this.closeAll(conn, prsts, null);
        }
        return rows;

		
	}

	@Override
	public List<Map<String,Object>> executeQuery(String sql, Object[] param, int[] type) {
		ResultSet rs = null;
        List<Map<String,Object>> list = null;
       
        PreparedStatement prsts = null;
        try {
                 prsts =this.GetPreparedStatement(sql);
                 for (int i = 1; i <= param.length; i++) {
                          prsts.setObject(i, param[i - 1], type[i - 1]);
                 }
                 rs = prsts.executeQuery();
              
                 list = new ArrayList<Map<String,Object>>();
                 ResultSetMetaData rsm = rs.getMetaData();
                 Map<String, Object> map = null;
                 while (rs.next()) {
                          map = new HashMap<String, Object>();
                          for (int i = 1; i <= rsm.getColumnCount(); i++) {
                                    map.put(rsm.getColumnName(i), rs.getObject(rsm.getColumnName(i)));
                          }
                          list.add(map);
                 }
        } catch (SQLException e) {
                 e.printStackTrace();
        }finally{
                 this.closeAll(null, prsts, rs);
        }
        return list;

		
	}

	@Override
	public int executeUpdate(Connection con, String sql) {
		try
		{
		   
		   Statement st=this.GetStatement(con);
		   int IsSucess=st.executeUpdate(sql);
		   return IsSucess;
		}
		catch(Exception ex)
		{
			return 0;
		}
	
	}

	@Override
	public ResultSet executeQuery(Connection con, String sql) {
		try
		{
		   
		   Statement st=con.createStatement();
		   ResultSet  rs= st.executeQuery(sql)	;	
		   return  rs;
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}

	@Override
	public PreparedStatement GetPreparedStatement(Connection con,String Sql) {
		 PreparedStatement prsts = null;
	        try {
	                 prsts = con.prepareStatement(Sql);
	                 return prsts;
	        }
	        catch(Exception ex)
	        {
	        	return null;
	        }
		
	}

	@Override
	public PreparedStatement GetPreparedStatement(String host, String port, String database, String user,
			String password,String Sql) {
		
		 PreparedStatement prsts = null;
	        try {
	        	Connection con=this.GetConnect(host, port, database, user, password);
	                 prsts = con.prepareStatement(Sql);
	                 return prsts;
	        }
	        catch(Exception ex)
	        {
	        	return null;
	        }
	}

	@Override
	public PreparedStatement GetPreparedStatement(String Sql) {
		PreparedStatement prsts = null;
		try
	 {
		 if(allcon==null)
		 {
			 allcon=this.GetConnect(Host, Port, DBName, UserName, Password);
			
		 }
		 prsts=allcon.prepareStatement(Sql); 
		 return prsts;
	 }
		catch(Exception ex)
		{
			return null;
		}
		
	}

}
