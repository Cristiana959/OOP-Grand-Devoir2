package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Main extends Application {

    Livre l1 = new Livre();
    Map<String,String> books = new LinkedHashMap();
    private TableView bookTable = new TableView();
    LinkedHashMap<String,String> newLivre = new LinkedHashMap<String,String>();
    String bookState = " ";
    String changedBookState = "";
    Boolean selectedBorrowed = true;
    Boolean selectedAvailable = true;//to check if the filter checkboxes are selected or not


    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane bPane = new BorderPane();

        Text welcomeText = new Text("Welcome to BookEX");

        bPane.setCenter(welcomeText);


        Button btn = new Button();
        btn.setText("Menu");

        final StackPane root = new StackPane();
        AnchorPane editorRoot = new AnchorPane();
        editorRoot.getChildren().add(btn);
        root.getChildren().add(editorRoot);

        bPane.setLeft(root);

        Scene scene = new Scene(bPane, 700, 550);
        scene.getStylesheets().add("menustyle.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        HBox fileRoot = new HBox();
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: blue;");
        menu.setFillWidth(true);
        Button backBtn = new Button("Left Arrow");
        backBtn.setPrefWidth(100);
        backBtn.getStyleClass().add("custom-menu-button");
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                hideFileRootTransition.setFromValue(1.0);
                hideFileRootTransition.setToValue(0.0);

                FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                showEditorRootTransition.setFromValue(0.0);
                showEditorRootTransition.setToValue(1.0);

                Label welcomeText = new Label("Welcome to BookEX");


                showEditorRootTransition.play();
                hideFileRootTransition.play();
                root.getChildren().remove(fileRoot);
                bPane.setCenter(welcomeText);
            }
        });
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(100);
        loginBtn.getStyleClass().add("custom-menu-button");
        Button booksBtn = new Button("Books");
        booksBtn.setPrefWidth(100);
        booksBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                books = l1.showAllBooks();
                GridPane bookList = new GridPane();

                TextField searchInput = new TextField();
                Button searchButton = new Button("Search");

               bookList.add(searchInput,7,1);
               bookList.add(searchButton,8,1);
               bPane.setRight(bookList);



                searchButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        ArrayList<String> result = new ArrayList<>();

                        GridPane bookGrid = new GridPane();

                        result = l1.findBook(2,searchInput.getText());


                        for(int i = 0; i < result.size(); i++){
                            Label book = new Label(result.get(i));
                            book.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    GridPane g = new GridPane();
                                    g.setHgap(24.0);
                                    g.setVgap(24.0);
                                    Label something = new Label("Something");
                                    g.add(something,2,3);
                                    Scene scene3 = new Scene(g,100,100);
                                    primaryStage.setScene(scene3);
                                    primaryStage.show();
                                }
                            });

                            bookGrid.add(book,3,i);

                        }
                        Scene scene2 = new Scene(bookGrid,200,200);
                        primaryStage.setScene(scene2);
                        primaryStage.show();
                    }
                });

                Button filterBtn = new Button("Filter");
                bPane.setBottom(filterBtn);

                filterBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        GridPane filterGrid = new GridPane();

                        CheckBox checkImprunte = new CheckBox("Borrowed books");
                        CheckBox checkDisponible = new CheckBox("Available books");

                        filterGrid.add(checkImprunte, 1, 1);
                        filterGrid.add(checkDisponible, 2, 1);


                        if (selectedBorrowed == true) {
                            checkImprunte.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                    if(selectedBorrowed == true) {
                                        ArrayList<String> checkResults;

                                        checkResults = l1.showBorrowedBooks();
                                        System.out.println(checkResults);

                                        int j = 3;

                                        for (int i = 0; i < checkResults.size(); i = i + 2) {
                                            Label title = new Label("Title: ");
                                            Label date = new Label("Returning date: ");
                                            Label titleLabel = new Label(checkResults.get(i));
                                            Label dateLabel = new Label(checkResults.get(i + 1));

                                            filterGrid.add(title, 1, j);
                                            filterGrid.add(titleLabel, 2, j);

                                            filterGrid.add(date, 1, j + 1);
                                            filterGrid.add(dateLabel, 2, j + 1);

                                            j = j + 2;
                                        }
                                        selectedBorrowed = false;
                                    }

                                    else {

                                        filterGrid.getChildren().clear();
                                        filterGrid.add(checkImprunte, 1, 1);
                                        filterGrid.add(checkDisponible, 2, 1);
                                        selectedBorrowed = true;
                                    }



                                }

                            });




                        }


                             checkDisponible.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                 @Override
                                 public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                    if(selectedAvailable == true){
                                     ArrayList<String> checkResults;
                                     checkResults = l1.showAvailableBooks();

                                        int j = 3;
                                     for (int i = 0; i < checkResults.size(); i = i + 2) {
                                         Label title = new Label("Title: ");
                                         Label titleLabel = new Label(checkResults.get(i));

                                         filterGrid.add(title, 1, j);
                                         filterGrid.add(titleLabel, 2, j);

                                         j++;

                                     }
                                     selectedAvailable = false;
                                 }

                                    else {

                                        filterGrid.getChildren().clear();
                                        filterGrid.add(checkImprunte, 1, 1);
                                        filterGrid.add(checkDisponible, 2, 1);
                                        selectedAvailable = true;

                                    }

                                 }
                             });



                        Scene scene7 = new Scene(filterGrid,300,300);
                        primaryStage.setScene(scene7);
                        primaryStage.show();

                    }
                });


                Accordion accordion = new Accordion();

                Iterator it = books.entrySet().iterator();
                ArrayList<String> authorList = new ArrayList<>();
                ArrayList<String> bookListFromAuthor = new ArrayList<>();

                authorList = l1.getAuthors();



                for(int i = 0; i < authorList.size(); i++){

                    TitledPane authorPane = new TitledPane();
                    authorPane.setText(authorList.get(i));
                    accordion.getPanes().add(authorPane);
                    ListView<Label> books = new ListView<>();
                    books.setPrefSize(50.0,50.0);

                    bookListFromAuthor = l1.getBooksFromAuthor(authorList.get(i));

                    for(int j = 0; j < bookListFromAuthor.size(); j++){
                        Label bookLabel = new Label(bookListFromAuthor.get(j));
                        bookLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                GridPane bookGrid = new GridPane();
                                final ArrayList<String> bookDetails;
                                bookDetails = l1.getDetails(bookLabel.getText().toString());
                                System.out.println(bookDetails.toString());

                                Label isbn = new Label("ISBN");
                                Label title = new Label("Title");
                                Label nrPages = new Label("Number of pages");
                                Label anneePub = new Label("Year of publication");
                                Label state = new Label("State");
                                Label genre = new Label("Genre");
                                Label dateIm = new Label("Borrowing date");
                                Label dateRet = new Label("Returning date");
                                Label authorId = new Label("Author ID");
                                Label publishingHouse = new Label("Publishing House");

                                bookGrid.add(isbn,1,1);
                                bookGrid.add(title,1,2);
                                bookGrid.add(nrPages,1,3);
                                bookGrid.add(anneePub,1,4);
                                bookGrid.add(state,1,5);
                                bookGrid.add(genre,1,6);
                                bookGrid.add(dateIm,1,7);
                                bookGrid.add(dateRet,1,8);
                                bookGrid.add(authorId,1,9);
                                bookGrid.add(publishingHouse,1,10);



                                for(int i = 0; i < bookDetails.size(); i++) {
                                    System.out.println(bookDetails.get(i));
                                    Label information = new Label(bookDetails.get(i));
                                    bookGrid.add(information,3,i+1);

                                }

                                Button deleteButton = new Button("Delete Book");
                                 deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                                     @Override
                                     public void handle(ActionEvent actionEvent) {

                                         Label questionLabel = new Label("Are you sure you want to delete this book?");
                                         Button yesBtn = new Button("Yes");



                                         Button noBtn = new Button("No");

                                         HBox questionBtns = new HBox(60,noBtn,yesBtn);
                                         questionBtns.setAlignment(Pos.CENTER);

                                         VBox question = new VBox(50,questionLabel,questionBtns);
                                         question.setAlignment(Pos.CENTER);

                                         yesBtn.setOnAction(new EventHandler<ActionEvent>() {
                                             @Override
                                             public void handle(ActionEvent actionEvent) {
                                                 l1.deleteBook(bookDetails.get(0));
                                                 Label message = new Label("Book deleted successfully");
                                                 VBox messagePane = new VBox(message);
                                                 messagePane.setAlignment(Pos.CENTER);

                                                 Scene scene5 = new Scene(messagePane,200,200);
                                                 primaryStage.setScene(scene5);
                                                 primaryStage.show();
                                             }
                                         });

                                         Scene scene3 = new Scene(question,300,200);
                                         primaryStage.setScene(scene3);
                                         primaryStage.show();

                                     }
                                 });

                                 bookGrid.add(deleteButton,1,12);

                                 Button changeStatus = new Button("Change book status");
                                 bookGrid.add(changeStatus,3,12);

                                 changeStatus.setOnAction(new EventHandler<ActionEvent>() {
                                     @Override
                                     public void handle(ActionEvent actionEvent) {
                                         GridPane changeGrid = new GridPane();
                                         RadioButton imprunte = new RadioButton("Imprunte");
                                         RadioButton disponible = new RadioButton("Disponible");

                                         changeGrid.add(imprunte,1,1);
                                         changeGrid.add(disponible,2,1);

                                         ToggleGroup tg = new ToggleGroup();
                                         imprunte.setToggleGroup(tg);
                                         disponible.setToggleGroup(tg);

                                         Label dateIm = new Label("Borrowing date");
                                         Label dateRet = new Label("Returning date");

                                         TextField dateImT1 = new TextField();
                                         TextField dateRetT1 = new TextField();

                                         tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                                             @Override
                                             public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                                                 RadioButton choice = (RadioButton)tg.getSelectedToggle();

                                                 if(choice != null)
                                                     changedBookState = choice.getText();

                                                 if(choice.getText().equals("Imprunte")){


                                                     changeGrid.add(dateIm,1,2);
                                                     changeGrid.add(dateImT1,2,2);

                                                     changeGrid.add(dateRet,1,3);
                                                     changeGrid.add(dateRetT1,2,3);


                                                 }


                                             }


                                         });




                                         Button change = new Button("Change");

                                         change.setOnAction(new EventHandler<ActionEvent>() {
                                             @Override
                                             public void handle(ActionEvent actionEvent) {
                                                 if(changedBookState.equals("Disponible")){
                                                     l1.setBookStatusDisponible(bookDetails.get(0));
                                                 }
                                                 else
                                                 {
                                                     l1.setBookStatusImprunte(bookDetails.get(0),dateImT1.getText(),dateRetT1.getText());



                                                 }
                                             }
                                         });

                                        changeGrid.add(change,3,3);

                                         Scene scene6 = new Scene(changeGrid,300,300);
                                         primaryStage.setScene(scene6);
                                         primaryStage.show();

                                     }
                                 });

                                Scene scene4 = new Scene(bookGrid,400,400);
                                primaryStage.setScene(scene4);
                                primaryStage.show();

                            }
                        });
                        books.getItems().add(bookLabel);
                    }
                    authorPane.setContent(books);

                accordion.setPadding(new Insets(25,25,25,25));

                bPane.setCenter(accordion);
            }
        }});
        booksBtn.getStyleClass().add("custom-menu-button");
        Button addBtn = new Button("Add a book");
        addBtn.setPrefWidth(100);

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane newBookGrid = new GridPane();
                Label isbn = new Label("ISBN");
                Label title = new Label("Title");
                Label nrPages = new Label("Number of pages");
                Label anneePub = new Label("Year of publication");
                Label state = new Label("State");
                Label genre = new Label("Genre");
                Label dateIm = new Label("Borrowing date");
                Label dateRet = new Label("Returning date");
                Label authorId = new Label("Author ID");
                Label publishingHouse = new Label("Publishing House");

                TextField isbnT = new TextField();
                TextField titleT = new TextField();
                TextField nrPagesT = new TextField();
                TextField anneePubT = new TextField();
                TextField genreT = new TextField();
                TextField dateImT = new TextField();
                TextField dateRetT = new TextField();
                TextField authorIdT= new TextField();
                TextField publishingHouseT = new TextField();

                RadioButton imprunte = new RadioButton("Imprunte");
                RadioButton disponible = new RadioButton("Disponible");



                newBookGrid.add(isbn,1,1);
                newBookGrid.add(isbnT,3,1);

                newBookGrid.add(title,1,2);
                newBookGrid.add(titleT,3,2);

                newBookGrid.add(nrPages,1,3);
                newBookGrid.add(nrPagesT,3,3);

                newBookGrid.add(anneePub,1,4);
                newBookGrid.add(anneePubT,3,4);

                newBookGrid.add(state,1,5);
                newBookGrid.add(imprunte,3,5);
                newBookGrid.add(disponible,6,5);


                newBookGrid.add(genre,1,6);
                newBookGrid.add(genreT,3,6);

                newBookGrid.add(dateIm,1,7);
                newBookGrid.add(dateImT,3,7);

                newBookGrid.add(dateRet,1,8);
                newBookGrid.add(dateRetT,3,8);

                newBookGrid.add(authorId,1,9);
                newBookGrid.add(authorIdT,3,9);

                newBookGrid.add(publishingHouse,1,10);
                newBookGrid.add(publishingHouseT,3,10);

                Button addBookBtn = new Button("Add Book");

                ToggleGroup tg = new ToggleGroup();
                imprunte.setToggleGroup(tg);
                disponible.setToggleGroup(tg);


                tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                    @Override
                    public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                        RadioButton choice = (RadioButton)tg.getSelectedToggle();

                        if(choice != null)
                            bookState = choice.getText();
                    }
                });



                newBookGrid.add(addBookBtn,3,11);
                addBookBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newLivre.put("isbn",isbnT.getText().toString());
                        newLivre.put("title",titleT.getText().toString());
                        newLivre.put("nrpages",nrPagesT.getText().toString());
                        newLivre.put("anneepub",anneePubT.getText().toString());
                        newLivre.put("etat",bookState.toString());
                        newLivre.put("genre",genreT.getText().toString());
                       // if(dateImT.getText() != null)
                         //   newLivre.put("dateim",dateImT.getText().toString());
                      //  else
                            newLivre.put("dateim",null);

                        //if(dateRetT.getText() != null)
                         // newLivre.put("dateret",dateRetT.getText().toString());
                        //else
                            newLivre.put("dateret",null);

                        newLivre.put("auteur_id",authorIdT.getText().toString());
                        newLivre.put("editure",publishingHouseT.getText().toString());

                        Livre bookToAdd = new Livre(newLivre);

                        try {
                            bookToAdd.addNewLivre();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }


                    }
                });

                newBookGrid.setPadding(new Insets(30,30,30,30));

                bPane.setCenter(newBookGrid);

            }
        });

        addBtn.getStyleClass().add("custom-menu-button");
        menu.getChildren().addAll(backBtn,loginBtn, booksBtn, addBtn);
        VBox.setVgrow(loginBtn, Priority.ALWAYS);
        fileRoot.getChildren().add(menu);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().add(fileRoot);
                FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                hideEditorRootTransition.setFromValue(1.0);
                hideEditorRootTransition.setToValue(0.0);

                FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                showFileRootTransition.setFromValue(0.0);
                showFileRootTransition.setToValue(1.0);
                hideEditorRootTransition.play();
                showFileRootTransition.play();
            }
        });


    }


    public static void main(String[] args) throws SQLException {
        launch(args);

        /*LinkedHashMap<String,String> newLivre = new LinkedHashMap<String,String>();

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

        l1.showBorrowedBooks();*/

    }
}

