package com.Tools;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.util.Map;

import org.msgpack.MessagePack;

public class MsgPackHelper {
 static Map<String,LinkedList<Class<?>>> msgPackTemplateInfo=new HashMap<String,LinkedList<Class<?>>>();
 
 /**
  * 获取类型
 * @param pack
 * @param obj
 */
public static  <T>  void    GetTemplate(MessagePack pack,T obj)
  {
	 
	  //InitBaseType();

	  Map<String,Class<?>> fileld=new LinkedHashMap<String,Class<?>>();
	  //
	  Class<?> cla=obj.getClass();
	  pack.register(Object.class, ObjectTemplate.getInstance());
	  pack.register(cla);
	 
	  try
	  {
		  LinkedList<Class<?>> lst =null;
		  lst=msgPackTemplateInfo.getOrDefault(obj.getClass().getName(), null);
		  if(lst==null)
		  {
			  GetDefineType(obj,obj.getClass(),fileld);
			  if(fileld.size()>0)
			  {
				  fileld.remove(Object.class.getName());
				  fileld.remove(cla.getName());
				 
				  lst=new LinkedList<Class<?>>();
				  if(fileld.size()>0)
				  {
					 
					 lst.addAll(fileld.values());
					 
				  }
				  msgPackTemplateInfo.put(cla.getName(), lst);
			  }
		  }
		  if(lst!=null)
		  {
			  LinkedList<Class<?>> lsttemp=new LinkedList<Class<?>>();
			  lsttemp.addAll(lst);
			  while(lsttemp.size()>0)
			  {
				  pack.register(lsttemp.removeLast());
			  }
			  
		  }
	 }
	  catch(Exception ex)
	  {
		 System.out.println(ex.getMessage());
	  }
   
  }
  
  /**
 * @param pack
 * @param cla
 */
public static  <T>  void  GetTemplate(MessagePack pack,Class<? extends Object> cla)
  {
	 
	
	
	  Map<String,Class<?>> fileld=new HashMap<String,Class<?>>();
	  //
	 
	  pack.register(Object.class, ObjectTemplate.getInstance());
	
	  pack.register(cla);
	 
	  try
	  {
		  LinkedList<Class<?>> lst =null;
		  lst=msgPackTemplateInfo.getOrDefault(cla.getName(), null);
		  if(lst==null)
		  {
			  GetDefineType(null,cla,fileld);
			  if(fileld.size()>0)
			  {
				  fileld.remove(Object.class.getName());
				  fileld.remove(cla.getName());
				 
				  lst=new LinkedList<Class<?>>();
				  if(fileld.size()>0)
				  {
					 
					 lst.addAll(fileld.values());
					 
				  }
				  msgPackTemplateInfo.put(cla.getName(), lst);
			  }
		  }
		  if(lst!=null)
		  {
			  LinkedList<Class<?>> lsttemp=new LinkedList<Class<?>>();
			  lsttemp.addAll(lst);
			  while(lsttemp.size()>0)
			  {
				  pack.register(lsttemp.removeLast());
			  }
			  
		  }
	 
	//
		 
		
	  }
	  catch(Exception ex)
	  {
		 System.out.println(ex.getMessage());
	  }
   
  }
  
  
  /**
 * @param Tobj
 * @param cla
 * @param fileld
 */
private static <T> void GetDefineType(T Tobj,  Class<? extends Object> cla,Map<String,Class<?>> fileld)
  {
	 
		 Field[] fields= cla.getDeclaredFields();
		  for(Field f:fields)
		  {
			 String selfInfo=f.getType().getName();
		    if(selfInfo.length()>4)
		    {
		    	  String childStr=selfInfo.substring(0,4);
		    	  
		    	
			      if(!childStr.equalsIgnoreCase("java")&&!f.getType().isPrimitive())
			      {
			    	  fileld.put(selfInfo, f.getType());
			    	  try {
						 Object fieldValue=f.get(Tobj);
						 GetDefineType(fieldValue,f.getType(),fileld);
					} catch (IllegalArgumentException e) {
						
						e.printStackTrace();
					} catch (IllegalAccessException e) {
					
						e.printStackTrace();
					}
			    	
			      }
			      //
			      else if(f.getType().equals(Object.class))
			      {
			    	  if(Tobj!=null)
			    	  {
			    		Class<?> clb;
						try {
							Object fieldValue=f.get(Tobj);
							clb = fieldValue.getClass();
							if(!clb.equals(Object.class)&&!clb.isPrimitive())
				    		{
								 selfInfo=clb.getName();
								
								 if(selfInfo.length()>4)
								 {
									 childStr=selfInfo.substring(0,4);
									 if(!childStr.equalsIgnoreCase("java"))
								      {
									    fileld.put(clb.getName(), clb);
									    //
									     GetDefineType(fieldValue,clb,fileld);
								      }
								 }
								 
				    		}
						} catch (IllegalArgumentException e) {
							
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							
							e.printStackTrace();
						}
			    		
			    	  }
			      }
		    }
		    
		  }
  }
 
  public static void AddTemplate(String name ,Class<?> cla)
  {
	  LinkedList<Class<?>> lst =null;
	  lst=msgPackTemplateInfo.getOrDefault(name, null);
	  if(lst==null)
	  {
		  lst=new LinkedList<Class<?>>();
		  lst.add(cla);
		  msgPackTemplateInfo.put(name, lst);
		  
	  }
	  else
	  {
		  lst.add(cla);
	  }
  }
  public static void AddTemplate(Class<?> cla)
  {
	  LinkedList<Class<?>> lst =null;
	  lst=msgPackTemplateInfo.getOrDefault(cla.getName(), null);
	  if(lst==null)
	  {
		  lst=new LinkedList<Class<?>>();
		  lst.add(cla);
		  msgPackTemplateInfo.put(cla.getName(), lst);
		  
	  }
	  else
	  {
		  lst.add(cla);
	  }
  }
  public static void AddListTemplate(String name ,LinkedList<Class<?>> lstcla)
  {
	  LinkedList<Class<?>> lst =null;
	  lst=msgPackTemplateInfo.getOrDefault(name, null);
	  if(lst==null)
	  {
		  lst=new LinkedList<Class<?>>();
		  lst.addAll(lstcla);
		  msgPackTemplateInfo.put(name, lst);
		  
	  }
	  else
	  {
		  lst.addAll(lstcla);
	  }
  }
  public static void AddListTemplate(LinkedList<Class<?>> lstcla)
  {
	  
	  for(Class<?> c: lstcla)
	  {
		  AddTemplate(c);
	  }
	  
	 
  }
}
