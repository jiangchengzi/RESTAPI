package com.Dispatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OperateScripts {
	public String CheckDB(){
		String cmdstring="chmod 777 /root/check.sh "; 
		String linestr;
		String result=new String();
		try {
		
		Process proc;
		proc=Runtime.getRuntime().exec(cmdstring);
		proc.waitFor();
		cmdstring="bash /root/check.sh";//检查实例进程是否启动，如果启动了，返回“up”,如果没有启动，返回“down”
		proc=Runtime.getRuntime().exec(cmdstring);
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		while((linestr=bufferedReader.readLine())!=null)//获得shell脚本执行的结果
		{
			result=linestr;
		
		}
		bufferedReader.close();
		proc.waitFor();
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
		
		
	}
	
	public String  StartDB(){
		String cmdstring="chmod 777 /root/start.sh "; 
		String linestr;
		String result=new String();
		try {
		
		Process proc;
		proc=Runtime.getRuntime().exec(cmdstring);
		proc.waitFor();
		cmdstring="bash /root/start.sh";//检查实例进程是否启动，如果没启动则启动之，如果启动了，返回“up”
		proc=Runtime.getRuntime().exec(cmdstring);
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		while((linestr=bufferedReader.readLine())!=null)//获得shell脚本执行的结果
		{
			result=linestr;
		
		}
		bufferedReader.close();
		proc.waitFor();
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
		
		
		
	}
}
