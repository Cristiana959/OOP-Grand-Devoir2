package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //launch(args);

        LinkedHashMap<String,String> newLivre = new LinkedHashMap<String,String>();

        newLivre.put("isbn","1111111111111");
        newLivre.put("title","The Idiot");
        newLivre.put("nrpages","450");
        newLivre.put("anneepub","2014");
        newLivre.put("etat","Disponible");
        newLivre.put("genre","adventure");
        newLivre.put("dateim","2020-05-12");
        newLivre.put("dateret","2020-05-18");
        newLivre.put("auteur_id","4");
        newLivre.put("editure","Penguin Classics");

        Livre l1 = new Livre(newLivre);
         l1.addNewLivre();
        l1.setBookStatus("Imprunte","1234567891011", "2020-02-27");
        //System.out.println(java.time.LocalDate.now());

        System.out.println(newLivre);

        System.out.println(newLivre);

        //l1.deleteBook("1234567891011");

        l1.findBook(3,"Macmillan Collector Library");

        l1.setBookStatus("Imprunte","9780451479914", "2020-09-23");

        l1.showBorrowedBooks();

    }
}

