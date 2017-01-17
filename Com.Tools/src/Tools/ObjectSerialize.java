/**
 * 
 */
package Tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** 
* 
* @Description: java对象序列化反序列化
* @author jinyu
* @date 2016年10月30日 下午11:55:25 
*  
*/
public class ObjectSerialize {

	public static byte[] ObjSerialize(Object obj)
	{
		  
	      
	           
	        	ByteArrayOutputStream bos = new ByteArrayOutputStream();

	        	ObjectOutputStream os;
				try {
					os = new ObjectOutputStream(bos);
					os.writeObject(obj);

		        	os.flush();

		        	os.close();

		        	byte[] b = bos.toByteArray();

		        	bos.close();
		        	return b;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}

	        
	       

	}
	public static Object ObjDeSerialize(byte[]bytes)
	{
		     ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);

			    try {
			    	   ObjectInputStream    ois2 = new ObjectInputStream(byteInStream);
					 
					   try {
						   Object 	obj = ois2.readObject();
						   return obj;
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					}
					   ois2.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
			return null;
	     
	}
}
