package com.Dispatcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateSql {
		public Map<String,String> SysDb(String DBInstanceId){//返回数据库ID：NAME键值对
			String sql="select * from sys_databases;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			Map<String,String> dbinfos=new HashMap<String,String>();
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();

				
				while(rs.next())//获取到数据库的ID和NAME
				{
					dbinfos.put(rs.getString("DB_ID"), rs.getString("DB_NAME"));
				}
			}catch (Exception e) {
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
				return dbinfos;
				
			}
		public Map<String,String> SysUsers(String DBInstanceId){//返回用户ID：NAME键值对
			String sql="select * from sys_users;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			Map<String,String> userinfos=new HashMap<String,String>();
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();

				
				while(rs.next())//获取到数据库的ID和NAME
				{
					userinfos.put(rs.getString("USER_ID"), rs.getString("USER_NAME"));
				}
			}catch (Exception e) {
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
				return userinfos;
				
			}
		public void InsertUser(String AccountName,String DBInstanceId,String UserStatus,String AccountDescription,String AccountPassword){
			String sql="INSERT INTO USERS VALUES("
					+"'"+AccountName+"'"+",'"+DBInstanceId+"','"+UserStatus+"','"+AccountDescription+"','"+AccountPassword+"');";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
			}catch (Exception e) {
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
		public String SelectUserId(String UserName,String DBInstanceId){
			String sql="select * from sys_users where USER_NAME='"+UserName+"';";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			String userid = null;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.next()){
					
					userid=rs.getString("USER_ID");
					
				}
			}catch (Exception e) {
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
			
			
			return userid;
			
			
			
		}
		public String SelectUserName(String UserId,String DBInstanceId){
			String sql="select * from sys_users where USER_ID='"+UserId+"';";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			String username = null;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.next()){
					
					username=rs.getString("USER_NAME");
					
				}
			}catch (Exception e) {
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
			
			
			return username;
			
			
			
		}
		public String SelectDbId(String DbName,String DBInstanceId){//根据数据库名称返回数据库ID
			String sql="select * from sys_databases where DB_NAME='"+DbName+"';";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			String dbid = null;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.next()){
					
					dbid=rs.getString("USER_ID");
					
				}
			}catch (Exception e) {
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
			
			
			return dbid;
			
			
			
		}
		public String SelectUserPrivilege(String DBName,String UserName,String DBInstanceId){//根据数据库ID、用户ID来获取用户权限
			String UserPrivilege=null;
			//DB_ID | GRANTOR_ID | GRANTEE_ID | OBJECT_ID | OBJECT_TYPE | AUTHORITY  | REGRANT | ORG_GRANTOR_ID | IS_SYS
			String sql="select USER_NAME,AUTHORITY from sys_users left join sys_acls on sys_acls.GRANTEE_ID=sys_users.USER_ID left join sys_databases on sys_users.DB_ID=sys_databases.DB_ID where sys_users.USER_NAME='"+UserName.toUpperCase()+"' and sys_databases.DB_NAME='"+DBName.toUpperCase()+"';";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			String authority = null;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.next()){
					
					authority=rs.getString("AUTHORITY");
					if (authority.equals("8388607")){
						UserPrivilege="ReadWrite";
					}
					else{
						UserPrivilege="ReadOnly";
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println(sql);
			} finally {
				try {
					if(rs!=null)rs.close();
					if(stm!=null) stm.close();
					if(conn!=null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return UserPrivilege;
		}
		public Map<String,String> DbPrivileges(String UserId,String DBInstanceId){//根据用户ID来返回数据库：权限键值对
			Map<String,String> UserPs=new HashMap<String,String>();
			String sql="select DB_ID,AUTHORITY from sys_acls where GRANTEE_ID='"+UserId+"';";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			String authority = null;
			String UserPrivilege=null;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.next()){
					authority=rs.getString("AUTHORITY");
					if (authority.equals("8388607")){
						UserPrivilege="ReadWrite";
					}
					else{
						UserPrivilege="ReadOnly";
					}
					UserPs.put(rs.getString("DB_ID"), UserPrivilege);
				}
				
			}catch (Exception e) {
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
			
			
			
			return UserPs;
		}
		public Map<String,String> UserPrivileges(String DBName,String DBInstanceId){//根据数据库ID来返回用户ID：权限键值对
			Map<String,String> UserPs=new HashMap<String,String>();
			String sql="select USER_NAME,AUTHORITY from sys_users left join sys_acls on sys_acls.GRANTEE_ID=sys_users.USER_ID left join sys_databases on sys_users.DB_ID=sys_databases.DB_ID where sys_databases.DB_NAME='"+DBName+"';";
			Connection conn = null;
			PreparedStatement stm = null; 
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			String authority = null;
			String UserPrivilege=null;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				while(rs.next()){
					authority=rs.getString("AUTHORITY");
					if (authority.equals("8388607")){
						UserPrivilege="ReadWrite";
					}
					else{
						UserPrivilege="ReadOnly";
					}
					UserPs.put(rs.getString("GRANTEE_ID"), UserPrivilege);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("zaizheer"+sql+UserPrivilege);
			} finally {
				try {
					if(rs!=null)rs.close();
					if(stm!=null) stm.close();
					if(conn!=null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			
			return UserPs;
		}
		public void SynDbUsers(String DBName,String DBInstanceId){
			String sql="select * from USERS;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			String[] sqlusers = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			Map<String,String> users=new HashMap<String,String>();
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				while(rs.next()){
					users.put(rs.getString("USER_NAME"), rs.getString("USER_PASSWORD"));
				}
				if(rs!=null)rs.close();
				if(stm!=null) stm.close();
				if(conn!=null) conn.close();
				sqlusers=new String[users.size()];
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				int i=0;
				for(Map.Entry<String, String> createusers:users.entrySet()){
					sqlusers[i]="USE "+DBName+";CREATE USER "+createusers.getKey()+" IDENTIFIED BY '"+ createusers.getValue()+"';";
					stm = conn.prepareStatement(sqlusers[i]);
					rs = stm.executeQuery();
					i++;
					
					
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println(sqlusers[0]);
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

		
		
}

