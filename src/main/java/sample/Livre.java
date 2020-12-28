package sample;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Livre {

    private LinkedHashMap<String,String> newLivre;

    public Livre(LinkedHashMap<String, String> newLivre) {
        this.newLivre = newLivre;
    }

    public LinkedHashMap<String, String> getNewLivre() {
        return newLivre;
    }

    public void setNewBook(LinkedHashMap<String, String> newLivre) {
        this.newLivre = newLivre;
    }

    public void addNewLivre() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "INSERT INTO LIVRE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Iterator iterator = newLivre.entrySet().iterator();

        int i = 1;

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            while (iterator.hasNext())
            {
                Map.Entry pair = (Map.Entry) iterator.next();
                pst.setString(i, pair.getValue().toString());
                i++;


            }


            pst.executeUpdate();

            System.out.println("A new book has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }





    }

    public void setBookStatus(String status, String isbn, String dateRetur){
        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "UPDATE LIVRE SET ETAT = ?, DATERET = ? WHERE ISBN = ? ";

        try (Connection con = DriverManager.getConnection(url,user,password);
             PreparedStatement pst = con.prepareStatement(query))
        {

            pst.setString(1, status);
            pst.setString(2, dateRetur);
            pst.setString(3, isbn);


            pst.executeUpdate();

            System.out.println("Status updated");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }



    }

    public void deleteBook(String isbn){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "DELETE FROM Livre WHERE ISBN = ? ";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query))
        {

            pst.setString(1, isbn);
            pst.executeUpdate();

            System.out.println("Book deleted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }


    }

    public void findBook(int choice, String wordToSearchAfter){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";
        String query = null;

        if (choice == 1){

            query = "SELECT * FROM LIVRE WHERE TITLE = '" + wordToSearchAfter + "'";

        }
        else
        {
            if (choice == 2){

                query = "SELECT * FROM LIVRE WHERE AUTEUR_ID = (SELECT ID FROM AUTEUR WHERE NOM = '" + wordToSearchAfter + "')";
            }
            else
            {
                query = "SELECT * FROM LIVRE WHERE EDITURE = '" + wordToSearchAfter + "'";
            }
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                System.out.println("Book(s) found");

                System.out.print(rs.getString(2));
                System.out.print(": ");
                System.out.print (rs.getString(3)+ ", ");
                System.out.print(rs.getString(4) + ", ");
                System.out.print(rs.getString(5) + ", ");
                System.out.print(rs.getString(6) + ", ");
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void showBorrowedBooks(){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "SELECT TITLE,DATERET FROM LIVRE WHERE ETAT = 'Imprunte'";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {


                System.out.print(rs.getString(1));
                System.out.print(": ");
                System.out.print (rs.getString(2));

            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

}


