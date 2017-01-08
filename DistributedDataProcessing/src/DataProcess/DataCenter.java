package DataProcess;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.Tools.MsgPackTool;

import DataModel.QueueModel;

import DataModel.TreansferModel;
import DataQueue.DataBus;
import DataQueue.IQueueCall;
import DataQueue.QueueID;
import DataStore.PersistentStorage;

/**
 * 缓存数据
 * @author jinyu
 *
 */
public class DataCenter {
	static DataBus busdata=null;
    public	static IQueueCall call;
	static PersistentStorage persist=null;
	static Map<String,QueueModel> cacheData=null;
	static long keymill=0;//构造健值
	static volatile int delayNum=0;//延迟包数
	static final int   maxDelayNum=500;//最多延迟包数
	
/**
 * 添加数据
 * 如果数据过多，则丢入BerkeleyDB
 * @param model
 */
public static void AddData(TreansferModel model)
{
	if(busdata==null)
	{
		busdata=new DataBus(call);
	}
	QueueModel curModel=new QueueModel();
	curModel.id=System.currentTimeMillis();
	curModel.data=model.data;
	delayNum++;
	//可用小于10M则丢进数据库
//    if(Runtime.getRuntime().totalMemory()+10*1024*1024<Runtime.getRuntime().maxMemory())
//    {
//    	   busdata.AddData(curModel);
//    }
//    else
//    {
    	AddCahe(curModel);
   // }
    if(delayNum>maxDelayNum)
    {
    	AddCahe(null);
    }
    
}

/**
 * 对数据库数据进行处理
 * 
 * @param qmodel
 */
private static void AddCahe(QueueModel qmodel)
{
	if(persist==null)
	{
		persist=new PersistentStorage ();
		StartThreadDB();
	}
	if(cacheData==null)
	{
		cacheData=new HashMap<String,QueueModel>();
	}
	if(qmodel!=null)
	{
	if(keymill!= System.currentTimeMillis()/1000)
	{
		 QueueID.queueid=0;
		 keymill=System.currentTimeMillis()/1000;
	}

	String key=String.valueOf(keymill)+ QueueID.queueid++;
	cacheData.put(key, qmodel);
 	if(cacheData.size()>=100)
{  
		Map<String,QueueModel> temp=new HashMap<String,QueueModel>();
		temp.putAll(cacheData);
		cacheData.clear();
		delayNum=0;
		persist.Save(temp);
}
    }
	else
	{
		if(cacheData.size()>0)
		{
		
		Map<String,QueueModel> temp=new HashMap<String,QueueModel>();
		temp.putAll(cacheData);
		cacheData.clear();
		delayNum=0;
		persist.Save(temp);
		}
	}
	
}

private static void StartThreadDB() {
	Thread dataCahe=new Thread(new Runnable()
			{

				@Override
				public void run() {
				  MsgPackTool tool=new MsgPackTool();
				  StringBuilder error=new StringBuilder();
				while(true)
				{
			  try
			  {
				 List<byte[]> keys=new LinkedList<byte[]>();
				 Map<byte[],byte[]> data=	persist.SelectList(300);
				 for (byte[] key : data.keySet()) {  
				 byte[]  value = data.get(key);  //
				  //StorageModel<QueueModel> model1=  tool.Deserialize(value, StorageModel.class, error);
				 
				  QueueModel model=tool.Deserialize(value, QueueModel.class, error);
				 
				  busdata.AddData(model);
				  keys.add(key);
				 }
				
				//
					
			int num=	persist.DeleteList(keys);
			System.out.println("删除数据："+num);
				if(keys.size()>=100)
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
				else
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					
						e.printStackTrace();
					}
				}
			  }
					catch(Exception ex)
					{
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				  
				}
				}
			}
	);
	dataCahe.setDaemon(true);
	dataCahe.setName("DBCache");
	dataCahe.start();
}
}
