package PointOfSale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Seller_GUI extends JFrame{
    private JPanel pointOfSaleJPanel;
    private JList listArticlesJList;
    public JTextField idEnter;
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

    private void scanActionPerformed(ActionEvent e) {
        String id = idEnter.getText();
        if (id.equals(""))
            JOptionPane.showMessageDialog(pointOfSaleJPanel, "Invalid bar-code", "Error", JOptionPane.ERROR_MESSAGE);
        else {
        int idArticle = Integer.parseInt(id);
        articleChecker(idArticle);
        }
    }

    private void exitActionPerformed(ActionEvent e) {
        printReceipt();
        JOptionPane.showMessageDialog(pointOfSaleJPanel, df.format(totalSum)+ " zł", "Amount to pay", JOptionPane.INFORMATION_MESSAGE);
        model.clear();
        totalSum = 0;
        sumTextArea.setText("0");
    }

    private void articleChecker(int id) {
        boolean isNotHere = true;
        ResultSet rs = null;
        try {
            rs = DBConnection.executeQuery("SELECT * FROM article WHERE id = " + id);
            while (rs.next()) {
                int idArticle = rs.getInt("id");
                String nameArticle = rs.getString("name");
                double costArticle = rs.getDouble("cost");

                if (id == idArticle) {
                    addToArticleList(idArticle, nameArticle, costArticle);
                    isNotHere = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isNotHere)
            JOptionPane.showMessageDialog(pointOfSaleJPanel ,"Product not found","Error", JOptionPane.ERROR_MESSAGE);
    }

    private void addToArticleList(int id, String articleName, double cost) {
        model.addElement(articleName + " " + cost + "zł");
        listArticlesJList.setModel(model);
        totalSum += cost;
        sumTextArea.setText(df.format(totalSum));
        listOfArticles.add(new Article(id, articleName, cost));
    }

    private void printReceipt() {
        System.out.println("RECEIPT: ");
        for (Article a : listOfArticles)
            System.out.println(a.getName() + " " + a.getCost());
        System.out.println("Total cost: " + df.format(totalSum)+ " zł");
    }

    public static void main(String[] args) {
        new Seller_GUI().setVisible(true);
    }
}