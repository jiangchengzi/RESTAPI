package com.Dispatcher;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class ReadOnlyDBInstanceIds {
	//@XmlElement(name="ReadOnlyDBInstanceIds")
	public String ReadOnlyDBInstanceId;
	
	public void setReadOnlyDBInstanceIds(String ReadOnlyDBInstanceId)
	{
		this.ReadOnlyDBInstanceId=ReadOnlyDBInstanceId;
	}
	
	

}
