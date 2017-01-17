package Tools;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlAnalyze {
public String fileName;
Document doc=null;
public boolean Init(String path,StringBuilder error)
{
	error=new StringBuilder();
	try
	{
	   File f=new File(fileName);
       DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
       DocumentBuilder builder=factory.newDocumentBuilder(); 
        doc = builder.parse(f); 
       return true;
	}
	catch(Exception ex)
	{
		error.append(ex.getMessage());
		return false;
	}
}
 public String GetValue(String path) throws Exception
 {
	   File f=new File(fileName);
       DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
       DocumentBuilder builder=factory.newDocumentBuilder(); 
       Document doc = builder.parse(f); 
       NodeList nl = doc.getElementsByTagName("VALUE"); 
   
      return nl.item(0).getNodeValue();
 }
}
