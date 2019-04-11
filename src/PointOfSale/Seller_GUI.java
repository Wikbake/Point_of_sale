package PointOfSale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This is main class which cooperates with <code>DBConnection</code> and <code>Article</code> classes. For now
 * it is set to get data from mocked method <code>ResultSet</code> but is also ready to work with real PostgreSQL.
 * As for input it is necessary to enter codes by hand in the right place while application is running.
 * <code>PrintReceipt</code> method is responsible for simulation the output.
 * Some access specifiers are package-private or protected instead of private because of test class!
 *
 * IMPORTANT!
 * If database is not connected even though excepton will be thrown the application will work correctly.
 *
 * @author Bartlomiej Karbownik
 */

public class Seller_GUI extends JFrame{
    private JPanel pointOfSaleJPanel;
    protected JList listArticlesJList;
    protected JTextField idEnter;
    private JButton scanButton;
    private JButton exitPrintReceiptButton;
    private JTextArea sumTextArea;
    private JPanel yourArticlesJPanel;
    private JPanel workspaceJPanel;


    public Seller_GUI() {
        DBConnection.getDBConncetion();
        add(pointOfSaleJPanel);

        this.setTitle("Point of sale");
        this.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 250, Toolkit.getDefaultToolkit().getScreenSize().height/2 - 250, 250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        scanButton.addActionListener(this::scanActionPerformed);
        exitPrintReceiptButton.addActionListener(this::exitActionPerformed);
    }

    private List<Article> listOfArticles = new ArrayList<Article>();
    private DefaultListModel model = new DefaultListModel();
    private double totalSum = 0;
    private DecimalFormat df = new DecimalFormat("0.00");


    void scanActionPerformed(ActionEvent e) {
        String id = idEnter.getText();
        try {
            if (id.equals(""))
                throw new InvalidBarCodeException();
            else {
                long idArticle = Long.parseLong(id);
                articleChecker(idArticle);
            }
        } catch (InvalidBarCodeException | NumberFormatException ex) {
            System.out.println("Invalid bar-code!");
            JOptionPane.showMessageDialog(pointOfSaleJPanel, "Invalid bar-code", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exitActionPerformed(ActionEvent e) {
        printReceipt();
        JOptionPane.showMessageDialog(pointOfSaleJPanel, df.format(totalSum)+ " zł", "Amount to pay", JOptionPane.INFORMATION_MESSAGE);
        model.clear();
        listOfArticles.clear();
        totalSum = 0;
        sumTextArea.setText("0");
    }

    private void articleChecker(long id) {
        ResultSet rs = null;
        long idArticle = 0L;
        try {
            /*
            *
            * You can use this in case of real database. Otherwise you will use mocked data from <code>DBConnection</code>.
            *
            rs = DBConnection.executeQuery("SELECT * FROM article WHERE id = " + id);
             */
            rs = DBConnection.executeQuery(String.valueOf(id));
            while (rs.next()) {
                idArticle = rs.getLong("id");
                String nameArticle = rs.getString("name");
                double costArticle = rs.getDouble("cost");

                System.out.println("Product found!");
                addToArticleList(idArticle, nameArticle, costArticle);
            }
            if (id != idArticle)
                throw new ProductNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ProductNotFoundException e) {
            System.out.println("Product not found!");
            JOptionPane.showMessageDialog(pointOfSaleJPanel, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void addToArticleList(long id, String articleName, double cost) {
        model.addElement(articleName + " " + cost + "zł");
        listArticlesJList.setModel(model);
        System.out.println("Product added to list!");
        totalSum += cost;
        sumTextArea.setText(df.format(totalSum));
        listOfArticles.add(new Article(id, articleName, cost));
    }

    protected void printReceipt() {
        System.out.println("RECEIPT: ");
        for (Article a : listOfArticles)
            System.out.println(a.getName() + " " + a.getCost());
        System.out.println("Total cost: " + df.format(totalSum)+ " zł");
    }

    public static void main(String[] args) {
        new Seller_GUI().setVisible(true);
    }
}