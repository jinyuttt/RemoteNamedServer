package BerkeleyDB;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sleepycat.je.CacheMode;
import com.sleepycat.je.CheckpointConfig;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockConflictException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;

public class BerkeleyDB {
	//���ݿ⻷��
    private  Environment myDbEnvironment = null;
    //���ݿ�����
    private  DatabaseConfig dbConfig=null;
   
//    //���ݿ��α�
//    private  Cursor myCursor = null;
    //���ݿ����
    private  Database myDatabase = null;
    //���ݿ��ļ���
    private  String fileName = "";
    //���ݿ�����
    private  String dbName = "";
    public void SetFileName(String DBfile)
    {
    	fileName=DBfile;
    }
    public void SetDBName(String DBName)
    {
    	dbName=DBName;
    }
    /*
     * �򿪵�ǰ���ݿ�
     */
    public  void openDatabase() {
        try{
        	 
           // CheckMethods.PrintDebugMessage("�����ݿ�: "+dbName);
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setTransactional(true);
            envConfig.setReadOnly(false);
            envConfig.setTxnTimeout(1, TimeUnit.SECONDS);
            envConfig.setLockTimeout(1, TimeUnit.SECONDS);
            envConfig.setCacheMode(CacheMode.DEFAULT);
            envConfig.setTxnNoSyncVoid(true);
            
            /*
             *   �������� ���Խ��и���
                EnvironmentMutableConfig envMutableConfig = new EnvironmentMutableConfig();
                envMutableConfig.setCachePercent(50);//����je��cacheռ��jvm �ڴ�İٷֱȡ�
                envMutableConfig.setCacheSize(123456);//�趨����Ĵ�СΪ123456Bytes
                envMutableConfig.setTxnNoSync(true);//�趨�����ύʱ�Ƿ�д���ĵ����ݵ����̣�true��д���̡�
                //envMutableConfig.setTxnWriteNoSync(false);//�趨�������ύʱ���Ƿ�д�����log�����̡����д���̻�Ӱ�����ܣ���д��Ӱ������İ�ȫ�����Ӧ�䡣
             *
             */
            File file = new File(fileName);
            if(!file.exists())
                file.mkdirs();
            myDbEnvironment = new Environment(file,envConfig);
            //
            CheckpointConfig config=new CheckpointConfig();
            config.setMinutes(1);
             myDbEnvironment.checkpoint(config);
             
            dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setTransactional(true);
            dbConfig.setReadOnly(false);
            dbConfig.setDeferredWriteVoid(false);
          //  dbConfig.setDeferredWrite(true);
          
           // dbConfig.setTemporary(true);
            //dbConfig.setSortedDuplicates(false);
            /*
                setBtreeComparator ��������B tree�ȽϵıȽ�����ͨ������������
                setDuplicateComparator ���������Ƚ�һ��key��������ֵͬ��ʱ��Ĵ�С�Ƚ�����
                setSortedDuplicates ����һ��key�Ƿ�����洢���ֵ��true��������Ĭ��false.
                setExclusiveCreate �Զ�ռ�ķ�ʽ�򿪣�Ҳ����˵ͬһ��ʱ��ֻ����һʵ�������database��
                setReadOnly ��ֻ����ʽ��database,Ĭ����false.
                setTransactional �������Ϊtrue,��֧��������Ĭ����false����֧������
            */
            if(myDatabase == null)
                myDatabase = myDbEnvironment.openDatabase(null, dbName, dbConfig);
        
            
           // CheckMethods.PrintDebugMessage(dbName+"���ݿ��е����ݸ���: "+myDatabase.count());
            /*
             *  Database.getDatabaseName()
                ȡ�����ݿ������
                �磺String dbName = myDatabase.getDatabaseName();
                
                Database.getEnvironment()
                ȡ�ð������database�Ļ�����Ϣ
                �磺Environment theEnv = myDatabase.getEnvironment();
                
                Database.preload()
                Ԥ�ȼ���ָ��bytes�����ݵ�RAM�С�
                �磺myDatabase.preload(1048576l); // 1024*1024
                
                Environment.getDatabaseNames()
                ���ص�ǰ�����µ����ݿ��б�
                Environment.removeDatabase()
                ɾ����ǰ������ָ�������ݿ⡣
                �磺
                String dbName = myDatabase.getDatabaseName();
                myDatabase.close();
                myDbEnv.removeDatabase(null, dbName);
                
                Environment.renameDatabase()
                ����ǰ�����µ����ݿ����
                �磺
                String oldName = myDatabase.getDatabaseName();  
                String newName = new String(oldName + ".new", "UTF-8");
                myDatabase.close();
                myDbEnv.renameDatabase(null, oldName, newName);
                
                Environment.truncateDatabase()
                ���database�ڵ��������ݣ���������˶�������¼��
                �磺
                Int numDiscarded= myEnv.truncate(null,
                myDatabase.getDatabaseName(),true);
                CheckMethods.PrintDebugMessage("һ��ɾ���� " + numDiscarded +" ����¼ �����ݿ� " + myDatabase.getDatabaseName());
             */
        }
        catch(DatabaseException e){
          //  CheckMethods.PrintInfoMessage(e.getMessage());

        }
    }
    /*
     * �����ݿ���д���¼
     * ����key��value
     */
    public  boolean writeToDatabase(byte[] key,byte[] value,boolean isOverwrite) {
      
        try {
       
              //����key/value,ע��DatabaseEntry��ʹ�õ���bytes����
            DatabaseEntry theKey=new DatabaseEntry(key);
              DatabaseEntry theData=new DatabaseEntry(value);
              OperationStatus res = null;
              Transaction txn = null;
              try
              {
                  TransactionConfig txConfig = new TransactionConfig();
                  txConfig.setSerializableIsolation(true);
                  txn = myDbEnvironment.beginTransaction(null, txConfig);
                  if(isOverwrite)
                  {
                      res = myDatabase.put(txn, theKey, theData);
                  }
                  else
                  {
                      res = myDatabase.putNoOverwrite(txn, theKey, theData);
                  }
                  txn.commit();
                  if(res == OperationStatus.SUCCESS)
                  {
                     // CheckMethods.PrintDebugMessage("�����ݿ�" + dbName +"��д��:"+key+","+value);
                      return true;
                  } 
                  else if(res == OperationStatus.KEYEXIST)
                  {
                    //  CheckMethods.PrintDebugMessage("�����ݿ�" + dbName +"��д��:"+key+","+value+"ʧ��,��ֵ�Ѿ�����");
                      return false;
                  }
                  else 
                  {
                    //  CheckMethods.PrintDebugMessage("�����ݿ�" + dbName +"��д��:"+key+","+value+"ʧ��");
                      return false;
                  }
              }
              catch(LockConflictException lockConflict)
              {
                  txn.abort();
                  return false;
              }
        }
        catch (Exception e) 
        {
            // ������
          
            
            return false;
        }
    }
    
