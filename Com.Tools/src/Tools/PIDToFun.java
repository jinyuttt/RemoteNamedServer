package Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.Random;

/**
 * 机器内当前唯一值
 * @author jinyu
 *
 */
public class PIDToFun {
	public static final String getPID() {   
        String pid = System.getProperty("pid");   
        if (pid == null) {   
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();   
            String processName = runtimeMXBean.getName();   
            if (processName.indexOf('@') != -1) {   
                pid = processName.substring(0, processName.indexOf('@'));   
            } else {   
                   return FileSingle();
            }   
            System.setProperty("pid", pid);   
        }   
        return pid;   
    } 
static  String FileSingle()
{
	String  sigle="0";
	try
	{
	
	int  curInt=0;
	 File file = new File("JVMLock.dat");  
	 if(!file.exists())
	 {
		 file.createNewFile();
		 
	 }
     FileOutputStream fout= new FileOutputStream(file);  
     FileInputStream  fin=new FileInputStream(file);
     FileChannel fc = fin.getChannel();  
     FileLock flock = fc.lock();  
     if(flock.isValid()){  
         System.out.println(file.getName()+ "is locked");  
     } 
    int r=  fin.read();
    if(r==-1)
    {
    	Date cur=new Date();
    	Random rdm=new Random(cur.getTime());
        curInt=	rdm.nextInt(500)+1;
      
    }
    else
    {
    	curInt=r;
    	
    }
    //
    int result=curInt+1;
    if(result==Integer.MAX_VALUE-1000)
    {
    	Date cur=new Date();
    	Random rdm=new Random(cur.getTime());
    	result=	rdm.nextInt(500)+1;
    }
    fout.write(result);
    fout.close();
    flock.release();  
    System.out.println(file.getName()+ "is released");  
    fc.close();
    fin.close();
    sigle=String.valueOf(curInt);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return sigle;
 }  
}
	


