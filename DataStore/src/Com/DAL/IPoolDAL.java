package Com.DAL;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface IPoolDAL {
	
	public  Statement  GetStatement();
	public  PreparedStatement   GetPreparedStatement(String Sql);
	public  ResultSet executeQuery(String sql);
	public  int executeUpdate(String sql);
	public  boolean executeSql(String sql);
	
	public  void closeAll(PreparedStatement prsts, ResultSet rs);
	public  int executeUpdate(String sql, Object[] param, int[] type);
	public  int executeInsertBatch(String sql, Object[] param, int[] type,int num);
	public  List<?> executeQuery(String sql, Object[] param, int[] type);
	public  List<?> executeQueryList(String sql);
}
