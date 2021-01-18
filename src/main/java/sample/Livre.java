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

    public Livre() {

    }

    public LinkedHashMap<String, String> getNewLivre() {
        return newLivre;
    }

    public void setNewBook(LinkedHashMap<String, String> newLivre) {
        this.newLivre = newLivre;
    }

    public  LinkedHashMap<String,String> showAllBooks(){
        LinkedHashMap books = new LinkedHashMap();
        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "SELECT TITLE, EDITURE FROM LIVRE ";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {


                books.put(rs.getString(1),rs.getString(2));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
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
                if(pair.getValue()== null)
                    pst.setNull(i, Types.DATE);
                else
                if(pair.getValue().toString() != null)
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

    public void setBookStatusImprunte( String isbn, String dateImp, String dateRetur){
        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "UPDATE LIVRE SET ETAT = 'IMPRUNTE', DATEIM = ?, DATERET = ? WHERE ISBN = ? ";

        try (Connection con = DriverManager.getConnection(url,user,password);
             PreparedStatement pst = con.prepareStatement(query))
        {


            pst.setString(1, dateImp);
            pst.setString(2, dateRetur);
            pst.setString(3, isbn);


            pst.executeUpdate();

            System.out.println("Status updated");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }



    }

    public void setBookStatusDisponible(String isbn){
        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "UPDATE LIVRE SET ETAT = 'DISPONIBLE', DATEIM = NULL, DATERET = NULL WHERE ISBN = ? ";

        try (Connection con = DriverManager.getConnection(url,user,password);
             PreparedStatement pst = con.prepareStatement(query))
        {


            pst.setString(1, isbn);


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

    public ArrayList<String> findBook(int choice, String wordToSearchAfter){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";
        String query = null;

        ArrayList<String> result = new ArrayList<>();

        if (choice == 1){

            query = "SELECT TITLE FROM LIVRE WHERE TITLE = '" + wordToSearchAfter + "'";

        }
        else
        {
            if (choice == 2){

                query = "SELECT TITLE FROM LIVRE WHERE AUTEUR_ID = (SELECT ID FROM AUTEUR WHERE NOM = '" + wordToSearchAfter + "')";
            }
            else
            {
                query = "SELECT TITLE FROM LIVRE WHERE EDITURE = '" + wordToSearchAfter + "'";
            }
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                System.out.println("Book(s) found");

                result.add(rs.getString(1));

                ;
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public ArrayList<String> showBorrowedBooks(){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "SELECT TITLE,DATERET FROM LIVRE WHERE ETAT = 'Imprunte'";

        ArrayList<String> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {


                result.add(rs.getString(1));
                result.add(rs.getString(2));


            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;

    }

    public ArrayList<String> showAvailableBooks(){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "SELECT TITLE FROM LIVRE WHERE ETAT = 'Disponible'";

        ArrayList<String> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {


                result.add(rs.getString(1));



            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;

    }

    public ArrayList<String> getBooksFromAuthor(String author){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String  query = "SELECT Title FROM LIVRE WHERE AUTEUR_ID = (SELECT ID FROM AUTEUR WHERE NOM = '" + author + "')";

        ArrayList<String> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {


                result.add(rs.getString(1));


            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public ArrayList<String> getAuthors(){

        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "SELECT NOM FROM AUTEUR";

        ArrayList<String> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {


                result.add(rs.getString(1));


            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }


    public ArrayList<String> getDetails(String book) {
        String url = "jdbc:mysql://localhost:3306/bibliotheque?useSSL=false";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM LIVRE WHERE TITLE = '" + book + "'";

        ArrayList<String> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);

             ResultSet rs = pst.executeQuery()) {

            int i = 1;

            while (rs.next()) {

                while(i <= 10){
                    result.add(rs.getString(i));
                    i++;}


            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Livre.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

}



