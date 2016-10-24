package com.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.Dispatcher.connsql;
public class DeleteDatabase {
	public DeleteDatabaseResponse response(String DBInstanceId,String DBName)
	{
		 String sql = "drop database "+DBName+";";
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
					} finally {
						try {
							if(rs!=null)rs.close();
							if(stm!=null) stm.close();
							if(conn!=null) conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
		
		DeleteDatabaseResponse planet=new DeleteDatabaseResponse();
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		return planet;
		
		
	}
}

@XmlRootElement(name="DeleteDatabaseResponse")
class DeleteDatabaseResponse extends PackageXml{
	public String RequestId;
	
}
