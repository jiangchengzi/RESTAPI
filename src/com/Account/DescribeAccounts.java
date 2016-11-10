package com.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateSql;
import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.database.DatabasePrivilege;
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
				sql="select * from (select  sys_databases.DB_ID,sys_databases.DB_NAME,sys_users.USER_ID,sys_users.USER_NAME,sys_acls.AUTHORITY,LOCKED,EXPIRED from sys_databases  left join sys_users on sys_users.DB_ID=sys_databases.DB_ID and sys_users.USER_ID>100 left join sys_acls on sys_acls.GRANTEE_ID=sys_users.USER_ID  and sys_users.DB_ID=sys_acls.DB_ID  order by sys_users.USER_NAME,sys_acls.AUTHORITY desc) ;";
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
						if(locked!=null&&expired!=null&&locked.equals("F")&&expired.equals("F")){
							dbinstanceaccount.AccountStatus="Available";
						}
						else{ dbinstanceaccount.AccountStatus="Unavailable";}
						if(rs.getString("AUTHORITY")!=null&&rs.getString("AUTHORITY").equals("8388607"))
						{
							databaseprivilege.AccountPrivilege="ReadWrite";
						}
						if(rs.getString("AUTHORITY")!=null&&rs.getString("AUTHORITY").equals("1"))
						{
							databaseprivilege.AccountPrivilege="ReadOnly";
						}
						if(rs.getString("AUTHORITY")==null)
						{
							databaseprivilege.AccountPrivilege=null;
							
						}
						if(rs.getString("DB_NAME").equals("SYSTEM")){
							databaseprivilege.DBName=null;
						}
						if(!rs.getString("DB_NAME").equals("SYSTEM")){
							databaseprivilege.DBName=rs.getString("DB_NAME");
						}
							dbinstanceaccount.DatabasePrivilege.add(databaseprivilege);
						if(dbinstanceaccount.AccountName!=null){//查询语句以database为左表，则有可能存在用户为空的情况
							accounts.add(dbinstanceaccount);
						}
						
					}
					for (int i=0;i<accounts.size()-1;i++){//一个dbinstanceaccount可以拥有多个databaseprivilege，为了实现这种设计
						if(!accounts.get(i).DatabasePrivilege.isEmpty()){
							if(accounts.get(i).DatabasePrivilege.get(0).AccountPrivilege==null){//把没有AccountPrivilege的删除
								accounts.get(i).DatabasePrivilege.remove(0);
							}
						}
						if(accounts.get(i).AccountName.equals(accounts.get(i+1).AccountName)){
							if(accounts.get(i+1).DatabasePrivilege.get(0).AccountPrivilege!=null){
							accounts.get(i).DatabasePrivilege.add(accounts.get(i+1).DatabasePrivilege.get(0));//只有在相邻数据库名相同，且AccountPrivilege不为空时才去重
							  for(int k=0;k<accounts.get(i).DatabasePrivilege.size()-1;k++){
								  for(int j=k+1;j<accounts.get(i).DatabasePrivilege.size();j++)
									  if(accounts.get(i).DatabasePrivilege.get(k).DBName.equals(accounts.get(i).DatabasePrivilege.get(j).DBName)){
										  accounts.get(i).DatabasePrivilege.remove(j);
									  }
							  		}
							}
							accounts.remove(i+1);
							i=i-1;
							continue;
							}
						}
							
					planet.dbinstanceaccount=accounts;
					
				} catch (Exception e) {
					e.printStackTrace();
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
class DescribeAccountResponse{
	public String RequestId;
	@XmlElementWrapper(name="Accounts")
	@XmlElement(name="DBInstanceAccount")
	public List<DBInstanceAccount> dbinstanceaccount;
	
}

@XmlRootElement(name="DBInstanceAccount")
class DBInstanceAccount extends PackageXml{
	
	public String AccountName;
	public String DBInstanceId;
	public String AccountStatus;
	public String AccountDescription;
	@XmlElementWrapper(name="DatabasePrivileges")
	public List<DatabasePrivilege> DatabasePrivilege;
	
}
