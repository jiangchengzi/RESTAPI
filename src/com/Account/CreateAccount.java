package com.Account;
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
import com.Dispatcher.connsql;;
public class CreateAccount {
	public CreateAccountResponse response(String DBInstanceId,String AccountName,String AccountPassword,String AccountDescription){
		CreateAccountResponse planet=new CreateAccountResponse();
		OperateSql finddb=new OperateSql();
		Map<String,String> dbinfos=finddb.SysDb(DBInstanceId);
		finddb.InsertUser(AccountName, DBInstanceId, "Available", AccountDescription, AccountPassword);
		for (Map.Entry<String, String> entry :dbinfos.entrySet())
		{
		String sql="USE "+entry.getValue()+";CREATE USER "+AccountName+" IDENTIFIED BY '"+ AccountPassword+"';";
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
		
		
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		
		return planet;
		
	}

}

@XmlRootElement(name="CreateAccountResponse")
class CreateAccountResponse extends PackageXml{
	public String RequestId;
}
