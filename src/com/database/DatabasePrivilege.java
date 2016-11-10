package com.database;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.PackageXml;

@XmlRootElement(name="DatabasePrivilege")
public class DatabasePrivilege{
	public String DBName;
	public String AccountPrivilege;
}