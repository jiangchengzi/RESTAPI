package com.Account;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Accounts {
	public String Account;
	@XmlElement
	public String AccountPrivilegeInfo;
	
	public void setAccounts(String accountPrivilegeInfo){
		this.AccountPrivilegeInfo=accountPrivilegeInfo;
		
	}
}
