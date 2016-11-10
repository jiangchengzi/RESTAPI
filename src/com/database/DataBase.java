package com.database;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.PackageXml;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class DataBase extends PackageXml {
	@XmlElement
	public String Engine;
	public String DBName;
	public String CharacterSetName;
	public String DBStatus;
	public String DBDescription;
	public String DBInstanceId;
	
	@XmlElementWrapper(name="Accounts")
	@XmlElement(name="AccountPrivilegeInfo")
	public List<AccountPrivilegeInfo> AccountPrivilegeInfo;
	
}

@XmlRootElement
class AccountPrivilegeInfo{
	public String Account;
	public String AccountPrivilege;
}