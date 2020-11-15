package controller;

import com.google.gson.Gson;
import dao.UserDao;
import dbc.DataBaseConnection;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/Register.do")
public class AjaxRegister extends HttpServlet {
    private static DataBaseConnection dbc;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        dbc=new DataBaseConnection();
        UserDao userDao = new UserDao(dbc.getConnection());
        User user = new User(request.getParameter("userName"),request.getParameter("password"), request.getParameter("chrName"),
                request.getParameter("email"), request.getParameter("province"), request.getParameter("city"));
        try {
            userDao.insert(user);
            System.out.println("插入成功");
            map.put("code",0);
            String jsonStr = new Gson().toJson(map);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonStr);
            out.flush();
            out.close();
            dbc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
