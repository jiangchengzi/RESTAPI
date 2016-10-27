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
public class revokeAccountPrivilege {
	public revokeAccountPrivilegeResponse response(String DBInstanceId,String AccountName,String DBName){
				revokeAccountPrivilegeResponse planet=new revokeAccountPrivilegeResponse();
				Connection conn = null;
				PreparedStatement stm = null;
				ResultSet rs = null;
				OperateXml opt=new OperateXml();
				Map<String,String> c=opt.SelectOpt(DBInstanceId);
				OperateSql operateSql=new OperateSql();
				String AccountPrivilege=operateSql.SelectUserPrivilege(DBName, AccountName, DBInstanceId);
				String sql;
				if(AccountPrivilege.equals("ReadOnly")){
					
					sql="use "+DBName+";revoke SELECT ANY TABLE FROM "+AccountName;
				}
				else{
					sql="use "+DBName+";revoke DBA FROM "+AccountName;
				}
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
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		return planet;
		
		
	}

}
@XmlRootElement(name="RevokeAccountPrivilegeResponse")
class revokeAccountPrivilegeResponse extends PackageXml{

	public String RequestId;

	
}

