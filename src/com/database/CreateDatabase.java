package com.database;


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

//import com.kunlun.jdbc.Driver;

	//import java.sql.Timestamp;

	/**
	 * 通过jdbc执行SQL实例
	 * 	功能：取得数据库版本信息
	 */

public class CreateDatabase{
		public CreateDatabaseResponse response(String DBInstanceId,String DBName,String CharacterSetName,String DBDescription) {
		    List<String> charsets=new ArrayList<>();
		    charsets.add("binary");
		    charsets.add("gbk");
		    charsets.add("gb18030");
		    charsets.add("utf8");
			if(!charsets.contains(CharacterSetName)){CharacterSetName="utf8";}
		    	
				
				String sql = "CREATE database \""+DBName+"\" CHARacter SET "+CharacterSetName+";";
				    Connection conn = null;
					PreparedStatement stm = null;
					ResultSet rs = null;
					OperateXml OptXml=new OperateXml();
					Map<String,String> c=OptXml.SelectOpt(DBInstanceId);
					OperateSql OptSql=new OperateSql();
					CreateDatabaseResponse planet =new CreateDatabaseResponse();
					try{
						
						Class.forName(c.get("DBInstanceDRV"));
						conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
						stm = conn.prepareStatement(sql);
					stm = conn.prepareStatement(sql);
					rs = stm.executeQuery();
					OptSql.SynDbUsers(DBName,DBInstanceId);
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

@XmlRootElement(name="CreateDatabaseResponse")
class CreateDatabaseResponse extends PackageXml{
	public String RequestId;
}

