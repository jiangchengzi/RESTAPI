package com.database;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class DataBase {
	@XmlElement
	public String Engine;
	public String DBName;
	public String CharacterSetName;
	public String DBStatus;
	public String DBDescription;
	public String DBInstanceId;
	
	@XmlElement(name="Accounts")
	public List<AccountPrivilegeInfo> AccountPrivilegeInfo;
	
}

@XmlRootElement
class AccountPrivilegeInfo{
	public String Account;
	public String AccountPrivilege;
}