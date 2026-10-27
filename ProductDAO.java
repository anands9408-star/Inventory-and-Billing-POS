package com.college.pos.dao;

import com.college.pos.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT product_id, product_code, name, unit_price, stock_qty FROM products ORDER BY name";
        List<Product> products = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) products.add(map(rs));
        }
        return products;
    }

    public List<Product> search(String query) throws SQLException {
        String sql = "SELECT product_id, product_code, name, unit_price, stock_qty FROM products " +
                     "WHERE LOWER(name) LIKE LOWER(?) OR product_code = ? ORDER BY name";
        List<Product> products = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + query.trim() + "%");
            ps.setString(2, query.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) products.add(map(rs));
            }
        }
        return products;
    }

    public void insert(Product product) throws SQLException {
        String sql = "INSERT INTO products(product_code, name, unit_price, stock_qty) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getProductCode());
            ps.setString(2, product.getName());
            ps.setBigDecimal(3, product.getUnitPrice());
            ps.setInt(4, product.getStockQty());
            ps.executeUpdate();
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("product_id"), rs.getString("product_code"),
            rs.getString("name"), rs.getBigDecimal("unit_price"), rs.getInt("stock_qty"));
    }
}
