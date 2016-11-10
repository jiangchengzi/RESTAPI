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
		public List<String> NotSysDb(String DBInstanceId){//返回数据库ID：NAME键值对
			String sql="select * from sys_databases where DB_ID>1;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			List<String> dbtables=new ArrayList<String>();
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				while(rs.next())//获取到数据库的ID和NAME
				{
					dbtables.add(rs.getString("DB_NAME"));
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
				return dbtables;
				
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
		public List<String> NotSysUsers(String DBInstanceId){//返回非系统用户
			String sql="select * from sys_users where USER_ID>100;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			List<String> username=new ArrayList<String>();
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();

				
				while(rs.next())//获取到数据库的ID和NAME
				{
					username.add(rs.getString("USER_NAME"));
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
		public void InsertUser(String AccountName,String DBInstanceId,String UserStatus,String AccountDescription,String AccountPassword){
			String sql="INSERT INTO USERS VALUES('"
					+AccountName+"','"+DBInstanceId+"','"+UserStatus+"','"+AccountDescription+"','"+AccountPassword+"');";
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
		public void ResetUser(String AccountName,String DBInstanceId,String AccountPassword){
			String sql="UPDATE USERS SET USER_PASSWORD='"+AccountPassword+"' WHERE USER_NAME='"+AccountName+"';";
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
		public void DropUser(String AccountName,String DBInstanceId){
			String sql="DELETE FROM USERS WHERE USER_NAME='"+AccountName+"';";
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
		
		
		
		public String SelectUserPrivilege(String DBName,String UserName,String DBInstanceId){//根据数据库ID、用户ID来获取用户权限
			String UserPrivilege=null;
			//DB_ID | GRANTOR_ID | GRANTEE_ID | OBJECT_ID | OBJECT_TYPE | AUTHORITY  | REGRANT | ORG_GRANTOR_ID | IS_SYS
			String sql="select USER_NAME,AUTHORITY from all_databases join sys_users on all_databases.DB_ID=sys_users.DB_ID and sys_users.USER_ID>100 and all_databases.DB_NAME='"+DBName+"' and sys_users.USER_NAME='"+UserName+"' join sys_acls on sys_users.USER_ID=sys_acls.GRANTEE_ID and sys_acls.DB_ID=all_databases.DB_ID order by sys_acls.AUTHORITY desc;";
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
					
					if (authority!=null&&authority.equals("8388607")){
						UserPrivilege="ReadWrite";
					}
					else if (authority!=null&&authority.equals("1")){
						UserPrivilege="ReadOnly";
					}
					else{
						UserPrivilege=null;
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				//System.out.println(sql);
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
					sqlusers[i]="USE "+DBName+";CREATE USER \""+createusers.getKey()+"\" IDENTIFIED BY '"+ createusers.getValue().trim()+"';";
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
		public void DropDB(List<String> DBName,String DBInstanceId){
			for(int i=0;i<DBName.size();i++)
			{
			String sql="drop database "+DBName.get(i)+";";
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
		}
		public void DropAllUsers(List<String> AllUsers,String DBInstanceId){
			for(int i=0;i<AllUsers.size();i++)
			{
			String sql="DROP USER "+AllUsers.get(i)+";";
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
		
		}
		public List<String> SysTables(String DBInstanceId){//返回系统库中创建的表
			String sql="select * from all_tables";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			List<String> tablename=new ArrayList<String>();
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();

				
				while(rs.next())//获取到数据库的ID和NAME
				{
					tablename.add(rs.getString("TABLE_NAME"));
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
				return tablename;
				
			}
		public void DropSysTables(List<String> SysTables,String DBInstanceId){
			for(int i=0;i<SysTables.size();i++)
			{
			String sql="drop table "+SysTables.get(i)+";";
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
		}
}

