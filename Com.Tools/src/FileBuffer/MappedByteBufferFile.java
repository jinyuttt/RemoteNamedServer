/**
 * 
 */
package FileBuffer;


import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/** 
* 
* @Description: TODO(������һ�仰��������������) 
* @author jinyu
* @date 2016��10��31�� ����12:26:33 
*  
*/
public class MappedByteBufferFile {
	String curfile;
	int bufferSize;
	long index=0;
	boolean preWrite=true;
	RandomAccessFile memoryMappedFile = null;
	byte[]dstread=null;
public MappedByteBufferFile(String fileName,int size)
{
	curfile=fileName;
	bufferSize=size;
	
}
//�����ļ�
private void CreateFile()
{
	RandomAccessFile raf = null;
	try {
	
		//����һ��ָ����С�Ŀ��ļ�
		raf = new RandomAccessFile(curfile, "rw");
		raf.setLength(bufferSize);
	    
	} catch (Exception e) {
	} finally {
		if ( raf != null ) {
			try {
				raf.close();
			} catch (Exception e2) {
			}
		}
	}
	
}
//д���ļ�
public void Write(byte[]data)
{ 
	if(preWrite)
	{
		preWrite=false;
		CreateFile();
	}
	MappedByteBuffer out;
	if(memoryMappedFile==null)
	{
	try {
		memoryMappedFile = new RandomAccessFile(curfile, "rw");
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		return;
	}
	}

try {
	out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, index, data.length);
	out.put(data);
	out.force();
} catch (IOException e) {
	
	e.printStackTrace();
}
index+=data.length;

}
//��ȡ�ļ�
public byte[] Read()
{
	MappedByteBuffer out;
	long leftsize=0;
//	if(dstread==null)
//	{
//		dstread=new byte[bufferSize];
//	}
	if(memoryMappedFile==null)
	{
	try {
		memoryMappedFile = new RandomAccessFile(curfile, "rw");
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		return null;
	}
	}
	//
	try {
		if(index+bufferSize<memoryMappedFile.length())
		{
			leftsize=bufferSize;
		}
		else
		{
			leftsize=memoryMappedFile.length()-index;
		}
		
		out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, index,leftsize);
		  return	out.array();
	} catch (IOException e) {
		 return null;
		
	}
  
	
}
public void Close()
{
	try {
		memoryMappedFile.close();
		RandomAccessFile raf = new RandomAccessFile(curfile, "rw");
		
		raf.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void WriteClose()
{
	try {
		memoryMappedFile.close();
		RandomAccessFile raf = new RandomAccessFile(curfile, "rw");
		raf.setLength(index);
		raf.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
