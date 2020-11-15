package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dbc.DataBaseConnection;
import vo.User;
import dao.UserDao;

@WebServlet(urlPatterns = "/checkExist.do")
public class CheckExist extends HttpServlet {
	private static DataBaseConnection dbc;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String mail = request.getParameter("mail");
		dbc=new DataBaseConnection();
		UserDao dao=new UserDao(dbc.getConnection());
		User user = null;
		if(userName!=null){  //查询用户名是否存在
			user = dao.getByField("userName", userName);
		}
		if(mail!=null){ //查询邮箱是否存在
			user = dao.getByField("mail", mail);
		}

		HashMap<String,Object> map=new HashMap<String,Object>();
		if(user == null){
			map.put("code", 0);
			map.put("info", "没有重复");
		}
		else{
			map.put("code", 1);
			map.put("info", "输入重复");
		}

		String jsonStr = new Gson().toJson(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();
		dbc.close();
	}

}
