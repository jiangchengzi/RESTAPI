package com.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateSql;
import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.Dispatcher.connsql;
import com.database.DatabasePrivilege;
import com.kunlun.jdbc.*;
public class DescribeAccounts {
	public DescribeAccountResponse response(String DBInstanceId,String AccountName){
		DescribeAccountResponse planet=new DescribeAccountResponse();
		List<DBInstanceAccount> dbinstanceaccounts=new ArrayList<DBInstanceAccount>();
		OperateSql Opt=new OperateSql();
		Map<String,String> dbinfos=Opt.SysDb(DBInstanceId);	
		String locked="F";
		String expired="F";
		for (Map.Entry<String, String> entry :dbinfos.entrySet())
		{			 
				String sql = null;
				sql="select  DB_ID,DB_NAME,USER_ID,USER_NAME,AUTHORITY,LOCKED,EXPIRED from sys_users left join sys_acls on sys_users.USER_ID=sys_acls.GRANTEE_ID and sys_users.DB_ID=sys_acls.DB_ID left join all_databases on sys_users.DB_ID=all_databases.DB_ID where USER_ID>100  order by sys_users.USER_NAME;";
				Connection conn = null;
				PreparedStatement stm = null;
				ResultSet rs = null;
				OperateXml opt=new OperateXml();
				Map<String,String> c=opt.SelectOpt(DBInstanceId);
				List<DBInstanceAccount> accounts=new ArrayList<DBInstanceAccount>();
				try{
					Class.forName(c.get("DBInstanceDRV"));
					conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
					stm = conn.prepareStatement(sql);
					rs = stm.executeQuery();
					while(rs.next()){
						DBInstanceAccount dbinstanceaccount=new DBInstanceAccount();
						dbinstanceaccount.DatabasePrivilege=new ArrayList<DatabasePrivilege>();
						DatabasePrivilege databaseprivilege=new DatabasePrivilege();
						dbinstanceaccount.AccountName=rs.getString("USER_NAME");
						dbinstanceaccount.DBInstanceId=DBInstanceId;
						dbinstanceaccount.AccountDescription=" ";
						locked=rs.getString("LOCKED");
						expired=rs.getString("EXPIRED");
						if(locked.equals("F")&&expired.equals("F")){
							dbinstanceaccount.AccountStatus="Available";
						}
						else{ dbinstanceaccount.AccountStatus="Unavailable";}
						if(rs.getString("AUTHORITY")!=null&&rs.getString("AUTHORITY").equals("8388607"))
						{
							databaseprivilege.AccountPrivilege="ReadWrite";
						}
						else{
							databaseprivilege.AccountPrivilege="ReadOnly";
							
						}
						databaseprivilege.DBName=rs.getString("DB_NAME");
						dbinstanceaccount.DatabasePrivilege.add(databaseprivilege);
						accounts.add(dbinstanceaccount);						
						
					}
					for (int i=0;i<accounts.size()-1;i++){
						if(accounts.get(i).AccountName.equals(accounts.get(i+1).AccountName)){
							accounts.get(i).DatabasePrivilege.add(accounts.get(i+1).DatabasePrivilege.get(0));
							accounts.remove(i+1);
							i=i-1;
							//i++;
						}
						//else{
						//	planet.dbinstanceaccount.add(accounts.get(i));
						//}
						
					}	
					planet.dbinstanceaccount=accounts;
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(accounts.get(0));
				} finally {
					try {
						if(rs!=null)rs.close();
						if(stm!=null) stm.close();
						if(conn!=null) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
		}
			planet.RequestId="2603CA96-B17D-4903-BC04-61A2C829CD94";
		
		
		
		return planet;
		
		
	}
}

@XmlRootElement(name="DescribeAccountsResponse")
class DescribeAccountResponse extends PackageXml{
	public String RequestId;
	@XmlElementWrapper(name="Accounts")
	@XmlElement(name="DBInstanceAccount")
	public List<DBInstanceAccount> dbinstanceaccount;
	
}

@XmlRootElement(name="DBInstanceAccount")
class DBInstanceAccount {
	
	public String AccountName;
	public String DBInstanceId;
	public String AccountStatus;
	public String AccountDescription;
	@XmlElementWrapper(name="DatabasePrivileges")
	public List<DatabasePrivilege> DatabasePrivilege;
	
}
