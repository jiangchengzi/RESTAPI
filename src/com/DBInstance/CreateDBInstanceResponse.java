package com.DBInstance;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.PackageXml;

@XmlRootElement(name="CreateDBInstanceResponse")
public class CreateDBInstanceResponse extends PackageXml{
	public String ConnectionString;
	public String DBInstanceId;
	public String port;
	public String RequestId;
}
