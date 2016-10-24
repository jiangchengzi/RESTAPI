package com.database;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DatabasePrivilege")
public class DatabasePrivilege {
	public String DBName;
	public String AccountPrivilege;
}