    /**
     * д��һ��
     * @param map
     * @param isOverwrite
     * @return
     */
    public  boolean writeToDBMap(Map<byte[],byte[]> map,boolean isOverwrite) {
        
        try {
              //����key/value,ע��DatabaseEntry��ʹ�õ���bytes����
           
              OperationStatus res = null;
              Transaction txn = null;
              try
              {
                  TransactionConfig txConfig = new TransactionConfig();
                  txConfig.setSerializableIsolation(true);
                  txn = myDbEnvironment.beginTransaction(null, txConfig);
                  for(Map.Entry<byte[], byte[]> kv : map.entrySet())
                  {
                	  DatabaseEntry theKey=new DatabaseEntry(kv.getKey());
                      DatabaseEntry theData=new DatabaseEntry(kv.getValue());
                  if(isOverwrite)
                  {
                      res = myDatabase.put(txn, theKey, theData);
                  }
                  else
                  {
                      res = myDatabase.putNoOverwrite(txn, theKey, theData);
                  }
                  }
                  txn.commit();
                  if(res == OperationStatus.SUCCESS)
                  {
                     // CheckMethods.PrintDebugMessage("�����ݿ�" + dbName +"��д��:"+key+","+value);
                      return true;
                  } 
                  else if(res == OperationStatus.KEYEXIST)
                  {
                    //  CheckMethods.PrintDebugMessage("�����ݿ�" + dbName +"��д��:"+key+","+value+"ʧ��,��ֵ�Ѿ�����");
                      return false;
                  }
                  else 
                  {
                    //  CheckMethods.PrintDebugMessage("�����ݿ�" + dbName +"��д��:"+key+","+value+"ʧ��");
                      return false;
                  }
              }
              catch(LockConflictException lockConflict)
              {
                  txn.abort();
                  return false;
              }
        }
        catch (Exception e) 
        {
            // ������
          
            
            return false;
        }
    } 
    
    
    /*
     * �رյ�ǰ���ݿ�
     */
    public  void closeDatabase() {
       
        if(myDatabase != null)
        {
            myDatabase.close();
        }
        if(myDbEnvironment != null)
        {
          
            myDbEnvironment.cleanLog(); 
            myDbEnvironment.close();
        }
    }
   
