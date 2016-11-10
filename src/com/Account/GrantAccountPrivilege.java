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


public class GrantAccountPrivilege {
	public GrantAccountPrivilegeResponse response(String DBInstanceId,String AccountName,String DBName,String AccountPrivilege){
		GrantAccountPrivilegeResponse planet=new GrantAccountPrivilegeResponse();
		String sql = null;
		OperateSql operateSql=new OperateSql();
		String accountprivilege=operateSql.SelectUserPrivilege(DBName, AccountName, DBInstanceId);
		
		if(AccountPrivilege.equals("ReadOnly")){
			if(accountprivilege!=null&&accountprivilege.equals("ReadWrite")){
				sql="USE "+DBName+";revoke DBA FROM "+AccountName+";GRANT SELECT ANY TABLE TO "+AccountName+";";
			}
			else{
				sql="USE "+DBName+";GRANT SELECT ANY TABLE TO "+AccountName+";";
				
			}
		}
		else if(AccountPrivilege.equals("ReadWrite"))
		{
			if(accountprivilege!=null&&accountprivilege.equals("ReadOnly")){
				sql="USE "+DBName+";revoke SELECT ANY TABLE FROM "+AccountName+";GRANT DBA TO "+AccountName+";";
			}
			else{
				sql="USE "+DBName+";"+"GRANT DBA TO "+AccountName+";";
			}
		}
		
		
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
					System.out.println(accountprivilege+sql);
				} finally {
					try {
						if(rs!=null)rs.close();
						if(stm!=null) stm.close();
						if(conn!=null) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
		
		
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		return planet;
		
		
	}
}

@XmlRootElement(name="GrantAccountPrivilegeResponse")
class GrantAccountPrivilegeResponse extends PackageXml{

	public String RequestId;

}
