package dao;

import vo.Page;
import vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private Connection con;
    private PreparedStatement pst;

    public UserDao (Connection con){
        this.con=con;
    }

    public User get(String userName){
        User user=null;
        String sql="select * from t_user where userName=?";
        try {
            pst=con.prepareStatement(sql);
            pst.setString(1,userName);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                user=new User();
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setChrName(rs.getString("chrName"));
                user.setEmail(rs.getString("email"));
                user.setProvince(rs.getString("province"));
                user.setCity(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getByEmail(String email){
        User user=null;
        String sql="select * from t_user where email=?";
        try {
            pst=con.prepareStatement(sql);
            pst.setString(1,email);
            ResultSet rs=pst.executeQuery();
            if(rs.next()) {
                user = new User();
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setChrName(rs.getString("chrName"));
                user.setEmail(rs.getString("email"));
                user.setProvince(rs.getString("province"));
                user.setCity(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insert(User user){
        String sql = "insert into t_user(userName,password,chrName,email,province,city) values(?,?,?,?,?,?) ";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getChrName());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getProvince());
            pst.setString(6,user.getCity());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User getByField(String field, String param) {
        User user = null;
        try {
            // 3.创建语句
            String sql = "select * from t_user where " + field + "=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, param);
            // 4.执行语句
            ResultSet rs = pst.executeQuery();
            // 5.结果处理
            if (rs.next()) {
                user = new User(rs.getString("userName"),
                        rs.getString("password"), rs.getString("chrName"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public boolean delete(String ids) {
        boolean success = false;
        String[] array = ids.split(",");
        try {

            // 3.创建语句
            String sql = "delete from t_user where userName in(";
            StringBuffer sb = new StringBuffer("?");
            for (int i = 0; i < array.length - 1; i++) {
                sb.append(",?");
            }
            sql = sql + sb.toString() + ")";
            System.out.println(sql);
            PreparedStatement pst = con.prepareStatement(sql);
            for (int i = 0; i < array.length; i++) {
                pst.setString(i + 1, array[i]);
            }

            // 4.执行语句
            int i = pst.executeUpdate();
            // 5.结果处理
            if (i > 0) {
                success = true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;

    }

    public ArrayList<User> query(User user, Page page) {
        ArrayList<User> list = new ArrayList<User>(); // 存放查询结果的集合
        StringBuffer condition = new StringBuffer();// 查询条件
        if (user.getUserName() != null && !"".equals(user.getUserName())) { // 判断是否有该查询条件
            condition.append(" and userName like '%")
                    .append(user.getUserName()).append("%'");
        }
        if (user.getChrName() != null && !"".equals(user.getChrName())) { //
            condition.append(" and chrName like '%").append(user.getChrName())
                    .append("%'");
        }
        if (user.getMail() != null && !"".equals(user.getMail())) { //
            condition.append(" and mail like '%").append(user.getMail())
                    .append("%'");
        }
        if (user.getProvinceName() != null
                && !"".equals(user.getProvinceName())) { //
            condition.append(" and provinceName like '%")
                    .append(user.getProvinceName()).append("%'");
        }
        if (user.getCityName() != null && !"".equals(user.getCityName())) { //
            condition.append(" and cityName like '%")
                    .append(user.getCityName()).append("%'");
        }

        int begin = page.getPageSize() * (page.getPageNumber() - 1);
        String sql = "select userName,password,chrName,mail,A.provinceCode provinceCode,";
        sql = sql
                + " B.provinceName provinceName,A.cityCode cityCode,C.cityName cityName ";
        sql = sql + " from t_user A left join t_province B ";
        sql = sql
                + " on A.provinceCode = B.provinceCode left join t_city C on A.cityCode = C.cityCode ";
        sql = sql + " where  1=1 ";
        sql = sql + condition + " order by " + page.getSort() + " "
                + page.getSortOrder() + " limit " + begin + ","
                + page.getPageSize();

        System.out.println(sql);
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                User userResult = new User();
                userResult.setUserName(rs.getString("userName"));
                userResult.setPassword(rs.getString("password"));
                userResult.setChrName(rs.getString("chrName"));
                userResult.setMail(rs.getString("mail"));
                userResult.setProvinceCode(rs.getString("provinceCode"));
                userResult.setProvinceName(rs.getString("provinceName"));
                userResult.setCityCode(rs.getString("cityCode"));
                userResult.setCityName(rs.getString("cityName"));
                list.add(userResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int count(User user, Page page) {
        int total = 0;

        StringBuffer condition = new StringBuffer();// 查询条件
        if (user.getUserName() != null && !"".equals(user.getUserName())) { //
            condition.append(" and userName like '%")
                    .append(user.getUserName()).append("%'");
        }
        if (user.getChrName() != null && !"".equals(user.getChrName())) { //
            condition.append(" and chrName like '%").append(user.getChrName())
                    .append("%'");
        }
        if (user.getMail() != null && !"".equals(user.getMail())) { //
            condition.append(" and mail like '%").append(user.getMail())
                    .append("%'");
        }
        if (user.getProvinceName() != null
                && !"".equals(user.getProvinceName())) { //
            condition.append(" and provinceName like '%")
                    .append(user.getProvinceName()).append("%'");
        }
        if (user.getCityName() != null && !"".equals(user.getCityName())) { //
            condition.append(" and cityName like '%")
                    .append(user.getCityName()).append("%'");
        }

        String sql = "select count(*) from t_user A left join t_province B";
        sql = sql
                + " on A.provinceCode = B.provinceCode left join t_city C on A.cityCode = C.cityCode ";
        sql = sql + " where  1=1 ";
        sql = sql + condition;
        System.out.println(sql);
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total;
    }
}
