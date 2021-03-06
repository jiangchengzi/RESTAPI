package com.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;


	//import java.sql.Timestamp;

	/**
	 * 通过jdbc执行SQL实例
	 * 	功能：取得数据库描述信息
	 */

public class DescribeDatabases {
			public DescribeDatabaseResponse response(String DBInstanceId,String DBName,String DBStatus){
					String sql = null;
					String online=null;
					String droped=null;
					String dbstatus = null;
					if(DBStatus!=null){
						switch(DBStatus){
						case "Running":{online="T";droped="F";break;}
						case "Deleting":{online="F";droped="T";break;}
						case "Creating":{online="F";droped="F";break;}
						default:{online="T";droped="F";break;}
						}
						if(DBName==null){
							sql= "select DB_ID,CHAR_SET,ONLINE,DROPED,COMMENTS,DB_NAME,USER_NAME,sys_users.USER_ID,AUTHORITY "
									+ "from all_databases left join sys_users on all_databases.DB_ID=sys_users.DB_ID "
									+ "left join sys_acls on all_databases.DB_ID=sys_acls.DB_ID and  sys_users.USER_ID=sys_acls.GRANTEE_ID "
									+ "where sys_users.USER_ID>100 and all_databases.online='"+online+"' and all_databases.droped='"+droped+"';";
						}
						else{
							sql= "select DB_ID,CHAR_SET,ONLINE,DROPED,COMMENTS,DB_NAME,USER_NAME,sys_users.USER_ID,AUTHORITY "
									+ "from all_databases left join sys_users on all_databases.DB_ID=sys_users.DB_ID "
									+ "left join sys_acls on all_databases.DB_ID=sys_acls.DB_ID and  sys_users.USER_ID=sys_acls.GRANTEE_ID "
									+ "where sys_users.USER_ID>100 and all_databases.online='"+online+"' and all_databases.droped='"+droped+"'"+""
											+ " and  all_databases.DB_NAME='"+ DBName+"';";
						}
					}
					else{
						if(DBName!=null){
							sql = "select DB_ID,CHAR_SET,ONLINE,DROPED,COMMENTS,DB_NAME,USER_NAME,sys_users.USER_ID,AUTHORITY from all_databases left join sys_users on all_databases.DB_ID=sys_users.DB_ID left join sys_acls on all_databases.DB_ID=sys_acls.DB_ID and  sys_users.USER_ID=sys_acls.GRANTEE_ID where all_databases.DB_NAME='"+ DBName+"' order by all_databases.DB_NAME;";
						}
						else{
							sql = "select * from (select DB_ID,CHAR_SET,ONLINE,DROPED,COMMENTS,DB_NAME,USER_NAME,sys_users.USER_ID,AUTHORITY from all_databases left join sys_users on all_databases.DB_ID=sys_users.DB_ID and sys_users.USER_ID>100 and all_databases.DB_ID>10 left join sys_acls on sys_users.DB_ID=sys_acls.DB_ID and  sys_users.USER_ID=sys_acls.GRANTEE_ID order by all_databases.DB_NAME) as a where a.DB_NAME!='SYSTEM';";
						}
						}
					
					OperateXml OptXml=new OperateXml();
					Map<String,String> c=OptXml.SelectOpt(DBInstanceId);
					DescribeDatabaseResponse planet=new DescribeDatabaseResponse();
					List<DataBase> databases=new ArrayList<DataBase>();
					Connection conn = null;
					PreparedStatement stm = null;
					ResultSet rs = null; 
					try{
						
						Class.forName(c.get("DBInstanceDRV"));
						conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
						stm = conn.prepareStatement(sql);
						rs = stm.executeQuery();
						while(rs.next()){
							DataBase database=new DataBase();
							List<AccountPrivilegeInfo> accountprivilegeinfos=new ArrayList<AccountPrivilegeInfo>();
							AccountPrivilegeInfo accountprivilegeinfo=new AccountPrivilegeInfo();
							database.DBName=rs.getString("db_name").toLowerCase();
							database.CharacterSetName=rs.getString("char_set");
							database.DBDescription=rs.getString("comments");
							database.Engine=c.get("DBInstanceENGINE");
							if(rs.getString("ONLINE").equals("T")&&(rs.getString("DROPED").equals("F"))){dbstatus="Running";}
							if(rs.getString("ONLINE").equals("F")&&(rs.getString("DROPED").equals("T"))){dbstatus="Deleting";}
							if(rs.getString("ONLINE").equals("F")&&(rs.getString("DROPED").equals("F"))){dbstatus="Creating";}
							database.DBStatus=dbstatus;
							database.DBInstanceId=DBInstanceId;
							accountprivilegeinfo.Account=rs.getString("USER_NAME");
							if(rs.getString("AUTHORITY")!=null&&rs.getString("AUTHORITY").equals("8388607"))
							{
								accountprivilegeinfo.AccountPrivilege="ReadWrite";
							}
							else if(rs.getString("AUTHORITY")!=null&&rs.getString("AUTHORITY").equals("1"))
							{
								accountprivilegeinfo.AccountPrivilege="ReadOnly";
								
							}
							else{
								accountprivilegeinfo.AccountPrivilege=null;
							}
								accountprivilegeinfos.add(accountprivilegeinfo);
							database.AccountPrivilegeInfo=accountprivilegeinfos;
							databases.add(database);
						
						}
						/*
						for (int i=0;i<databases.size()-1;i++){
							if(databases.get(i).DBName.equals(databases.get(i+1).DBName)){
								databases.get(i).AccountPrivilegeInfo.add(databases.get(i+1).AccountPrivilegeInfo.get(0));
								databases.remove(i+1);
								i=i-1;
							}
						}
						*/	
						for (int i=0;i<databases.size()-1;i++){//一个dbinstanceaccount可以拥有多个databaseprivilege，为了实现这种设计
							if(!databases.get(i).AccountPrivilegeInfo.isEmpty()){
								if(databases.get(i).AccountPrivilegeInfo.get(0).AccountPrivilege==null){//把没有AccountPrivilege的删除
									databases.get(i).AccountPrivilegeInfo.remove(0);
								}
							}
							if(databases.get(i).DBName.equals(databases.get(i+1).DBName)){
								if(databases.get(i+1).AccountPrivilegeInfo.get(0).AccountPrivilege!=null){
									databases.get(i).AccountPrivilegeInfo.add(databases.get(i+1).AccountPrivilegeInfo.get(0));//只有在相邻数据库名相同，且AccountPrivilege不为空时才去重
								  for(int k=0;k<databases.get(i).AccountPrivilegeInfo.size()-1;k++){
									  for(int j=k+1;j<databases.get(i).AccountPrivilegeInfo.size();j++)
										  if(databases.get(i).AccountPrivilegeInfo.get(k).Account.equals(databases.get(i).AccountPrivilegeInfo.get(j).Account)){
											  databases.get(i).AccountPrivilegeInfo.remove(j);
										  }
								  		}
								}
								databases.remove(i+1);
								i=i-1;
								continue;
								}
							}
						
						
						planet.database=databases;
						planet.setDescribeDatabaseResponse("2603CA96-B17D-4903-BC04-61A2C829CD94", databases);
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
					
					
				
				
				return planet;

}

}

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DescribeDatabasesResponse")
class DescribeDatabaseResponse extends PackageXml{
	@XmlElement
	public String RequestId;
	
	@XmlElementWrapper(name="Databases")
	@XmlElement(name="Database")
	public List<DataBase> database;
	
	public void setDescribeDatabaseResponse(String RequestId,List<DataBase> database){
		//super();
		this.database=database;
		this.RequestId=RequestId;
	}
}




