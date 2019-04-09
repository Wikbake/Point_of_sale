package PointOfSale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static Connection dbConnection;

    static void getDBConncetion() {
        try {
        Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Point_of_sale", "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ResultSet executeQuery(String query) throws SQLException {
        return dbConnection.createStatement().executeQuery(query);
    }
}
