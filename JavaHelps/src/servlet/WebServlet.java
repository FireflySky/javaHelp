package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");//同意跨域请求
		 resp.setContentType("application/json;charset=utf-8");
		 resp.setCharacterEncoding("UTF-8");
		 PrintWriter out =resp.getWriter();
		 StringBuilder strb=new StringBuilder();
		 strb.append("mybatis1,mybatis2,spring1,spring2,springMvc1,springMvc-HelloWorld,ssm1,ssm2,router,axios,sqlyouhua,ms1,ms2,ms3*")
		 .append("Mybatis基础,常用标签及sql动态语句,初识Spring和IOC用法,SpringAop和代理,SpringMVC基础,SpringMvc框架输出Hello World实例,集成ssm步骤一,集成ssm步骤二,")
		 .append("vue路由,vue集成axios,数据库优化,框架常见面试题1,框架常见面试题2,框架常见面试题3");//
		 
		 
		 try {
			 out.write(strb.toString());//返回数据
			 System.out.println(strb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 out.close();//关闭
	}

}
