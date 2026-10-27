package com.college.pos.model;

import java.math.BigDecimal;

public class Product {
    private final int productId;
    private final String productCode;
    private final String name;
    private final BigDecimal unitPrice;
    private final int stockQty;

    public Product(int productId, String productCode, String name, BigDecimal unitPrice, int stockQty) {
        if (productCode == null || productCode.isBlank()) throw new IllegalArgumentException("Product code is required.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name is required.");
        if (unitPrice == null || unitPrice.signum() < 0) throw new IllegalArgumentException("Price cannot be negative.");
        if (stockQty < 0) throw new IllegalArgumentException("Stock cannot be negative.");
        this.productId = productId;
        this.productCode = productCode.trim();
        this.name = name.trim();
        this.unitPrice = unitPrice;
        this.stockQty = stockQty;
    }
    public int getProductId() { return productId; }
    public String getProductCode() { return productCode; }
    public String getName() { return name; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getStockQty() { return stockQty; }
}
