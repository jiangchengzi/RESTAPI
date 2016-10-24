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
import com.database.DatabasePrivilege;
public class ResetAccountPassword {
   public ResetAccountPasswordResponse response(String DBInstanceId,String AccountName,String AccountPassword){
	   ResetAccountPasswordResponse planet=new ResetAccountPasswordResponse();
	   OperateSql finddb=new OperateSql();
		Map<String,String> dbinfos=finddb.SysDb(DBInstanceId);
		for (Map.Entry<String, String> entry :dbinfos.entrySet())
		{
		String sql="USE "+entry.getValue()+";alter USER "+AccountName+" IDENTIFIED BY '"+ AccountPassword+"';";
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

@XmlRootElement(name="ResetAccountPasswordResponse")
class ResetAccountPasswordResponse extends PackageXml{

	public String RequestId;

	
}
