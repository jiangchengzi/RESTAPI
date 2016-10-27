package com.Dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OperateXml {
	public void InsertOpt(String DBInstanceId,String DBInstanceNAME,String IP,String PORT){//插入新的实例节点
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml");
			Element root=doc.getDocumentElement();
			Element DBInstance=doc.createElement("DBInstance");
			Element DBInstanceid=doc.createElement("DBInstanceId");
			Element DBInstancename=doc.createElement("DBInstanceName");
			Element DBInstanceip=doc.createElement("DBInstanceIP");
			Element DBInstanceport=doc.createElement("DBInstancePORT");
			DBInstanceid.setTextContent(DBInstanceId);
			DBInstancename.setTextContent(DBInstanceNAME);
			DBInstanceip.setTextContent(IP);
			DBInstanceport.setTextContent(PORT);
			DBInstance.appendChild(DBInstanceid);
			DBInstance.appendChild(DBInstancename);
			DBInstance.appendChild(DBInstanceip);
			DBInstance.appendChild(DBInstanceport);
			root.appendChild(DBInstance);
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult("/usr/local/DBInstanceInfo.xml"));
		}catch(Exception e){e.printStackTrace();}
	}
	public void RemoveOpt(String DBInstanceId){//移除实例节点
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		int index=0;
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml");
			NodeList dblist=doc.getElementsByTagName("DBInstance");
			Element root=doc.getDocumentElement();
			for(int i=0;i<dblist.getLength();i++){
				Element dbinstance=(Element) dblist.item(i);
				NodeList nodes=dbinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE){
							if(nodes.item(j).getTextContent().equals(DBInstanceId)){
							index=i;
							System.out.println("index="+index);
							}
							System.out.println(DBInstanceId+dblist.getLength()+i);
					}
				}
			}
			Element RemoveNode=(Element) dblist.item(index);
			root.removeChild(RemoveNode);
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult("/usr/local/DBInstanceInfo.xml"));
		}catch(Exception e){e.printStackTrace();}
	}
	public Map<String,String> SelectOpt(String DBInstanceId){//搜索某一实例节点
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		Map<String,String> result=new HashMap<String,String>();
		dbf.setIgnoringElementContentWhitespace(true);
		int  index = 0;
		int itemindex=0;
		String[] dbinfo={"DBInstanceID","DBInstanceName","DBInstanceIP","DBInstancePORT","DBInstanceDRV","DBInstanceURL","DBInstanceUSER","DBInstancePWD"};
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml"); 
			NodeList dblist=doc.getElementsByTagName("DBInstance");
			
			for(int i=0;i<dblist.getLength();i++){
				Element dbinstance=(Element) dblist.item(i);
				NodeList nodes=dbinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE){
							if(nodes.item(j).getTextContent().equals(DBInstanceId)){
							index=i;
							}
							//findresult="True";
					}
				}
			}
			Element getinstance=(Element) dblist.item(index);
			NodeList nodes=getinstance.getChildNodes();
			for(int j=0;j<nodes.getLength();j++){
				
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE){
						result.put(dbinfo[itemindex], nodes.item(j).getTextContent());
						itemindex=itemindex+1;
				}
			}	
		}catch(Exception e){e.printStackTrace();}
		return result;
	}
	//还要添加一个遍历所有实例节点的方法，供描述所有实例信息（describedbinstances）时使用
	public List<String> GetAllDbinstances(){//搜索某一实例节点
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		List<String> result =new ArrayList<String>();
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml"); 
			NodeList dblist=doc.getElementsByTagName("DBInstance");
			
			for(int i=0;i<dblist.getLength();i++){
				Element dbinstance=(Element) dblist.item(i);
				NodeList nodes=dbinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE&&nodes.item(j).getNodeName().equals("DBInstanceID")){
					result.add(nodes.item(j).getTextContent());
					}
				}
			}	
		}catch(Exception e){e.printStackTrace();}
		return result;
	}
}
