package edu.entity;

import edu.beans.Product;
import edu.db.ConnectionDB;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ProductEntity {
    public List<Product> getAll() {
        Statement s = null;
        try {
            s = ConnectionDB.connect();
            List<Product> re= new LinkedList<>();
            ResultSet rs = s.executeQuery("select * from products");
            while(rs.next()) {
                re.add(new Product(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
            rs.close();
            s.close();
            return re;

        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }

    }

    public int insertAll(Collection<Product> data) {
        Statement s = null;
        try {
            s = ConnectionDB.connect();
            String sql = "insert into products (id,name,img,price,pricesale) values ";
            int i = 0;
            for (Product d : data) {
                if( ++i < data.size())
                    sql += "(" +d.getId()+ ",\"" +d.getName()+ "\",\"" +d.getImg()+ "\",\"" +d.getPrice()+ "\",\"" +d.getPriceSale()+ "\"),";
                else
                    sql += "(" +d.getId()+ ",\"" +d.getName()+ "\",\"" +d.getImg()+ "\",\"" +d.getPrice()+ "\",\"" +d.getPriceSale()+ "\")";
            }
//            System.out.println(sql);
            s.executeUpdate(sql);
            s.close();
            return 0;

        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int count(String txtSearch) throws SQLException, ClassNotFoundException {
        Statement s = null;
        s = ConnectionDB.connect();

        String query = "select count(*) from products where name like "+"'%"+txtSearch+"%'";

        ResultSet rs = s.executeQuery(query);
        while(rs.next()) {
            return rs.getInt(1);
        }

        rs.close();
        s.close();
        return 0;

    }
    public List<Product> getResult(String txtSearch) throws SQLException, ClassNotFoundException {
        Statement s = null;
        s = ConnectionDB.connect();
        List<Product> re= new LinkedList<>();
        ResultSet rs = s.executeQuery("select * from products where name like "+"'%"+txtSearch+"%'");
        while(rs.next()) {
            re.add(new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
                ));
            }
            rs.close();
            s.close();
            return re;
    }

}
