package dao;

import vo.City;
import vo.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProvinceCityDao {
    private Connection con;
    private PreparedStatement pst;

    public ProvinceCityDao(Connection con) {
        this.con = con;
    }

    public ArrayList<Province> queryProvince(){
        ArrayList<Province> list=new ArrayList<>();
        String sql="select * from t_province";
        try {
            pst=con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Province province=new Province();
                province.setProvinceCode(rs.getInt("provinceCode"));
                province.setProvinceName(rs.getString("provinceName"));
                list.add(province);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<City> queryCity(String provinceName){
        ArrayList<City> list=new ArrayList<>();
        String sql="select * from t_city where provinceName=?";
        try {
            pst=con.prepareStatement(sql);
            pst.setString(1,provinceName);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                City city=new City();
                city.setCityCode(rs.getInt("cityCode"));
                city.setCityName(rs.getString("cityName"));
                city.setProvinceName(rs.getString("provinceName"));
                list.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String queryProvinceName(int provinceCode){
        String provinceName="";
        String sql="select provinceName from t_province where provinceCode=?";
        try {
            pst=con.prepareStatement(sql);
            pst.setInt(1,provinceCode);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                provinceName=rs.getString("provinceName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinceName;
    }
}
