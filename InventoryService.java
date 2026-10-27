package com.college.pos.service;

import com.college.pos.dao.ProductDAO;
import com.college.pos.model.Product;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;

public class InventoryService {
    private final ProductDAO productDAO = new ProductDAO();
    public List<Product> listProducts() throws SQLException { return productDAO.findAll(); }
    public List<Product> searchProducts(String query) throws SQLException {
        if (query == null || query.isBlank()) return listProducts();
        return productDAO.search(query);
    }
    public void addProduct(String code, String name, String priceText, String stockText) throws SQLException {
        try {
            BigDecimal price = new BigDecimal(priceText.trim());
            int stock = Integer.parseInt(stockText.trim());
            productDAO.insert(new Product(0, code, name, price, stock));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enter a valid price and whole-number stock quantity.", ex);
        }
    }
}
