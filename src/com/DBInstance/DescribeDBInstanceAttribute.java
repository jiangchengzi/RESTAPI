package com.DBInstance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateScripts;
import com.Dispatcher.OperateXml;
import com.Dispatcher.PackageXml;
import com.Dispatcher.ReadOnlyDBInstanceIds;
public class DescribeDBInstanceAttribute {
	public DescribeDBInstanceAttributeResponse response(String DBInstanceId){
		DescribeDBInstanceAttributeResponse planet=new DescribeDBInstanceAttributeResponse();
		OperateXml opt=new OperateXml();
		Map<String,String> c=opt.SelectOpt(DBInstanceId);//数据库url、用户名以及密码设置
		OperateScripts o=new OperateScripts();
		String result=o.CheckDB();
		if(result.equals("up")){
			String sql="select * from DBINSTANCE;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			
			try {
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.last()){
				List<DBInstanceAttribute> dbinstances=new ArrayList<DBInstanceAttribute>(); 
				DBInstanceAttribute dbinstance=new DBInstanceAttribute();
				ReadOnlyDBInstanceIds ReadOnlyDBInstanceId=new ReadOnlyDBInstanceIds();
				ReadOnlyDBInstanceId.setReadOnlyDBInstanceIds(" ");
				planet.RequestId="3C5CFDEE-F774-4DED-89A2-1D76EC63C575".trim();
				dbinstance.EngineVersion=rs.getString("EngineVersion".toUpperCase()).trim();
				dbinstance.ZoneId=rs.getString("ZoneId".toUpperCase()).trim();
				dbinstance.DBInstanceClass=rs.getString("DBInstanceClass".toUpperCase()).trim();
				dbinstance.DBInstanceNetType=rs.getString("DBInstanceType".toUpperCase()).trim();
				dbinstance.PayType=rs.getString("PayType".toUpperCase()).trim();
				dbinstance.CreationTime=rs.getString("CreationTime".toUpperCase()).trim();
				dbinstance.SecurityIPList="0.0.0.0%2F0".trim();
				dbinstance.DBInstanceStorage=Integer.valueOf(rs.getString("DBInstanceStorage".toUpperCase()).trim()).intValue();
				dbinstance.Engine=rs.getString("Engine").trim();
				dbinstance.DBInstanceDescription=rs.getString("DBInstanceDescription".toUpperCase()).trim();
				dbinstance.DBInstanceId=rs.getString("DBInstanceId").trim();
				dbinstance.RegionId=rs.getString("RegionId".toUpperCase()).trim();
				dbinstance.DBInstanceType="Primary";
				dbinstance.DBInstanceStatus="Running";
				dbinstance.MaxConnections=1000;
				dbinstance.ConnectionString=c.get("DBInstanceIP");
				dbinstance.Port=Integer.valueOf(c.get("DBInstancePORT").trim()).intValue();;
				dbinstances.add(dbinstance);
				planet.setResponse(dbinstances);
				
				}
				
				
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
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724D1"+result;
		return planet;

	}
}
@XmlRootElement(name="DescribeDBInstanceAttributeResponse")
class DescribeDBInstanceAttributeResponse{
			public String RequestId;
			
			@XmlElementWrapper(name="Items")
			@XmlElement(name="DBInstanceAttribute")
			public List<DBInstanceAttribute> dbinstanceattribute;
			
			public void setResponse(List<DBInstanceAttribute> dbinstance)
			{
				this.dbinstanceattribute=dbinstance;
			}
	
}
@XmlRootElement
class DBInstanceAttribute extends PackageXml{
	public String LockMode;
	public String ConnectionString;
	public String CreationTime;
	public String DBInstanceNetType;
	public int  MaxConnections;
	public String LockReason;
	public String Engine;
	public String  AvailabilityValue;
	public int AccountMaxQuantity;
	public int  DBMaxQuantity;
	public String RegionId;
	public String ZoneId;
	
	public DBInstanceAttribute(){}
	
	@XmlElement(name="ReadOnlyDBInstanceIds")
	public ReadOnlyDBInstanceIds ReadOnlyDBInstanceId;
	
	public String TempDBInstanceId;
	public long DBInstanceMemory;
	public int MaxIOPS;
	public String DBInstanceType;
	public String DBInstanceStatus;
	public String MasterInstanceId;
	public String EngineVersion;
	public String IncrementSourceDBInstanceId;
	public String GuardDBInstanceId;
	public int DBInstanceStorage;
	public String DBInstanceDescription;
	public String DBInstanceId;
	public String PayType;
	public String ExpireTime;
	public String MaintainTime;
	public String DBInstanceClass;
	public String SecurityIPList;
	public int Port;
	
	
	
}
