package Com.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Com.DAL.IPoolDAL;
import ConnectPoolManager.ConnectionPoolManager;


public class DBHelper implements IPoolDAL{
	public String poolName="";

	@Override
	public Statement GetStatement() {
		try
		{
		Connection conn=ConnectionPoolManager.getInstance().getConnection(poolName);
		Statement st=conn.createStatement();
		return st;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public PreparedStatement GetPreparedStatement(String Sql) {
	  
		try
		{
		Connection conn=	ConnectionPoolManager.getInstance().getConnection(poolName);
		PreparedStatement prest=conn.prepareStatement(Sql);
		return prest;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public ResultSet executeQuery(String sql) {
		
		try
		{
			Statement st=this.GetStatement();
			ResultSet rs=	st.executeQuery(sql);
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
			Statement st=this.GetStatement();
			int rs=	st.executeUpdate(sql);
			return rs; 
		}
		catch(Exception ex)
		{
			return 0;
		}
	}

	@Override
	public void closeAll(PreparedStatement prsts, ResultSet rs) {
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
    ConnectionPoolManager.getInstance().close(poolName);
     
		
	}

	@Override
	public int executeUpdate(String sql, Object[] param, int[] type)  {
		int rows = 0;
	
        PreparedStatement prsts = null;
		try {
        	   
                 prsts = this.GetPreparedStatement(sql);
                 for (int i = 1; i <= param.length; i++) {
                          prsts.setObject(i, param[i - 1], type[i - 1]);
                 }
                 rows = prsts.executeUpdate();
        } catch (SQLException e) {
                
        } finally {
        	try {
				prsts.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}  
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
                 this.closeAll(prsts, rs);
        }
        return list;

	}

	@Override
	public List<?> executeQueryList(String sql) {
		ResultSet rs = null;
        List<Map<String,Object>> list = null;
       
      
        try {
        	 rs=this.executeQuery(sql);
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
              this.closeAll(null, rs);
        }
        return list;
	}

	@Override
	public int executeInsertBatch(String sql, Object[] param, int[] type,int num) {
		  
  
           //从池中获取连接 
         if(num==0)
         {
        	 num=500;
         }
          int index=0;
          try
          {
	Connection conn=ConnectionPoolManager.getInstance().getConnection(poolName);
		
       
           PreparedStatement pstmt = this.GetPreparedStatement(sql);
           conn.setAutoCommit(false);
        	   for (int i = 1; i <= param.length; i++) {
        		   pstmt.setObject(i, param[i - 1], type[i - 1]);
        		   index++;
        		   if(index==num)
        		   {
        			   //加入批处理 
                       pstmt.addBatch(); 
              
                       pstmt.executeBatch();    //执行批处理 
                       
                       
                       index=0;
        		   }
        	   }
              if(index>0)
              {
            	  //加入批处理 
                  pstmt.addBatch(); 
         
                  pstmt.executeBatch();    //执行批处理 
              }
              conn.setAutoCommit(true);
              pstmt.close();
              return 1;
          }
          catch(Exception ex)
          {
        		return 0; 
          }
          
	   
        
   }

	@Override
	public boolean executeSql(String sql) {
		try
		{
			Statement st=this.GetStatement();
		boolean r=	st.execute(sql);
			return r; 
		}
		catch(Exception ex)
		{
			return false;
		}
		
	}

	

	
}
