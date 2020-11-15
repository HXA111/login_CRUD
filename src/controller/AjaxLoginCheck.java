package controller;

import com.google.gson.Gson;
import dao.UserDao;
import dbc.DataBaseConnection;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/ajaxLoginCheck.do")
public class AjaxLoginCheck extends HttpServlet {
    private static DataBaseConnection dbc;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求参数编码格式为utf-8,防止中文参数乱码
        request.setCharacterEncoding("utf-8");
        //1.按照表单的各元素的name属性获取各请求参数值
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String vcode=request.getParameter("vcode");
        String checkBox=request.getParameter("checkBox");
        //2.获取HttpSession对象
        HttpSession session=request.getSession();
        //取出CreateVerifyImageController中存放的验证码字符串
        String saveVcode=(String) session.getAttribute("verifyCode");
        System.out.println(saveVcode);
        //存放返回信息的Map
        Map<String,Object> map=new HashMap<>();
        //比较输入的验证码和随机生成的验证码是否相同
        if(!vcode.equalsIgnoreCase(saveVcode)){//不同
            System.out.println("111");
            //在map中存放返回数据
            map.put("code",1);
            map.put("info","验证码不正确");
        }else{//验证码正确
            System.out.println("222");
            dbc=new DataBaseConnection();
            UserDao userDao=new UserDao(dbc.getConnection());
            User user=userDao.get(userName);
            if(user==null){//用户名不存在
                map.put("code",2);
                map.put("info","用户名不存在");
            }else {//用户名存在
                if(!user.getPassword().equals(password)){//密码不正确
                    map.put("code",3);
                    map.put("info","密码不正确");
                }else{//用户名密码正确
                    //将需要传递的数据存放在session域范围中，一个会话阶段的所有程序都可以从中获取
                    session.setAttribute("currentUser",user);
                    if(checkBox!=null){//免登陆选中
                        //设置cookie
                        Cookie cookie1=new Cookie("userName",userName);
                        cookie1.setMaxAge(60 * 60 * 24 * 7);//设置有效期为7天
                        response.addCookie(cookie1);
                        Cookie cookie2=new Cookie("password",password);
                        cookie2.setMaxAge(60 * 60 * 24 * 7);
                        response.addCookie(cookie2);
                    }
                    map.put("code",0);
                    map.put("info","登录成功");
                }
            }
        }
        //调用谷歌的Gson库将map类型数据转换为json字符串
        String jsonStr=new Gson().toJson(map);
        System.out.println(jsonStr);
        //字符流输出字符串
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
        dbc.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
