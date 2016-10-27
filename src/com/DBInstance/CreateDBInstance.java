package com.DBInstance;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import com.Dispatcher.OperateScripts;
import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.Dispatcher.error;
public class CreateDBInstance {		
	public PackageXml response(String EngineVersion,String ZoneId,String DBInstanceClass,String DBInstanceNetType,String PayType,String Timestamp,String DBInstanceStorage, String RegionId,String Engine,String DBInstanceDescription){
		CreateDBInstanceResponse planet=new CreateDBInstanceResponse();
		OperateXml opt=new OperateXml();
		Map<String,String> c=opt.SelectOpt(DBInstanceDescription);
		OperateScripts o=new OperateScripts();
		String result=o.CheckDB();
		if(result.equals("up")){//如果启动了，则创建用户表，并且添加实例到DBInstanceInfoxml中

			String sql=null;
			sql = "CREATE TABLE USERS (USER_NAME CHAR(100),"
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
					+ "INSERT INTO DBINSTANCE VALUES"
					+ " ('"
					+ c.get("DBInstanceID")+"','"+PayType+"','"+DBInstanceNetType+"','"+RegionId+"','"+ZoneId+"','"+Engine+"','"+EngineVersion
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			//OperateXml i=new OperateXml();
			//i.InsertOpt(c.get("DbInstanceId"), c.name, c.IP, c.port);//首次创建时由api来自主决定实例名称和IP，之后扩展的时候，将会建立一个配置文件，把能够配置
															//数据库服务器的服务器IP，端口号都写进去，使用时随机选择或者按照负载均衡规则选择，并将返回的数据放到DBInstanceInfoxml中
			planet.ConnectionString=c.get("DBInstanceIP");
			planet.DBInstanceId=c.get("DBInstanceID");
			planet.port=c.get("DBInstancePORT");
			planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
			return  planet;
	}
}

@XmlRootElement(name="CreateDBInstanceResponse")
class CreateDBInstanceResponse extends PackageXml{
	public String ConnectionString;
	public String DBInstanceId;
	public String port;
	public String RequestId;
}

