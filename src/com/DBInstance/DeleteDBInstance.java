package com.DBInstance;

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
public class DeleteDBInstance {
	public PackageXml response(String  DBInstanceId){
		OperateXml opt=new OperateXml();
		Map<String,String> c=opt.SelectOpt("kunlun");//数据库url、用户名以及密码设置,检查实例是否存在和开启，如果存在并开启则关闭删除实例，并更新xml
		if(c.get("DBInstanceId")!=null){
				OperateScripts checkdb=new OperateScripts(); 
				String status=checkdb.CheckDB();
				if(status.equals("up"))//启动的话则关闭实例，并且删除xml中的实例项
				{
				 String sql = "shutdown;";
				 	Connection conn = null;
					PreparedStatement stm = null;
					ResultSet rs = null;
					
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
				
		}
		OperateXml r=new OperateXml();
		r.RemoveOpt(DBInstanceId);
		DeleteDBInstanceResponse planet =new DeleteDBInstanceResponse();
		planet.RequestId="65BDA532-28AF-4122-AA39-B382721EEE64";
		return planet;
	}
}
@XmlRootElement(name="DeleteDBInstanceResponse")
class DeleteDBInstanceResponse extends PackageXml{
	public String RequestId;
}