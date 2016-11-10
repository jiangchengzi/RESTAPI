package com.Account;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateSql;
import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.Dispatcher.error;

public class CreateAccount {
	private int httpstatus;
	public void setHttpStatus(int httpstatus){
		this.httpstatus=httpstatus;
	}
	public int getHttpStatus(){
		return this.httpstatus;
	}
	public PackageXml response(String DBInstanceId,String AccountName,String AccountPassword,String AccountDescription){
		CreateAccountResponse planet=new CreateAccountResponse();
		OperateSql finddb=new OperateSql();
		String sql = null;
		Map<String,String> dbinfos=finddb.SysDb(DBInstanceId);
		for (Map.Entry<String, String> entry :dbinfos.entrySet())
		{
			sql="USE "+entry.getValue()+";CREATE USER \""+AccountName+"\" IDENTIFIED BY '"+ AccountPassword+"';";
				Connection conn = null;
				PreparedStatement stm = null;
				ResultSet rs = null;
				//connsql c=new connsql();//数据库url、用户名以及密码设置
				OperateXml opt=new OperateXml();
				Map<String,String> c=opt.SelectOpt(DBInstanceId);
				try{
				
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
				finddb.InsertUser(AccountName, DBInstanceId, "Available", AccountDescription, AccountPassword);
				this.setHttpStatus(200);;
				
				} catch (Exception e) {
					e.printStackTrace();
					this.setHttpStatus(403);
					error planet_error=new error();
					planet_error.setCode("OperationDenied");
					planet_error.setMessage("The?resource?is?out?of?usage.");
					planet_error.setRequestId("8906582E-6722-409A-A6C4-0E7863B733A5");
					try {
						planet_error.setHostId(InetAddress.getLocalHost().getHostAddress());
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
					return planet_error;
							
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
		return planet;
	}

}

