package JPerst;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import org.garret.perst.NullFile;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PerstHelper {
	Storage db = StorageFactory.getInstance().createStorage();
	MyRootClass root=null;
	public boolean Is_Open=false;//内存数据库用
	
	/**
	 * 文件存储，打开数据库
	 * @param DBName
	 * @param pagePoolSize
	 * @return
	 */
	public boolean Open(String DBName,int pagePoolSize)
	{
		try
		{
	         db.open(DBName, pagePoolSize);
	    	 root = (MyRootClass)db.getRoot();// get storage root
			
		 if (root==null)
		    {
		      root=new MyRootClass(db);
		      db.setRoot(root);
		    }

		return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	/**
	 * 内存数据库打开
	 * @return
	 */
	public boolean Open()
	{
		try
		{
		db.open(new NullFile(), Storage.INFINITE_PAGE_POOL);
		return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	/**
	 * 关闭数据库
	 */
	public void Close()
	{
		if(db!=null)
		{
			db.close();
		}
	}
	public boolean AddData(MyPersistentClass obj)
	{

		try
		
		{
		    if(root!=null)
		    {
		    
		    	root.intKeyIndex.put(obj);
		    	root.strKeyIndex.put(obj);
		    	root.idIndex.put(obj.id, obj);
		    	db.commit(); 
		    }
		return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public boolean Update(MyPersistentClass obj)
	{
            try{
		    if(root!=null)
		    {
		    
		    	root.intKeyIndex.set(obj);
		    	root.strKeyIndex.set(obj);
		    	root.idIndex.set(obj.id, obj);
		    	db.commit(); 
		    }
		return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public void Delete(Object obj)
	{
		root.intKeyIndex.remove(obj);// 
		root.strKeyIndex.remove(obj);// 
		root.idIndex.remove(obj);
		db.deallocate(obj); // 
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> Select(Class<T> c, String indexName,  String  selectCondition)
	{
		List<T> list=new ArrayList<T>();
		switch(indexName.toLowerCase())
		{
		case "id":
			list.addAll((Collection<? extends T>) root.idIndex.select(c, selectCondition));
			break;
		case "intkey":
			list.addAll((Collection<? extends T>) root.intKeyIndex.select(c, selectCondition));
			break;
		case "strkey":
			list.addAll((Collection<? extends T>) root.strKeyIndex.select(c, selectCondition));
			break;
			default:
				list.addAll((Collection<? extends T>) root.idIndex.select(c, selectCondition));
				break;
		}
	  
		
		 db.commit(); //执行操作，假如操作失败则恢复到操作之前的状态
	
		return list;
	}
}
	
	
	

