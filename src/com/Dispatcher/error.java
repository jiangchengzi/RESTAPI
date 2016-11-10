package com.Dispatcher;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Error")
public class error extends PackageXml{
	private String RequestId;
	private String Code ;
	private String Message;
	private String HostId;
	public String getRequestId(){
		return this.RequestId;
	}
	public void setRequestId(String RequestId){
		this.RequestId=RequestId;
	}
	public String getCode(){
		return this.Code;
	}
	public void setCode(String Code){
		this.Code=Code;
	}
	public String getMessage(){
		return this.Message;
	}
	public void setMessage(String Message){
		this.Message=Message;
	}
	public String getHostId(){
		return this.HostId;
	}
	public void setHostId(String HostId){
		this.HostId=HostId;
	}
	
}
