package com.Dispatcher;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import com.database.*;
//import com.database.response.CreateDatabaseResponse;

import com.DBInstance.*;
import com.Account.*;
@Path("/")
public class Sqlapi{
	@QueryParam("DBInstanceId") 
	String DBInstanceId;
	
	@QueryParam("DBName")
	String DBName;
	
	@QueryParam("CharacterSetName")
	String CharacterSetName;
	
	@QueryParam("Action") 
	String action;
	
	@QueryParam("DBDescription")
	String DBDescription;
	
	@QueryParam("DBStatus")
	String DBStatus;
	
	@QueryParam("AccountName")
	String AccountName;
	
	@QueryParam("AccountPassword")
	String AccountPassword;
	
	@QueryParam("AccountDescription")
	String AccountDescription;
	
	@QueryParam("AccountPrivilege")
	String AccountPrivilege;
	
	@QueryParam("EngineVersion")//
	String EngineVersion;
	
	@QueryParam("ZoneId")
	String ZoneId;
	
	@QueryParam("DBInstanceClass")
	String DBInstanceClass;
	
	@QueryParam("DBInstanceNetType")
	String DBInstanceNetType;
	
	@QueryParam("PayType")
	String PayType;
	
	@QueryParam("Timestamp")
	String Timestamp;
	
	@QueryParam("DBInstanceStorage")
	String DBInstanceStorage;
	
	@QueryParam("RegionId")
	String RegionId;
	
	@QueryParam("Engine")
	String Engine;
	
	@QueryParam("DBInstanceDescription")
	String DBInstanceDescription;
	
	@GET()//
	@Produces(MediaType.APPLICATION_XML)
    public  PackageXml  dbxml() {
		switch(action){
		case "DescribeDatabases":{DescribeDatabases d=new DescribeDatabases();return d.response(DBInstanceId,DBName,DBStatus);}
		case "CreateDatabase":   {CreateDatabase d=new CreateDatabase();return d.response(DBInstanceId,DBName,CharacterSetName,DBDescription);}
		case "DeleteDatabase":   {DeleteDatabase d=new DeleteDatabase();return d.response(DBInstanceId,DBName);}
		case "CreateDBInstance": 
			{CreateDBInstance d=new CreateDBInstance();
				return d.response(EngineVersion, ZoneId, DBInstanceClass, DBInstanceNetType, PayType, Timestamp, DBInstanceStorage, RegionId, Engine, DBInstanceDescription);}
		case "DeleteDBInstance": {DeleteDBInstance d=new DeleteDBInstance();return d.response(DBInstanceId);} 
		case "DescribeDBInstances":{DescribeDBInstances d=new DescribeDBInstances();return d.response();}
		case "DescribeDBInstanceAttribute":
		{
			
			DescribeDBInstanceAttribute d=new DescribeDBInstanceAttribute();
			return d.response(DBInstanceId);
			
		}
		case "RestartDBInstance": {RestartDBInstance d=new RestartDBInstance();return d.response();}
		case "CreateAccount":{CreateAccount d=new CreateAccount();return d.response(DBInstanceId,AccountName,AccountPassword,AccountDescription);}
		case "DeleteAccount":{DeleteAccount d=new DeleteAccount();return d.response(DBInstanceId,AccountName);}
		case "DescribeAccounts":{DescribeAccounts d=new DescribeAccounts();return d.response(DBInstanceId,AccountName);}
		case "GrantAccountPrivilege":{GrantAccountPrivilege d=new GrantAccountPrivilege();return d.response(DBInstanceId,AccountName,DBName,AccountPrivilege);}
		case "ResetAccountPassword":{ResetAccountPassword d=new ResetAccountPassword();return d.response(DBInstanceId,AccountName,AccountPassword);}
		case "RevokeAccountPrivilege":{revokeAccountPrivilege d=new revokeAccountPrivilege();return d.response(DBInstanceId,AccountName,DBName);}
		default :
			{
				
				error d=new error();
				d.Code="UnsupportedOperation";
				d.Message="The?specified?action?is?not?supported.";
				d.RequestId="8906582E-6722-409A-A6C4-0E7863B733A5";
				try {
					d.HostId=InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return d;
			}
	}
    }
}
