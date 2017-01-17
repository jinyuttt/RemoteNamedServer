/**
 * 
 */
package FileBuffer;

import java.io.File;
import java.io.FileFilter;


/** 
* 
* @Description:筛选
* @author jinyu
* @date 2016年10月31日 上午3:34:51 
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
