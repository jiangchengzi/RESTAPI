package com.DBInstance;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.Dispatcher.OperateScripts;
import com.Dispatcher.PackageXml;
import com.Dispatcher.connsql;
import com.Dispatcher.error;
public class RestartDBInstance {
	public PackageXml response(){
		
		connsql c=new connsql();//数据库url、用户名以及密码设置
		OperateScripts check=new OperateScripts();
		String result=check.CheckDB();
		if(result.equals("up"))//如果启动了，则重启
		{
							String sql = "shutdown;";
						    Connection conn = null;
							PreparedStatement stm = null;
							ResultSet rs = null;
							
							try{
								Class.forName(c.drv);
								conn = DriverManager.getConnection(c.url, c.user, c.pwd);
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
		OperateScripts start=new OperateScripts();	
		start.StartDB();
		RestartDBInstanceResponse planet=new RestartDBInstanceResponse();
		planet.RequestId="1E43AAE0-BEE8-43DA-860D-EAF2AA0724DC";
		return  planet;

}
}
@XmlRootElement(name="RestartDBInstanceResponse")
class RestartDBInstanceResponse extends PackageXml{
	public String RequestId;
}