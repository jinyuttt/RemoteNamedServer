package Tools;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;


public class MsgPackTool {
	
	/**
	 * 序列化
	 * @param obj 对象 
	 * @param error 错误信息
	 * @return
	 */
  public <T> byte[]   Serialize(T obj,StringBuilder error) 
  {
	  
	  if(obj==null)
	  {
		  return null;
	  }
	
	  MessagePack msgpack = new MessagePack();
	msgpack.register(obj.getClass());
	byte[] raw=null;
	try {
		raw = msgpack.write(obj);
	} catch (Exception e) {
		error.append(e.getMessage());
	}
	
	  return raw;
  }
  /**
   * 批量序列化
   * @param obj
   * @param error
   * @return
   */
  public <T> byte[]  ArrySerialize(T[] obj,StringBuilder error) 
  {
	  
	  if(obj==null||obj.length==0)
	  {
		  return null;
	  }
	  error=new StringBuilder();
	  MessagePack msgpack = new MessagePack();
	  msgpack.register(obj[0].getClass());
	byte[] raw=null;
	try {
		
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Packer packer = msgpack.createPacker(out);
	       for(T temp:obj)
	       {
	    	   packer.write(temp);
	       }
	     raw = out.toByteArray();
	} catch (Exception e) {
		error.append(e.getMessage());
	}
	
	  return raw;
  }
  /**
   * 反序列化
   * @param bytes 字节
   * @param error 错误信息
   * @return 
   */
  public <T> T Deserialize(byte[] bytes,Class<?> Tobj,StringBuilder error) 
  {
	  if(bytes==null||bytes.length==0)
	  {
		  return null;
	  }
	  error=new StringBuilder();
	  MessagePack msgpack = new MessagePack();
	msgpack.register(Tobj);
	try {
		@SuppressWarnings("unchecked")
		T  obj =(T)msgpack.read(bytes,Tobj);
		return obj;
		
	} catch (Exception e) {
	     error.append(e.getMessage());
	     return null;
	}
	
	
  }
  /**
   * 批量反序列化
   * @param bytes
   * @param size 对象个数
   * @param error
   * @return
   */
  public <T> ArrayList<T> ArryDeserialize(byte[] bytes,Class<?> Tobj,int size,StringBuilder error) 
  {
	  if(bytes==null||bytes.length==0)
	  {
		  return null;
	  }
	  error=new StringBuilder();
	  MessagePack msgpack = new MessagePack();
	  msgpack.register(Tobj);
	try {
		ArrayList<T> list=new ArrayList<T>(size);
		@SuppressWarnings("unchecked")
		T  obj =(T)msgpack.read(bytes,Template.class);
	       list.add(obj);
	       return list;
		
	} catch (Exception e) {
	     error.append(e.getMessage());
	     return null;
	}
	
	
  }
}
