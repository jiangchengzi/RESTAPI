package com.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateSql;
import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.Dispatcher.connsql;
public class DeleteAccount {
	public DeleteAccountResponse response(String DBInstanceId,String AccountName){
		DeleteAccountResponse planet=new DeleteAccountResponse();
		OperateSql Opt=new OperateSql();
		Map<String,String> dbinfos=Opt.SysDb(DBInstanceId);	
		for (Map.Entry<String, String> entry :dbinfos.entrySet())
		{
			String sql = null;
			sql="USE "+entry.getValue()+";drop user "+AccountName+";";
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
				} catch (Exception e) {
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
		
		
		}
		
		
		
		
			/*
			String sql = null;
			sql="select * from all_databases;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			OperateXml opt=new OperateXml();
			Map<String,String> c=opt.SelectOpt(DBInstanceId);
			
			int i;
			try{
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				List<DbInfo> dbinfos=new ArrayList<DbInfo>();
				while(rs.next())//获取到数据库的ID和NAME
				{
					DbInfo dbinfo =new DbInfo();
					dbinfo.DB_ID=rs.getString("DB_ID");
					dbinfo.DB_NAME=rs.getString("DB_NAME");
					dbinfos.add(dbinfo);
				}
				if(rs!=null)rs.close();
				if(stm!=null) stm.close();
				if(conn!=null) conn.close();
				String[] sqlusers=new String[dbinfos.size()];
						Class.forName(c.get("DBInstanceDRV"));
						conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
					for(i=0;i<dbinfos.size();i++){//根据数据库NAME查询该数据库下用户名为AccountName的用户，并存成Arraylist
						sqlusers[i]="use "+dbinfos.get(i).DB_NAME+";"+"select * from all_users where USER_NAME="+"'"+AccountName+"';";
						stm = conn.prepareStatement(sqlusers[i]);
						rs = stm.executeQuery();
						sql=sqlusers[0]+dbinfos.size();
						if(rs.next()&&rs.getString("USER_NAME")!=null){
							
							
							sql="drop user "+AccountName+";";
							stm = conn.prepareStatement(sql);
							rs = stm.executeQuery();
							
						}
						
						
					}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							if(rs!=null)rs.close();
							if(stm!=null) stm.close();
							if(conn!=null) conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
					}
				*/
		
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		return planet;

	}
}
@XmlRootElement(name="DeleteAccountResponse")
class DeleteAccountResponse extends PackageXml{
	public String RequestId;
}
