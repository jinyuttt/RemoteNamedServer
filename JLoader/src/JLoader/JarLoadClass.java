package JLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JarLoadClass {
	
	/**
	 * 
	 * @param ����Ŀ¼
	 * @return ������
	 * @throws Exception
	 */
	public ClassLoader LoadClassLibs(String path) throws Exception
	{
	 /*��̬����ָ����*/  
      File file=new File(path);//��·��(���ļ���һ��)  
      URL url=file.toURI().toURL();  
       ClassLoader loader=new URLClassLoader(new URL[]{url});//�����������  
       return loader;
       
	}
	/**
	 * ����jar
	 * @param Jarpath jar·��
	 * @return ������
	 * @throws Exception
	 */
	public ClassLoader LoadClassJar(String Jarpath) throws Exception
	{
    /*��̬����ָ��jar����������ĳ����ķ���*/  
      File  file=new File(Jarpath);//jar����·��  
      URL  url=file.toURI().toURL();  
     ClassLoader  loader=new URLClassLoader(new URL[]{url});//�����������  
 
     return loader;
	}
	/**
	 * ����ʵ��
	 * @param Jarpath  jar·��
	 * @param className �����ƣ����������ƣ�
	 * @return �����
	 * @throws Exception
	 */
	public Object LoadClassLibInstance(String path,String className) throws Exception
	{
		 /*��̬����ָ��jar����������ĳ����ķ���*/  
	      File  file=new File(path);//jar����·��  
	      URL  url=file.toURI().toURL();  
	     @SuppressWarnings("resource")
		ClassLoader  loader=new URLClassLoader(new URL[]{url});//����������� 
	     Class<?> cls=loader.loadClass(className);//����ָ���࣬ע��һ��Ҫ������İ���
          Object obj=cls.newInstance();//��ʼ��һ��ʵ��  
            return obj;
	}
	/**
	 * ����ʵ��
	 * @param Jarpath  jar·��
	 * @param className �����ƣ����������ƣ�
	 * @return �����
	 * @throws Exception
	 */
	public Object LoadClassInstance(String Jarpath,String className) throws Exception
	{
		 /*��̬����ָ��jar����������ĳ����ķ���*/  
	      File  file=new File(Jarpath);//jar����·��  
	      URL  url=file.toURI().toURL();  
	     @SuppressWarnings("resource")
		ClassLoader  loader=new URLClassLoader(new URL[]{url});//����������� 
	     Class<?> cls=loader.loadClass(className);//����ָ���࣬ע��һ��Ҫ������İ���
          Object obj=cls.newInstance();//��ʼ��һ��ʵ��  
            return obj;
	}
	/**
	 * ִ�з���
	 * @param Jarpath jar·��
	 * @param className �����ƣ����������ƣ�
	 * @param funName ��������
	 * @param objPara ��������
	 * @param objValues ����ֵ
	 * @throws Exception
	 */
	public void LoadClassExe(String Jarpath,String className,String funName,@SuppressWarnings("rawtypes") Class[]objPara,Object[]objValues) throws Exception
	{   File  file=new File(Jarpath);//jar����·��  
		      URL  url=file.toURI().toURL();  
		     @SuppressWarnings("resource")
			ClassLoader  loader=new URLClassLoader(new URL[]{url});//����������� 
		     Class<?> cls=loader.loadClass(className);//����ָ���࣬ע��һ��Ҫ������İ���
	          Object obj=cls.newInstance();//��ʼ��һ��ʵ�� 
	          Method method=cls.getMethod(funName,objPara);//�������Ͷ�Ӧ�Ĳ�������  
	         method.invoke(obj,objValues);//���õõ����ϱߵķ���method  
	
	}
}

