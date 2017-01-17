/**
 * 
 */
package FileBuffer;

import java.io.File;
import java.io.FileFilter;


/** 
* 
* @Description:ɸѡ
* @author jinyu
* @date 2016��10��31�� ����3:34:51 
*  
*/
public class DataFileFilter implements FileFilter  {

	String ext;
  public DataFileFilter(String ext)
  {
	  this.ext=ext;
  }
	
	/* (non-Javadoc)
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File pathname) {
		 if (pathname.getName().endsWith(ext))
		      return true;
		return false;
	}

}
