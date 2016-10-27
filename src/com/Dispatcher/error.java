package com.Dispatcher;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Error")
public class error extends PackageXml{
	public String RequestId;
	public String Code ;
	public String Message;
	public String HostId;
}
