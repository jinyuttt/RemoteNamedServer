package Tools;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;


public class MsgPackTool {
	
	/**
	 * ���л�
	 * @param obj ���� 
	 * @param error ������Ϣ
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
   * �������л�
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
   * �����л�
   * @param bytes �ֽ�
   * @param error ������Ϣ
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
   * ���������л�
   * @param bytes
   * @param size �������
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
