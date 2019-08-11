package servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JavaServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");//同意跨域请求
		 resp.setContentType("application/json;charset=utf-8");
		 resp.setCharacterEncoding("UTF-8");
		 PrintWriter out =resp.getWriter();
		 StringBuilder strb=new StringBuilder();
		 strb.append("txt,txt2,txt3-1,txt3-2,ms_1,ms_2,ms_3,day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,day13_1,day13_2,day13_3,day13_4,day14,porjet1_1,porjet1_2*")
		 .append("Eclipse菜单介绍,IDEA快捷键,类加载机制1,类加载机制2,面试题1,面试题2,面试题3,修饰符及基本数据类型,类型转换及运算符,选择结构,循环结构,数组,排序,常用类(String),类和对象,封装及方法重载,")
		 .append("继承,静态、final、设计模式,抽象类和接口,集合框架1,集合框架2,集合框架3,集合方法,IO流,食品管理系统(后端),食品管理系统(前端)");//
		 
		 try {
			 out.write(strb.toString());//返回数据
			 System.out.println(strb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 out.close();//关闭
	}

}
