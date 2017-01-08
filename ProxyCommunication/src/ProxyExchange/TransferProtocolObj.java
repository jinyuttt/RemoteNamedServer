/**
 * 
 */

package ProxyExchange;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import ClassUtil.JarUtil;
import DDS_Transfer.ProtocolType;



/** 
* 
* @Description: ���Ҵ���Ŀ¼�еĴ������
* @author jinyu
* @date 2016��10��31�� ����11:54:31 
*  
*/
public class TransferProtocolObj {
private static TransferProtocolObj instance=new TransferProtocolObj();
private TransferProtocolObj(){};
public static TransferProtocolObj getInstance()
{
	return instance;
}
	ConcurrentHashMap<String,Class<?>> hashmap=new ConcurrentHashMap<String, Class<?>>();
	public  Object CreateObject(String name) throws InstantiationException, IllegalAccessException
	{
		if(hashmap.size()==0)
		{
			Init("transfer");
		}
		Class<?> cl=hashmap.getOrDefault(name.toLowerCase(), null);
		if(cl!=null)
		{
		 return	cl.newInstance();
		}
		else
		{
			return null;
		}
		//return hashmap.getOrDefault(name, null).newInstance();
	}
public void Init(String protocolfile)
{
     	JarUtil  loader=new JarUtil();
		List<Class<?>> all=loader.getPathClass(protocolfile,new Class<?>[]{ DDS_Transfer.IDDS_Protocol.class});
		if(all!=null&&all.size()>0)
		{
			for(int i = 0;i<all.size();i++)
			{
				Class<?> tmp=(Class<?>)all.get(i);
				if(tmp.isAnnotationPresent(ProtocolType.class))
				{
					ProtocolType annotation=  tmp.getAnnotation(ProtocolType.class);
					hashmap.put(annotation.Name().toLowerCase(), tmp);
					
			    }
					
					
			}
		}
}
}