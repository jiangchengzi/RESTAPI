package com.Account;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.PackageXml;

@XmlRootElement(name="CreateAccountResponse")
public class CreateAccountResponse extends PackageXml{
	public String RequestId;
}