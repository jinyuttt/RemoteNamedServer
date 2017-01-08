package Com.DAL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.List;
public abstract class BasisDAL {
	
	public String Host;
	public String Port;
	public String UserName;
	public String Password;
	public String DBName;
public abstract Connection GetConnect(String strcon,String user, String password);
public abstract Connection GetConnect(String host,String port, String database, String user, String password);
public abstract Statement  GetStatement(String strcon,String user, String password);
public abstract Statement  GetStatement(Connection con);
public abstract PreparedStatement   GetPreparedStatement(Connection con,String Sql);
public abstract Statement GetStatement(String host,String port, String database, String user, String password);
public abstract PreparedStatement   GetPreparedStatement(String Sql);
public abstract PreparedStatement GetPreparedStatement(String host,String port, String database, String user, String password,String sql);
public abstract ResultSet executeQuery(String sql);
public abstract int executeUpdate(String sql);
public abstract int executeUpdate(Connection con,String sql);
public abstract ResultSet executeQuery(Connection con,String sql);
public abstract void closeAll(Connection conn, PreparedStatement prsts, ResultSet rs);
public abstract int executeUpdate(String sql, Object[] param, int[] type);
public abstract List<?> executeQuery(String sql, Object[] param, int[] type);
}