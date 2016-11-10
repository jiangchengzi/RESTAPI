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
import com.database.DataBase;
public class DescribeDBInstances {
	public DescribeDBInstancesResponse response(){//在这里api实际上是想返回所有实例的信息，因此最好是先搜寻xml文件中有那些实例，再根据实例的IP和端口号依次访问，并返回信息
		DescribeDBInstancesResponse planet=new DescribeDBInstancesResponse();
		OperateXml opt=new OperateXml();
		List<String> DBInstances=opt.GetAllDbinstances();//从xml中获取所有实例的名称
		List<DBInstance> dbinstances=new ArrayList<DBInstance>(); 
		for(int i=0;i<DBInstances.size();i++){
		Map<String,String> c=opt.SelectOpt(DBInstances.get(i));//数据库url、用户名以及密码设置
		OperateScripts o=new OperateScripts();
		String result=o.CheckDB();//需要改成远程的shell执行
		String status;
		if(result.equals("up")){
			status="Running";
			String sql="use system;select * from dbinstance;";
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			
			try { 
				Class.forName(c.get("DBInstanceDRV"));
				conn = DriverManager.getConnection(c.get("DBInstanceURL"), c.get("DBInstanceUSER"), c.get("DBInstancePWD"));
				stm = conn.prepareStatement(sql);
				rs = stm.executeQuery();
				if(rs.next()){
				
				DBInstance dbinstance=new DBInstance();
				ReadOnlyDBInstanceIds ReadOnlyDBInstanceId=new ReadOnlyDBInstanceIds();
				dbinstance.DBInstanceDescription=rs.getString("DBInstanceDescription").trim();
				dbinstance.ExpireTime="2024-10-10T16:00:00Z";
				dbinstance.DBInstanceId=rs.getString("DBInstanceId").trim();
				dbinstance.DBInstanceNetType=rs.getString("DBInstanceType").trim();
				dbinstance.PayType=rs.getString("PayType").trim();
				dbinstance.DBInstanceStatus=status;
				dbinstance.DBInstanceType=rs.getString("DBInstanceType").trim();
				dbinstance.Engine=rs.getString("Engine").trim();
				dbinstance.LockMode="Unlock";
				dbinstance.LockReason=null;
				dbinstance.RegionId=rs.getString("RegionId").trim();
				dbinstance.ZoneId=rs.getString("ZoneId").trim();
				dbinstance.MasterInstanceId=rs.getString("DBInstanceId").trim();
				ReadOnlyDBInstanceId.setReadOnlyDBInstanceIds(" ");
				dbinstance.ReadOnlyDBInstanceId=ReadOnlyDBInstanceId;
				dbinstances.add(dbinstance);
				
				
				} 
		}catch (Exception e) {
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
		}
		planet.setResponse(dbinstances);
		planet.PageNumber=1;
		planet.TotalRecordCount=DBInstances.size();
		planet.PageRecordCount=DBInstances.size();
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		return planet;
	}
}
@XmlRootElement(name="DescribeDBInstancesResponse")
class DescribeDBInstancesResponse{
	public int PageRecordCount;
	
	@XmlElementWrapper(name="Items")
	@XmlElement(name="DBInstance")
	public List<DBInstance> dbinstance;
	
	public List<DataBase> Databases;
	public int PageNumber;
	public int  TotalRecordCount;
	public String RequestId;
	
	
	public void setResponse(List<DBInstance> dbinstance)
	{
		this.dbinstance=dbinstance;
	}
}

@XmlRootElement
class DBInstance extends PackageXml{
	public String DBInstanceDescription;
	public String ExpireTime;
	public String DBInstanceId;
	public String DBInstanceNetType;
	public String Engine;
	public String LockMode;
	public String LockReason;;
	public String RegionId;
	public String ZoneId;
	public String PayType;
	public String DBInstanceStatus;
	public String DBInstanceType;
	public String MasterInstanceId;
	public String GuardDBInstanceId;
	public String TempDBInstanceId;
	@XmlElement(name="ReadOnlyDBInstanceIds")
	public ReadOnlyDBInstanceIds ReadOnlyDBInstanceId;
	
}



