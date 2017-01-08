package ClassUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtil {
/**
 * 获取Jar文件中的类
 * @param jarFile 文件路径
 * @param iface 接口
 * @return 实现接口的类
 */
public  List<Class<?>> GetJarClass(String jarFile,Class<?>[] iface)
{
	JarFile jartemp;
	 List<Class<?>> list =new ArrayList<Class<?>>();
	// File curfile=new File(jarFile);
	try {
		jartemp = new JarFile(jarFile);
		 Enumeration<JarEntry> en = jartemp.entries();
		  File file=new File(jarFile);
		  while (en.hasMoreElements()) { // 遍历显示JAR文件中的内容信息  
			 
			  JarEntry entry = en.nextElement();  
              String name = entry.getName();  
              // 如果是以/开头的  
              if (name.charAt(0) == '/') {  
                  // 获取后面的字符串  
                  name = name.substring(1);  
              }  
              if (name.endsWith(".class") && !entry.isDirectory()) {  
                    name = name.replace("/", ".").substring(0,  
                              name.lastIndexOf("."));  
                      try {  
                    	

                    @SuppressWarnings("resource")
					URLClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()},Thread.currentThread().getContextClassLoader());
                    Class<?> processorClass = loader.loadClass(name);
                        //  Class<?> c = Class.forName(name);
                    	//  Class<?> processorClass =Class.forName(name, false, Thread.currentThread().getContextClassLoader());
                          if (isFunction(iface,processorClass)) {  
                            
							list.add(processorClass);  
                          }  
                      } catch (Exception e) {  
                        e.printStackTrace(); 
                      }  
                  }  
              
	      } 
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 return list;
}
private List<String> getFiles(String filePath)
{
        List<String>   filelist=new ArrayList<String>();
       File root = new File(filePath);
       if(!root.exists())
       {
    	   System.out.println("没有找到路径");
       }
	    File[] files = root.listFiles();
	    if(files==null)
	    {
	    	return filelist;
	    }
	    for(File file:files){     
	     if(file.isDirectory()){
	      /*
	       * 递归调用
	       */
	    	  List<String>   filechildlist = getFiles(file.getAbsolutePath());
	    	  filelist.addAll(filechildlist);
	     }
	    else{
	    	
	    	if(file.isFile()&&file.getName().endsWith(".jar"))
	    	{
	    	  filelist.add(file.getAbsolutePath());
	    	}
	    }
	    }
	   return filelist;
}   
/**
 * 获取目录下的所有类
 * @param filePath 文件目录
 * @param iface 接口
 * @return 目录下实现接口的类
 */
public List<Class<?>> getPathClass(String filePath,Class<?>[] iface)
	    {
	           List<String>   filelist=getFiles(filePath);
	           List<Class<?>> list =new ArrayList<Class<?>>();
	           for(String file : filelist)
	           {
	        	   try
	        	   {
	        	   List<Class<?>> lst= GetJarClass(file,iface);
	        	   if(lst.size()>0)
	        	   {
	        	     list.addAll(lst);
	        	   }
	        	   }
	        	   catch(Exception ex)
	        	   {
	        		   ex.printStackTrace();
	        	   }
	           }
	           return list;
	   }

/**
 * 判断实现接口
 * @param cls 接口
 * @param c 类
 * @return
 */
public static boolean isFunction(Class<?>[] cls,Class<?> c) {
	if (c == null) {
		return false;
	}
	if (c.isInterface()) {
		return false;
	}
	if (Modifier.isAbstract(c.getModifiers())) {
		return false;// 抽象
	}
	boolean isy=false;
	for(int i=0;i<cls.length;i++)
	{
		isy=cls[i].isAssignableFrom(c);
		if(isy)
		{
			break;
		}
	}
	 
	
    return	isy;
   
	
}
 
}
