package sample;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcPrepared {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";
        String author = "Trygve Gulbranssen";
        String s = "nom";
        // String sql = "INSERT INTO Authors(Name) VALUES(?)";

        String query = "SELECT "+s+" FROM Auteur";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                System.out.println(rs.getString(1));
                    /*System.out.print(": ");
                    System.out.print (rs.getString(2));
                    System.out.println(rs.getString(3));*/
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcPrepared.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}

