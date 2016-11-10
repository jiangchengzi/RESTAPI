package com.DBInstance;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import com.Dispatcher.OperateScripts;
import com.Dispatcher.OperateXml;

public class CreateDBInstance {	
	private String EngineVersion;
	private String ZoneId;
	private String DBInstanceClass;
	private String DBInstanceNetType;
	private String PayType;
	private String Timestamp;
	private String DBInstanceStorage;
	private String RegionId;
	private String Engine;
	private String DBInstanceDescription;
	public String getEngineVersion(){
		return this.EngineVersion;
	}
	public void setEngineVersion(String EngineVersion){	
		this.EngineVersion=EngineVersion;
	}
	public String getZoneId(){
		return this.ZoneId;
	}
	public void setZoneId(String ZoneId){	
		this.ZoneId=ZoneId;
	}
	public String getDBInstanceClass(){
		return this.DBInstanceClass;
	}
	public void setDBInstanceClass(String DBInstanceClass){	
		this.DBInstanceClass=DBInstanceClass;
	}
	public String getDBInstanceNetType(){
		return this.DBInstanceNetType;
	}
	public void setDBInstanceNetType(String DBInstanceNetType){	
		this.DBInstanceNetType=DBInstanceNetType;
	}
	public String getPayType(){
		return this.PayType;
	}
	public void setPayType(String PayType){	
		this.PayType=PayType;
	}
	public String getTimestamp(){
		return this.Timestamp;
	}
	public void setTimestamp(String Timestamp){	
		this.Timestamp=Timestamp;
	}
	public String getDBInstanceStorage(){
		return this.DBInstanceStorage;
	}
	public void setDBInstanceStorage(String DBInstanceStorage){	
		this.DBInstanceStorage=DBInstanceStorage;
	}
	public String getRegionId(){
		return this.RegionId;
	}
	public void setRegionId(String RegionId){	
		this.RegionId=RegionId;
	}
	public String getEngine(){
		return this.Engine;
	}
	public void setEngine(String Engine){	
		this.Engine=Engine;
	}
	public String getDBInstanceDescription(){
		return this.DBInstanceDescription;
	}
	public void setDBInstanceDescription(String DBInstanceDescription){	
		this.DBInstanceDescription=DBInstanceDescription;
	}
	public CreateDBInstanceResponse response(){
		CreateDBInstanceResponse planet=new CreateDBInstanceResponse();
		OperateXml opt=new OperateXml();
		Map<String,String> c=opt.SelectUnusedOpt(Engine);//搜索到可用实例节点
		OperateScripts o=new OperateScripts();
		String result=o.CheckDB();
		String sql;
		if(result.equals("up")&&c!=null){//如果启动了，则创建用户表，并且添加实例到DBInstanceInfoxml中
			sql = 	"CREATE TABLE USERS (USER_NAME CHAR(100),"
					+ "DBINSTANCE_ID CHAR(100),"
					+ "USER_STATUS CHAR(100),"
					+ "USER_DESCRIBETION CHAR(100),"
					+ "USER_PASSWORD CHAR(100));"
					+ "CREATE TABLE DBINSTANCE ("
					+ "DBINSTANCEID CHAR(100),"
					+ "PAYTYPE CHAR(100),"
					+ "DBINSTANCETYPE CHAR(100),"
					+ "REGIONID CHAR(100),"
					+ "ZONEID CHAR(100),"
					+ "ENGINE CHAR(100),"
					+ "ENGINEVERSION CHAR(100),"
					+ "DBINSTANCECLASS CHAR(100),"
					+ "DBINSTANCESTORAGE CHAR(100),"
					+ "DBINSTANCEDESCRIPTION CHAR(100),"
					+ "CREATIONTIME CHAR(100)"
					+ ");"
					+"INSERT INTO DBINSTANCE VALUES"
					+ " ('"
					+ DBInstanceDescription+"','"+PayType+"','"+DBInstanceNetType+"','"+RegionId+"','"+ZoneId+"','"+Engine+"','"+EngineVersion
					+"','"+DBInstanceClass+"','"+DBInstanceStorage+"','"+DBInstanceDescription+"','"+Timestamp
					+ "');";//创建用户表,用于同步用户信息到各个数据库中，
			                //解决昆仑数据库中无该创建实例全局可见用户的问题
							// 这里不增加用户拥有某一数据库权限信息，是避免与数据库操作耦合
		    Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			
			try {
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				opt.AlterOpt(DBInstanceDescription, DBInstanceDescription, null, "unavailable", null, null, null, null, null, null);
				planet.ConnectionString=c.get("DBInstanceIP");
				planet.DBInstanceId=DBInstanceDescription;
				planet.port=c.get("DBInstancePORT");
				planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				planet=null;
			}finally {
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



