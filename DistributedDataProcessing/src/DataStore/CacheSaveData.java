package DataStore;

import java.util.ArrayList;
import java.util.List;

import Com.DBHelper.DBHelper;
import ConnectPoolManager.DBbean;
import DataModel.CacheDataModel;
import DataQueue.QueueID;
import JPerst.PerstHelper;

public class CacheSaveData {
	public static final  PerstHelper hlp=new PerstHelper();
	public boolean Is_Open=false;
	private void Init()
	{
		String url="jdbc:sqlite:memory";
       String  DriverName="org.sqlite.JDBC";
       String  UserName = "";
       String   Password = "";
       //
       DBbean beanSqlite = new DBbean(); 
       beanSqlite.setDriverName(DriverName);  
       beanSqlite.setUrl(url);  
       beanSqlite.setUserName(UserName);  
       beanSqlite.setPassword(Password);  
         
       beanSqlite.setMinConnections(5);  
       beanSqlite.setMaxConnections(100);  
       beanSqlite.setMemory(true);
       beanSqlite.setPoolName("SqliteMem");  
       
       
	}
  public <T>  void Save(String key,T obj)
  {
	  if(key.isEmpty())
	  {
		  key=String.valueOf( System.currentTimeMillis())+ QueueID.queueid++;
	  }
	  CacheDataModel model=new CacheDataModel();
	  
	  model.strKey=key;
	  model.ObjData=obj;
	  if(hlp.Is_Open==false)
	  {
		  hlp.Is_Open=true;
		  hlp.Open();
	  }
	  hlp.AddData(model);
	 
  }
  @SuppressWarnings("unchecked")
public <T> List<T> GetCahe(String key)
  {
	    List<T> list=new ArrayList<T>();
    	List<CacheDataModel> lst= hlp.Select(CacheDataModel.class, "strKey", "strKey="+key);
    	for(CacheDataModel tmp:lst)
    	{
    		list.add((T)tmp.ObjData);
    	}
    	return list;
  }
  public   void Save(String  sql)
  {
	   //初始化数据
	  if(!Is_Open)
	  {
		  Init();
		  Is_Open=true;
	  }
	  DBHelper hlp=new DBHelper();
	  hlp.poolName="SqliteMem";
	  hlp.executeUpdate(sql);
	   
  }
  public <T> List<?> GetCaheSet(String sql)
  {
	   //初始化数据
	  if(!Is_Open)
	  {
		  Init();
		  Is_Open=true;
	  }
	  DBHelper hlp=new DBHelper();
	  hlp.poolName="SqliteMem";
	List<?>  rs=  hlp.executeQueryList(sql);
	return rs;
	   
  }
}
