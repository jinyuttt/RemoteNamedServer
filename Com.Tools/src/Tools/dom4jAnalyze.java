package Tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class dom4jAnalyze {
public	String UBSSDIC_PATH;
	public void createApplicationConfigXML(){
        //建立document对象
		 try {
				 Document document = DocumentHelper.createDocument();
				 Element root = document.addElement("root");//添加文档根
				 root.addComment("这个一个注释");//加入一行注释
				 Element request = root.addElement("request"); //添加root的子节点
				 request.addAttribute("type", "cat");
				 request.addAttribute("flow", "tong");
				 request.addAttribute("time", "2009");
				 Element pro = request.addElement("pro");
				 pro.addAttribute("type", "att");
				 pro.addAttribute("name", "附件");
				 pro.addText("测试哈子");
				 
				 Element cd = request.addElement("pro");
				 cd.addAttribute("type", "cd");
				 cd.addAttribute("name", "特殊字符过滤");
				 cd.addCDATA("特殊字符");
				 
	             //输出全部原始数据，在编译器中显示
				 OutputFormat format = OutputFormat.createPrettyPrint();
				 format.setEncoding("GBK");//根据需要设置编码
				 XMLWriter writer = new XMLWriter(System.out, format);
				 document.normalize();
				 writer.write(document);  
				 writer.close();
				 // 输出全部原始数据，并用它生成新的我们需要的XML文件
				 XMLWriter writer2 = new XMLWriter(new FileWriter(new File("test.xml")), format);
				 writer2.write(document); //输出到文件
				 writer2.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getApplcationConfigFromXMLTest(){
		String value = "";
		try {
			SAXReader sax = new SAXReader();
			Document xmlDoc = sax.read(new File(this.UBSSDIC_PATH));
			Element root = xmlDoc.getRootElement();//根节点
			Iterator<?> it = root.elementIterator();
			while(it.hasNext()){
				Element ele = (Element)it.next();
				Attribute attribute = ele.attribute("type");
				if(attribute.getStringValue().equals("Pending")){
					attribute.setValue("sendread2");//修改属性节点的值
				}
	
				Attribute flowType = ele.attribute("flowType");
				flowType.detach();//删除某个属性
				
				ele.addAttribute("type", "Pending");//添加一个属性节点
			}
			Element new_cdata = root.addElement("new_cdata");//添加一个元素
			new_cdata.addCDATA("tst&ree");
			
			Element new_ele = root.addElement("new_ele");//添加一个元素
			new_ele.addText("33434343");

			Element obj = (Element)root.selectObject("//pro[@type='att']");//根据XPath查找元素
			obj.setText("测试dddddd");//修改元素的值 即text节点
              //输出全部原始数据，在编译器中显示
	           OutputFormat format = OutputFormat.createPrettyPrint();
	           format.setEncoding("GBK");
	           XMLWriter writer = new XMLWriter(System.out, format);
	           writer.write(xmlDoc);  
	           writer.close();
	           // 输出全部原始数据，并用它生成新的我们需要的XML文件
	           XMLWriter writer2 = new XMLWriter(new FileWriter(new File(
	             "test.xml")), format);
	           writer2.write(xmlDoc); //输出到文件
	           writer2.close();
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return value ;
	}
	public void parseApplicationConfigXML(){
		try {
			SAXReader sax = new SAXReader();
			Document xmlDoc = sax.read(new File("E:\\20090316HPS\\Dom4jTest\\t.xml"));
			Element root = xmlDoc.getRootElement();//根节点
			Iterator<?> it = root.elementIterator("request");
			while(it.hasNext()){
				Element request = (Element)it.next();
				System.out.println(request.getName());
				System.out.println(request.attributeValue("type"));
				System.out.println(request.attributeValue("flow"));
				System.out.println(request.attributeValue("time"));
				
			}
			System.out.println("-------------------------------------------");
			List<?> list = root.selectNodes("//pro");
			for(int i = 0; i < list.size(); i++){
				Element pro = (Element)list.get(i);
				System.out.println(pro.getName());
				System.out.println(pro.attributeValue("type"));
				System.out.println(pro.attributeValue("name"));
				System.out.println(pro.getText());
				System.out.println("+++++++++++++++++++++++++++++++++");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public HashMap<String,String> GetNodeValue(String name)
    {
    	HashMap<String,String> hmap=new HashMap<String,String>();
        int i=0;
		try {
			SAXReader sax = new SAXReader();
			Document xmlDoc = sax.read(new File(UBSSDIC_PATH));
			Element root = xmlDoc.getRootElement();//根节点
			Iterator<?> it = root.elementIterator(name);
			while(it.hasNext()){
				i++;
				Element request = (Element)it.next();
				String nameNode=request.getName()+String.valueOf(i);
				String nodetext=request.asXML();
			    hmap.put(nameNode, nodetext);
				
			}
			return hmap;
		} catch (DocumentException e) {
			
			e.printStackTrace();
			return hmap;
		}
    	
    	
    }
    public HashMap<String,String> GetKeyValue(String xmlString)
    {
    	HashMap<String,String> hmap=new HashMap<String,String>();
    	Document document = null;
		try {
			document = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
    	Element root = document.getRootElement();//根节点
		Iterator<?> it = root.elementIterator();
		while(it.hasNext()){
			Element request = (Element)it.next();
			hmap.put(request.getName(), request.getStringValue());
			
		}
    	return hmap;
    }
    public void WriteXml(String xmlString)
    {
    	
    	Document document = null;
		try {
			document = DocumentHelper.parseText(xmlString);
			 //输出全部原始数据，在编译器中显示
			 OutputFormat format = OutputFormat.createPrettyPrint();
			 format.setEncoding("utf-8");//根据需要设置编码
			 XMLWriter writer = new XMLWriter(System.out, format);
			 document.normalize();
			 writer.write(document);  
			 writer.close();
			 // 输出全部原始数据，并用它生成新的我们需要的XML文件
			 XMLWriter writer2 = new XMLWriter(new FileWriter(new File(this.UBSSDIC_PATH)), format);
			 writer2.write(document); //输出到文件
			 writer2.close();
		}
		catch(Exception ex)
		{
			
		}
    	
    }
    public HashMap<String,String> GetValue(String name)
    {
    	HashMap<String,String> hmap=new HashMap<String,String>();
        int i=0;
		try {
			SAXReader sax = new SAXReader();
			Document xmlDoc = sax.read(new File(UBSSDIC_PATH));
			Element root = xmlDoc.getRootElement();//根节点
			Iterator<?> it = root.elementIterator(name);
			while(it.hasNext()){
				i++;
				Element request = (Element)it.next();
				String nameNode=request.getName()+String.valueOf(i);
				String nodetext=request.getStringValue();
			    hmap.put(nameNode, nodetext);
				
			}
			return hmap;
		} catch (DocumentException e) {
			
			e.printStackTrace();
			return hmap;
		}
    }

}
