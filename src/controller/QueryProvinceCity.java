package controller;

import com.google.gson.Gson;
import dao.ProvinceCityDao;
import dbc.DataBaseConnection;
import vo.City;
import vo.Province;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/queryProvinceCity.do")
public class QueryProvinceCity extends HttpServlet {
    private static DataBaseConnection dbc;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String provinceCode=request.getParameter("provinceCode");
        System.out.println("provinceCode:"+provinceCode);
        String jsonStr="";
        dbc=new DataBaseConnection();
        ProvinceCityDao dao=new ProvinceCityDao(dbc.getConnection());
        if(provinceCode==null){//没有请求参数provinceCode,表示查询所有省份列表
            ArrayList<Province> list=dao.queryProvince();
            jsonStr=new Gson().toJson(list);
        }else{//按provinceCode查询对应的城市的列表
            String provinceName=dao.queryProvinceName(Integer.parseInt(provinceCode));
            ArrayList<City> list=dao.queryCity(provinceName);
            jsonStr=new Gson().toJson(list);
        }
        //字符流输出字符串
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        System.out.println(jsonStr);
        out.print(jsonStr);
        out.flush();
        out.close();
        dbc.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
