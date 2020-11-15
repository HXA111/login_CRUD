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

@WebServlet(urlPatterns = "/RegisterCheck.do")
public class AjaxRegisterCheck extends HttpServlet {
    private static DataBaseConnection dbc;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        int flag=0;
        if (request.getParameter("userNameCheck")!=null)flag=1;
        System.out.println(request.getParameter("userNameCheck"));
        if (request.getParameter("emailCheck")!=null)flag=2;
        if (flag==1||flag==2){
            String userNameCheck = request.getParameter("userNameCheck");
            System.out.println("userNameCheck:"+userNameCheck);
            String emailCheck=request.getParameter("emailCheck");
            System.out.println("emailCheck:"+emailCheck);
            dbc=new DataBaseConnection();
            UserDao userDao = new UserDao(dbc.getConnection());
            User user1 = new User();
            switch (flag){
                case 1:user1.setUserName(userNameCheck);break;
                case 2:user1.setEmail(emailCheck);break;
            }
            try {
                if (userDao.get(user1.getUserName())==null&&userDao.getByEmail(user1.getEmail())==null){
                    map.put("code",0);
                }
                else{
                    map.put("code",1);
                }
                String jsonStr = new Gson().toJson(map);
                System.out.println(jsonStr);
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
