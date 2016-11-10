package com.Dispatcher;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
	
	@QueryParam("Format")
	String Format;
	
	@GET()//
	@Produces(MediaType.APPLICATION_XML)
    public  Response  dbxml() {
		
		if(action.equals("DescribeDatabases")){
			DescribeDatabases d=new DescribeDatabases();
			return Response.status(200).entity(d.response(DBInstanceId,DBName,DBStatus)).build();
		}
		else if(action.equals("CreateDatabase")){
			CreateDatabase d=new CreateDatabase();
			return Response.status(200).entity(d.response(DBInstanceId,DBName,CharacterSetName,DBDescription)).build();
		}
		else if(action.equals("DeleteDatabase")){
			DeleteDatabase d=new DeleteDatabase();
			return Response.status(200).entity(d.response(DBInstanceId,DBName)).build();
		}
		else if(action.equals("CreateDBInstance")){
			CreateDBInstance d=new CreateDBInstance();
			d.setDBInstanceDescription(DBInstanceDescription);
			d.setDBInstanceClass(DBInstanceClass);
			d.setDBInstanceNetType(DBInstanceNetType);
			d.setDBInstanceStorage(DBInstanceStorage);
			d.setEngine(Engine);
			d.setEngineVersion(EngineVersion);
			d.setPayType(PayType);
			d.setRegionId(RegionId);
			d.setTimestamp(Timestamp);
			d.setZoneId(ZoneId);
			CreateDBInstanceResponse responsedata=d.response();
			if(responsedata!=null){
				return Response.status(200).entity(responsedata).build();
			}
			else{
				error e=new error();
				e.setCode("OperationDenied");
				e.setMessage("The?resource?is?out?of?usage.");
				e.setRequestId("8906582E-6722-409A-A6C4-0E7863B733A5");
				try {
					e.setHostId(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return Response.status(403).entity(e).build();
			}
		}
		else if(action.equals("DeleteDBInstance")){
			DeleteDBInstance d=new DeleteDBInstance();
			return Response.status(200).entity(d.response(DBInstanceId)).build();
		}
		else if(action.equals("DescribeDBInstances")){
			DescribeDBInstances d=new DescribeDBInstances();
			return Response.status(200).entity(d.response()).build();
		}
		else if(action.equals("DescribeDBInstanceAttribute")){
			DescribeDBInstanceAttribute d=new DescribeDBInstanceAttribute();
			return Response.status(200).entity(d.response(DBInstanceId)).build();
		}
		else if(action.equals("RestartDBInstance")){
			RestartDBInstance d=new RestartDBInstance();
			return Response.status(200).entity(d.response()).build();
		}
		else if(action.equals("CreateAccount")){
			CreateAccount d=new CreateAccount();
			PackageXml createAccountResponse=d.response(DBInstanceId,AccountName,AccountPassword,AccountDescription);
			
			return Response.status(d.getHttpStatus()).entity(createAccountResponse).build();
		}
		else if(action.equals("DeleteAccount")){
			DeleteAccount d=new DeleteAccount();
			return Response.status(200).entity(d.response(DBInstanceId,AccountName)).build();
		}
		else if(action.equals("DescribeAccounts")){
			DescribeAccounts d=new DescribeAccounts();
			return Response.status(200).entity(d.response(DBInstanceId,AccountName)).build();
		}
		else if(action.equals("GrantAccountPrivilege")){
			GrantAccountPrivilege d=new GrantAccountPrivilege();
			return Response.status(200).entity(d.response(DBInstanceId,AccountName,DBName,AccountPrivilege)).build();
			
		}
		else if(action.equals("ResetAccountPassword")){
			ResetAccountPassword d=new ResetAccountPassword();
			return Response.status(200).entity(d.response(DBInstanceId,AccountName,AccountPassword)).build();
			
		}
		else if(action.equals("RevokeAccountPrivilege")){
			revokeAccountPrivilege d=new revokeAccountPrivilege();
			return Response.status(200).entity(d.response(DBInstanceId,AccountName,DBName)).build();
			
		}
		else{
				
				error d=new error();
				d.setCode("UnsupportedOperation");
				d.setMessage("The?specified?action?is?not?supported.11");
				d.setRequestId("8906582E-6722-409A-A6C4-0E7863A743A5");
				try {
					d.setHostId(InetAddress.getLocalHost().getHostAddress()); 
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Response.status(404).entity(d).build();
		}
	
    }
}
