package com.college.pos.ui;

import com.college.pos.model.Product;
import com.college.pos.service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private final InventoryService service = new InventoryService();
    private final DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID", "Code", "Product", "Unit Price (INR)", "Stock"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField searchField = new JTextField(18);

    public MainFrame() {
        super("Inventory and Billing POS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Search code/name:"));
        top.add(searchField);
        JButton search = new JButton("Search");
        JButton refresh = new JButton("Refresh");
        JButton add = new JButton("Add Product");
        top.add(search); top.add(refresh); top.add(add);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JLabel footer = new JLabel("Starter version: product management connected to MySQL.");
        footer.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        add(footer, BorderLayout.SOUTH);
        search.addActionListener(e -> loadProducts(() -> service.searchProducts(searchField.getText())));
        refresh.addActionListener(e -> { searchField.setText(""); refreshProducts(); });
        add.addActionListener(e -> showAddProductDialog());
        refreshProducts();
    }

    private interface ProductQuery { List<Product> run() throws SQLException; }
    private void loadProducts(ProductQuery query) {
        try {
            model.setRowCount(0);
            for (Product p : query.run())
                model.addRow(new Object[]{p.getProductId(), p.getProductCode(), p.getName(),
                    p.getUnitPrice().toPlainString(), p.getStockQty()});
        } catch (SQLException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operation failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void refreshProducts() { loadProducts(service::listProducts); }

    private void showAddProductDialog() {
        JTextField code = new JTextField(), name = new JTextField(), price = new JTextField(), stock = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.add(new JLabel("Product code:")); panel.add(code);
        panel.add(new JLabel("Product name:")); panel.add(name);
        panel.add(new JLabel("Unit price:")); panel.add(price);
        panel.add(new JLabel("Stock quantity:")); panel.add(stock);
        if (JOptionPane.showConfirmDialog(this, panel, "Add Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                service.addProduct(code.getText(), name.getText(), price.getText(), stock.getText());
                refreshProducts();
                JOptionPane.showMessageDialog(this, "Product added successfully.");
            } catch (SQLException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not add product", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
