package DataStore;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Tools.MsgPackTool;

import BerkeleyDB.BerkeleyDB;
import Com.DBHelper.DBHelper;
import ConnectPoolManager.DBbean;
import DataModel.StorageModel;
import DataQueue.QueueID;

public class PersistentStorage {
	public boolean Is_Open=false;
	 BerkeleyDB berkeleydb=new BerkeleyDB();
	private void Init()
	{
		String url="jdbc:h2:file:~/h2DB";;
       String  DriverName="org.h2.Driver";
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
    
       beanSqlite.setPoolName("h2Embedded");  
       
       
	}
	  public <T>  void Save(String key, T obj)
	  {
		  if(key.isEmpty())
		  {
			  key=String.valueOf( System.currentTimeMillis())+ QueueID.queueid++;
		  }
		  //
		  if(!Is_Open)
		  {
			  berkeleydb.SetFileName("BerkeleyDBData");
			  berkeleydb.SetDBName("DBCache");
			  berkeleydb.openDatabase();
			  Is_Open=true;
		  }
		  StringBuilder error=null;
		  
		  StorageModel<T> model=new StorageModel<T>();
		  model.data=obj;
		  MsgPackTool tool=new MsgPackTool();
		  byte[]bytes=  tool.Serialize(obj, error);
		  berkeleydb.writeToDatabase(key.getBytes(), bytes, true);
		 
	  }
	  public <T>  void Save(Map<String,T> map)
     {
//		  if(key.isEmpty())
//		  {
//			  key=String.valueOf( System.currentTimeMillis())+ QueueID.queueid++;
//		  }
		  //
		  if(!Is_Open)
		  {
			  berkeleydb.SetFileName("BerkeleyDBData");
			  berkeleydb.SetDBName("DBCache");
			  berkeleydb.openDatabase();
			  Is_Open=true;
		  }
		  StringBuilder error=null;
		  Map<byte[],byte[]> dbData=new HashMap<byte[],byte[]>();
		  
		  for(Map.Entry<String, T> kv : map.entrySet())
		  {
//			  StorageModel<T> model=new StorageModel<T>();
//			  model.data=kv.getValue();
			  T objtemp=kv.getValue();
			  MsgPackTool tool=new MsgPackTool();
			//  byte[]bytes=tool.Serialize(model, error);
			  byte[]bytes=tool.Serialize(objtemp, error);
			  dbData.put(kv.getKey().getBytes(Charset.forName("UTF-8")), bytes);
		  }
		  berkeleydb.writeToDBMap(dbData, true);
		 
	  }
	  public <T>  void Select(String key, T obj)
	  {
		  
		  //
		  StringBuilder error=null;
		
		  StorageModel<T> model=new StorageModel<T>();
		  model.data=obj;
		  MsgPackTool tool=new MsgPackTool();
		  byte[]bytes=  tool.Serialize(obj, error);
		  berkeleydb.writeToDatabase(key.getBytes(), bytes, true);
		 
	  }
	  public   Map<byte[],byte[]> SelectList(int num)
	  {
		
		  //
		 
		 
	   	return  berkeleydb.getItem(num);
		 
	  }
	  /**
	   * 删除数据
	 * @param keys
	 * @return
	 */
	public   int  DeleteList(List<byte[]>keys)
	  {
		
		  //
		 
		  return berkeleydb.deleteFromDatabase(keys, 10);
		 
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
		  hlp.poolName="h2Embedded";
		  hlp.executeUpdate(sql);
	  }
}
