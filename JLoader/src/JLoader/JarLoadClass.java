package JLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JarLoadClass {
	
	/**
	 * 
	 * @param 加载目录
	 * @return 加载器
	 * @throws Exception
	 */
	public ClassLoader LoadClassLibs(String path) throws Exception
	{
	 /*动态加载指定类*/  
      File file=new File(path);//类路径(包文件上一层)  
      URL url=file.toURI().toURL();  
       ClassLoader loader=new URLClassLoader(new URL[]{url});//创建类加载器  
       return loader;
       
	}
	/**
	 * 加载jar
	 * @param Jarpath jar路径
	 * @return 加载器
	 * @throws Exception
	 */
	public ClassLoader LoadClassJar(String Jarpath) throws Exception
	{
    /*动态加载指定jar包调用其中某个类的方法*/  
      File  file=new File(Jarpath);//jar包的路径  
      URL  url=file.toURI().toURL();  
     ClassLoader  loader=new URLClassLoader(new URL[]{url});//创建类加载器  
 
     return loader;
	}
	/**
	 * 创建实例
	 * @param Jarpath  jar路径
	 * @param className 类名称（包括包名称）
	 * @return 类对象
	 * @throws Exception
	 */
	public Object LoadClassLibInstance(String path,String className) throws Exception
	{
		 /*动态加载指定jar包调用其中某个类的方法*/  
	      File  file=new File(path);//jar包的路径  
	      URL  url=file.toURI().toURL();  
	     @SuppressWarnings("resource")
		ClassLoader  loader=new URLClassLoader(new URL[]{url});//创建类加载器 
	     Class<?> cls=loader.loadClass(className);//加载指定类，注意一定要带上类的包名
          Object obj=cls.newInstance();//初始化一个实例  
            return obj;
	}
	/**
	 * 创建实例
	 * @param Jarpath  jar路径
	 * @param className 类名称（包括包名称）
	 * @return 类对象
	 * @throws Exception
	 */
	public Object LoadClassInstance(String Jarpath,String className) throws Exception
	{
		 /*动态加载指定jar包调用其中某个类的方法*/  
	      File  file=new File(Jarpath);//jar包的路径  
	      URL  url=file.toURI().toURL();  
	     @SuppressWarnings("resource")
		ClassLoader  loader=new URLClassLoader(new URL[]{url});//创建类加载器 
	     Class<?> cls=loader.loadClass(className);//加载指定类，注意一定要带上类的包名
          Object obj=cls.newInstance();//初始化一个实例  
            return obj;
	}
	/**
	 * 执行方法
	 * @param Jarpath jar路径
	 * @param className 类名称（包括包名称）
	 * @param funName 方法名称
	 * @param objPara 参数类型
	 * @param objValues 参数值
	 * @throws Exception
	 */
	public void LoadClassExe(String Jarpath,String className,String funName,@SuppressWarnings("rawtypes") Class[]objPara,Object[]objValues) throws Exception
	{   File  file=new File(Jarpath);//jar包的路径  
		      URL  url=file.toURI().toURL();  
		     @SuppressWarnings("resource")
			ClassLoader  loader=new URLClassLoader(new URL[]{url});//创建类加载器 
		     Class<?> cls=loader.loadClass(className);//加载指定类，注意一定要带上类的包名
	          Object obj=cls.newInstance();//初始化一个实例 
	          Method method=cls.getMethod(funName,objPara);//方法名和对应的参数类型  
	         method.invoke(obj,objValues);//调用得到的上边的方法method  
	
	}
}