    /*
     * ɾ�����ݿ��е�һ����¼
     */
    public  boolean deleteFromDatabase(byte[] key) {
        boolean success = false;
           long sleepMillis = 0;
            if (sleepMillis != 0) 
            {
                try 
                {
                    Thread.sleep(sleepMillis);
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
                sleepMillis = 0;
            }
            Transaction txn = null;
            try
            {
                TransactionConfig txConfig = new TransactionConfig();
                txConfig.setSerializableIsolation(true);
                txn = myDbEnvironment.beginTransaction(null, txConfig);
                DatabaseEntry theKey;
                theKey = new DatabaseEntry(key);
                OperationStatus res = myDatabase.delete(txn, theKey);
                txn.commit();
                if(res == OperationStatus.SUCCESS)
                {
                       success = true; 
                       return success;
                }
                else if(res == OperationStatus.KEYEMPTY)
                {
                	 return false;
                }
                else
                {
                   return true;
                }
                
            }
            catch (Exception e) 
            {
                
                
                e.printStackTrace();
                success=false;
            }
            finally 
            {
                 if (!success)
                 {
                      if (txn != null) 
                      {
                          txn.abort();
                      }
                 }
            }
			return success;
    }
   
    /*
     * ɾ�����ݿ��еĶ�����¼
     */
    public  int deleteFromDatabase(List<byte[]> keys,int sleepMillis) {
        
    	int sum=0;
          
            if (sleepMillis != 0) 
            {
                try 
                {
                    Thread.sleep(sleepMillis);
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
                sleepMillis = 0;
            }
            Transaction txn = null;
            try
            {
                TransactionConfig txConfig = new TransactionConfig();
                txConfig.setSerializableIsolation(true);
                txn = myDbEnvironment.beginTransaction(null, txConfig);
                for(byte[] key:keys)
                {
                DatabaseEntry theKey;
                theKey = new DatabaseEntry(key);
                OperationStatus res = myDatabase.delete(txn, theKey);
              
                if(res == OperationStatus.SUCCESS)
                {
                      sum++;
                }
                }
              
                if (txn != null) 
                {
                    txn.commit();
                 
                  int num=  myDbEnvironment.cleanLog();
                  System.out.println("ɾ����־:"+num);
                  boolean sucess= myDbEnvironment.cleanLogFile();
                  if(sucess)
                  {
                	  System.out.println("ɾ����־�ļ��ɹ�");
                  }
                }
            }
            catch (Exception e) 
            {
                
            	if (txn != null) 
                {
                    txn.abort();
                }
                e.printStackTrace();
               
            }
           
			return sum;
    }
   
    
    /*
     * �����ݿ��ж�������
     * ����key ����value
     */
    public String readFromDatabase(byte[] key) {
       
        try {
             DatabaseEntry theKey = new DatabaseEntry(key);
             DatabaseEntry theData = new DatabaseEntry();
             Transaction txn = null;
             try
             {
                 TransactionConfig txConfig = new TransactionConfig();
                 txConfig.setSerializableIsolation(true);
                 txn = myDbEnvironment.beginTransaction(null, txConfig);
                 OperationStatus res = myDatabase.get(txn, theKey, theData, LockMode.DEFAULT);
                
                 txn.commit();
                 if(res == OperationStatus.SUCCESS)
                 {
                     byte[] retData = theData.getData();
                     String foundData = new String(retData, "UTF-8");
              
                     return foundData;
                 }
                 else
                 {
                
                     return "";
                 }
             }
             catch(LockConflictException lockConflict)
             {
                 txn.abort();
                 return "";
             }
            
        } catch (UnsupportedEncodingException e) {
         
            e.printStackTrace();
            
            return "";
        }
    }
  
    
    /*
    
 * �������ݿ��е����м�¼������list
     */
    public  ArrayList<String> getEveryKeyItem() {
     
         Cursor myCursor = null;
         ArrayList<String> resultList = new ArrayList<String>();
         Transaction txn = null;
         try{
             txn = this.myDbEnvironment.beginTransaction(null, null);
             CursorConfig cc = new CursorConfig();
             cc.setReadCommitted(true);
             
             if(myCursor==null)
                 myCursor = myDatabase.openCursor(txn, cc);
             DatabaseEntry foundKey = new DatabaseEntry();
             DatabaseEntry foundData = new DatabaseEntry();         
           
             if(myCursor.getFirst(foundKey, foundData, LockMode.DEFAULT)
                     == OperationStatus.SUCCESS)
             {
                 String theKey = new String(foundKey.getData(), "UTF-8");
                 resultList.add(theKey);
             
                 while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) 
                           == OperationStatus.SUCCESS) 
                 {
                        theKey = new String(foundKey.getData(), "UTF-8");
                        resultList.add(theKey);
                      
                 }
             }
             myCursor.close();
             txn.commit();
             return resultList;
         } 
         catch (UnsupportedEncodingException e) {
           
            e.printStackTrace();    
            return null;
         }
         catch (Exception e) 
         {

             
             txn.abort();
             if (myCursor != null) 
             {
                 myCursor.close();
             }
             return null;
         }
    }

    
    public  ArrayList<byte[]> getKeyItem(int num) {
        
        Cursor myCursor = null;
        ArrayList<byte[]> resultList = new ArrayList<byte[]>();
        Transaction txn = null;
        int count=0;
        try{
            txn = this.myDbEnvironment.beginTransaction(null, null);
            CursorConfig cc = new CursorConfig();
            cc.setReadCommitted(true);
            
            if(myCursor==null)
                myCursor = myDatabase.openCursor(txn, cc);
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();         
          
            if(myCursor.getFirst(foundKey, foundData, LockMode.DEFAULT)
                    == OperationStatus.SUCCESS)
            {
               
            	count++;
                resultList.add(foundKey.getData());
            
                while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) 
                          == OperationStatus.SUCCESS) 
                {
                        count++;
                       resultList.add(foundKey.getData());
                     if(count>=num&&num!=0)
                     {
                    	 break;
                     }
                }
            }
            myCursor.close();
            txn.commit();
            return resultList;
        } 
        catch (Exception e) 
        {

            
            txn.abort();
            if (myCursor != null) 
            {
                myCursor.close();
            }
            return null;
        }
   }

    /**
     * ��ȡ����
     * @param num ������
     * @return ����ֵ
     */
    public  Map<byte[],byte[]> getItem(int num) {
        
        Cursor myCursor = null;
       Map<byte[],byte[]> resultList = new HashMap<byte[],byte[]>();
        Transaction txn = null;
        int count=0;
        try{
            txn = this.myDbEnvironment.beginTransaction(null, null);
            CursorConfig cc = new CursorConfig();
            cc.setReadCommitted(true);
            
            if(myCursor==null)
                myCursor = myDatabase.openCursor(txn, cc);
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();         
          
            if(myCursor.getFirst(foundKey, foundData, LockMode.DEFAULT)
                    == OperationStatus.SUCCESS)
            {
               
            	count++;
            	resultList.put(foundKey.getData(), foundData.getData());
              
            
                while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) 
                          == OperationStatus.SUCCESS) 
                {
                        count++;
                        resultList.put(foundKey.getData(), foundData.getData());
                     if(count>=num&&num!=0)
                     {
                    	 break;
                     }
                }
            }
            myCursor.close();
            txn.commit();
            return resultList;
        } 
        catch (Exception e) 
        {

            
            txn.abort();
            if (myCursor != null) 
            {
                myCursor.close();
            }
            return null;
        }
   }

}
