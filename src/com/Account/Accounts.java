package com.Account;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.PackageXml;

@XmlRootElement
public class Accounts  extends PackageXml{
	public String Account;
	@XmlElement
	public String AccountPrivilegeInfo;
	
	public void setAccounts(String accountPrivilegeInfo){
		this.AccountPrivilegeInfo=accountPrivilegeInfo;
		
	}
}
