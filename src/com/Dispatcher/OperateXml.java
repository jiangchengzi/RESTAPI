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
	public void AlterOpt(String DBInstanceID,String DBInstanceName,String DBInstanceENGINE ,String DBInstanceSTATUS,String DBInstanceIP,String DBInstancePORT,String DBInstanceDRV,String DBInstanceURL,String DBInstanceUSER,String DBInstancePWD){
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();//修改实例节点,如果修改项为空，则不修改
		dbf.setIgnoringElementContentWhitespace(true);
		int index=0;
		int itemindex=0;
		String[] dbinfo={DBInstanceID,DBInstanceName,DBInstanceENGINE,DBInstanceSTATUS,DBInstanceIP,DBInstancePORT,DBInstanceDRV,DBInstanceURL,DBInstanceUSER,DBInstancePWD};
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
							if(nodes.item(j).getTextContent().equals(DBInstanceID)){
							index=i;
							}
					}
				}
			}
			Element setinstance=(Element) dblist.item(index);
			NodeList nodes=setinstance.getChildNodes();
			for(int j=0;j<nodes.getLength();j++){
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE ){
					if(dbinfo[itemindex]!=null){
					nodes.item(j).setTextContent(dbinfo[itemindex]);
					}
					itemindex=itemindex+1;
				}
			}	
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult("/usr/local/DBInstanceInfo.xml"));
		}catch(Exception e){e.printStackTrace();}
	}
	public Map<String,String> SelectOpt(String DBInstanceId){//搜索某一实例节点
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		Map<String,String> result=new HashMap<String,String>();
		dbf.setIgnoringElementContentWhitespace(true);
		int  index = -1;
		int itemindex=0;
		String[] dbinfo={"DBInstanceID","DBInstanceName","DBInstanceENGINE","DBInstanceSTATUS","DBInstanceIP","DBInstancePORT","DBInstanceDRV","DBInstanceURL","DBInstanceUSER","DBInstancePWD"};
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml"); 
			NodeList dblist=doc.getElementsByTagName("DBInstance");
			
			for(int i=0;i<dblist.getLength();i++){
				Element dbinstance=(Element) dblist.item(i);
				NodeList nodes=dbinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE&&nodes.item(j).getNodeName().equals("DBInstanceID")){
							if(nodes.item(j).getTextContent().equals(DBInstanceId)){
							   index=i;
							}
					}
				}
			}
			if(index>=0){
			Element getinstance=(Element) dblist.item(index);
			NodeList nodes=getinstance.getChildNodes();
			for(int j=0;j<nodes.getLength();j++){
				
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE){
						result.put(dbinfo[itemindex], nodes.item(j).getTextContent());
						itemindex=itemindex+1;
				}
			}	
			}
		}catch(Exception e){e.printStackTrace();}
		return result;
	}
	//还要添加一个遍历所有实例节点的方法，供描述所有实例信息（describedbinstances）时使用
	public List<String> GetAllDbinstances(){
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		List<String> result =new ArrayList<String>();
		String DBInstanceID = null;
		String status="false";
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml"); 
			NodeList dblist=doc.getElementsByTagName("DBInstance");
			
			for(int i=0;i<dblist.getLength();i++){
				Element dbinstance=(Element) dblist.item(i);
				NodeList nodes=dbinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
					if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE&&nodes.item(j).getNodeName().equals("DBInstanceID")){
						DBInstanceID=nodes.item(j).getTextContent();
					} 
					if(nodes.item(j).getNodeName().equals("DBInstanceSTATUS")&&nodes.item(j).getTextContent().equals("unavailable")){
						status="true";
					}
					
				}
				if(status.equals("true")){
					result.add(DBInstanceID);
				}
				status="false";
				
			}	
		}catch(Exception e){e.printStackTrace();}
		return result;
	}
	public Map<String,String> SelectUnusedOpt(String Engine){//获取可用实例节点
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		Map<String,String> result=new HashMap<String,String>();
		dbf.setIgnoringElementContentWhitespace(true);
		int  index = -1;
		String findengine="false";
		int itemindex=0;
		String[] dbinfo={"DBInstanceID","DBInstanceName","DBInstanceENGINE","DBInstanceSTATUS","DBInstanceIP","DBInstancePORT","DBInstanceDRV","DBInstanceURL","DBInstanceUSER","DBInstancePWD"};
		try{
			DocumentBuilder db=dbf.newDocumentBuilder();//创建xml文档解析器
			Document doc=db.parse("/usr/local/DBInstanceInfo.xml"); 
			NodeList dblist=doc.getElementsByTagName("DBInstance");
			
			for(int i=0;i<dblist.getLength();i++){
				Element dbinstance=(Element) dblist.item(i);
				NodeList nodes=dbinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
				if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE){
							if(nodes.item(j).getTextContent().equals(Engine)){
								
								findengine="true";
							}
							if(nodes.item(j).getTextContent().equals("available")&&findengine.equals("true"))
							{
								index=i;
							}
							
					}
				}
				findengine="false";
			}
			if(index>=0){//当搜索到可用实例后，则返回
				Element getinstance=(Element) dblist.item(index);
				NodeList nodes=getinstance.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
					
					if(nodes.item(j).getNodeType()==Node.ELEMENT_NODE){
							result.put(dbinfo[itemindex], nodes.item(j).getTextContent());
							itemindex=itemindex+1;
					}
				}	
			}
		}catch(Exception e){e.printStackTrace();}
		return result;
	}
}
