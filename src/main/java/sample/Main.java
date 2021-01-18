package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends Application {

    private Livre l1 = new Livre();
    private Map<String,String> books = new LinkedHashMap();

    private LinkedHashMap<String,String> newLivre = new LinkedHashMap<String,String>();
    private String bookState = " ";
    private String changedBookState = "";

    private Boolean selectedBorrowed = true;
    private Boolean selectedAvailable = true;

    private  Boolean insertionIsCorrect = true;

    private String currentSelectedBookTitle = " ";
    private int searchCriteria;

    public GridPane bookDetailsLayout(String bookTitle){
        GridPane bookGrid = new GridPane();
        final ArrayList<String> bookDetails;
        bookDetails = l1.getDetails(bookTitle);
        System.out.println(bookDetails.toString());

        Button backButton = new Button("\u2190");
        bookGrid.add(backButton,1,1);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backButton.getScene().setRoot(loadMainPane());
            }
        });

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

        bookGrid.add(isbn,3,1);
        bookGrid.add(title,3,2);
        bookGrid.add(nrPages,3,3);
        bookGrid.add(anneePub,3,4);
        bookGrid.add(state,3,5);
        bookGrid.add(genre,3,6);
        bookGrid.add(dateIm,3,7);
        bookGrid.add(dateRet,3,8);
        bookGrid.add(authorId,3,9);
        bookGrid.add(publishingHouse,3,10);



        for(int i = 0; i < bookDetails.size(); i++) {
            System.out.println(bookDetails.get(i));
            Label information = new Label(bookDetails.get(i));
            information.getStyleClass().add("bookDetailLabels");
            bookGrid.add(information,5,i+1);

        }
        bookGrid.getStyleClass().add("grid");
        return bookGrid;

    }

    public GridPane loadSearchBookDetails(String searchWord) {

        ArrayList<String> result = new ArrayList<>();

        GridPane bookGrid = new GridPane();
        bookGrid.getStyleClass().add("grid");
        if (searchCriteria == 1) {

            result = l1.findBook(1, searchWord);
        } else if (searchCriteria == 2) {
            result = l1.findBook(2, searchWord);
        } else if (searchCriteria == 3) {
            result = l1.findBook(3, searchWord);
        }


        if(result.isEmpty())
        {
            bookGrid.add(new Label("Sorry! The results were not found."),4,15);
            Button backBtn = new Button("\u2190");
            bookGrid.add(backBtn,1,1);
            backBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    backBtn.getScene().setRoot(loadMainPane());
                }
            });
        }

        for (int i = 0; i < result.size(); i++) {
            Label book = new Label(result.get(i));
            book.getStyleClass().add("labels");
            book.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (searchCriteria == 1) {
                        book.getScene().setRoot(bookDetailsLayout(searchWord));
                    } else if ((searchCriteria == 2) || (searchCriteria == 3)) {
                        book.getScene().setRoot(bookDetailsLayout(book.getText()));

                    }

                }
            });

            bookGrid.add(book, 3, i);
        }

        return bookGrid;




    }

    public GridPane loadFilterPane(){

        GridPane filterGrid = new GridPane();
        filterGrid.getStyleClass().add("grid");

        CheckBox checkImprunte = new CheckBox("Borrowed books");
        CheckBox checkDisponible = new CheckBox("Available books");

        checkImprunte.getStyleClass().add("label");
        checkDisponible.getStyleClass().add("label");

        filterGrid.add(checkImprunte, 3, 1);
        filterGrid.add(new Label("      "),4,1);
        filterGrid.add(checkDisponible, 5, 1);

        Button backBtn = new Button("\u2190");
        filterGrid.add(backBtn,1,1);
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backBtn.getScene().setRoot(loadMainPane());
            }
        });


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

                            filterGrid.add(title, 3, j);
                            filterGrid.add(titleLabel, 4, j);

                            filterGrid.add(date, 3, j + 1);
                            filterGrid.add(dateLabel, 4, j + 1);

                            j = j + 2;
                        }
                        selectedBorrowed = false;
                    }

                    else {

                        filterGrid.getChildren().clear();
                        filterGrid.add(backBtn,1,1);
                        filterGrid.add(new Label("    "),2,1);
                        filterGrid.add(checkImprunte, 3, 1);
                        filterGrid.add(new Label("    "),4,1);
                        filterGrid.add(checkDisponible, 5, 1);
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

                        filterGrid.add(title, 5, j);
                        filterGrid.add(titleLabel, 6, j);

                        j++;

                    }
                    selectedAvailable = false;
                }

                else {

                    filterGrid.getChildren().clear();
                    filterGrid.add(backBtn,1,1);
                    filterGrid.add(new Label("    "),2,1);
                    filterGrid.add(checkImprunte, 3, 1);
                    filterGrid.add(new Label("    "),4,1);
                    filterGrid.add(checkDisponible, 5, 1);
                    selectedAvailable = true;

                }

            }
        });

        return filterGrid;
    }

    public VBox loadYesDeleteBookPane(String isbn){

        l1.deleteBook(isbn);
        Label message = new Label("Book deleted successfully");
        Button backBtn = new Button("Go Back");
        VBox messagePane = new VBox(message,backBtn);
        messagePane.getStyleClass().add("grid");
        messagePane.setAlignment(Pos.CENTER);
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backBtn.getScene().setRoot(loadMainPane());
            }
        });



        return messagePane;

    }

    public VBox loadQuestionDeletePane(String isbn){

        Label questionLabel = new Label("Are you sure you want to delete this book?");
        Button yesBtn = new Button("Yes");



        Button noBtn = new Button("No");

        noBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                noBtn.getScene().setRoot(loadSelectedBookPane(currentSelectedBookTitle));
            }
        });

        HBox questionBtns = new HBox(60,noBtn,yesBtn);
        questionBtns.setAlignment(Pos.CENTER);

        VBox question = new VBox(50,questionLabel,questionBtns);
        question.setAlignment(Pos.CENTER);
        question.getStyleClass().add("grid");

        yesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                yesBtn.getScene().setRoot(loadYesDeleteBookPane(isbn));
            }
        });




        return question;
    }

    public BorderPane loadChangeStatusGrid(String isbn){

        BorderPane changePane = new BorderPane();

        RadioButton imprunte = new RadioButton("Imprunte");
        RadioButton disponible = new RadioButton("Disponible");



        ToggleGroup tg = new ToggleGroup();
        imprunte.setToggleGroup(tg);
        disponible.setToggleGroup(tg);

        Label dateIm = new Label("Borrowing date");
        Label dateRet = new Label("Returning date");

        TextField dateImT1 = new TextField();
        TextField dateRetT1 = new TextField();


        imprunte.getStyleClass().add("radiobutton");
        disponible.getStyleClass().add("radiobutton");

        Button goBackBtn = new Button("Back");
        changePane.setTop(goBackBtn);


        VBox left = new VBox(new Label("             "));
        left.setFillWidth(true);
        left.setPrefWidth(200);
        left.setAlignment(Pos.TOP_LEFT);
        changePane.setLeft(left);
        goBackBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                goBackBtn.getScene().setRoot(loadSelectedBookPane(currentSelectedBookTitle));
            }
        });

        Button change = new Button("Change");


        GridPane changeGrid = new GridPane();

        changeGrid.add(imprunte,3,16);

        changeGrid.add(disponible,4,16);

        changeGrid.add(change,6,16);


        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton choice = (RadioButton) tg.getSelectedToggle();

                if (choice != null)
                    changedBookState = choice.getText();

                if (choice.getText().equals("Imprunte")) {

                    changeGrid.add(new Label("    "),3,17);
                    changeGrid.add(dateIm,3,18);
                    changeGrid.add(dateImT1,4,18);
                    changeGrid.add(new Label("    "),3,17);
                    changeGrid.add(dateRet,3,19);
                    changeGrid.add(dateRetT1,4,19);








                }}});
        BorderPane.setAlignment(changeGrid,Pos.CENTER);

        changeGrid.setPadding(new Insets(180,80,300,-10));
        changePane.setCenter(changeGrid);


        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(changedBookState.equals("Disponible")){
                    l1.setBookStatusDisponible(isbn);
                }
                else
                {
                    l1.setBookStatusImprunte(isbn,dateImT1.getText(),dateRetT1.getText());



                }
            }
        });

        changePane.getStyleClass().add("grid");

        return changePane;

    }

    public GridPane loadSelectedBookPane(String bookLabel){

        GridPane bookGrid = new GridPane();
        bookGrid.getStyleClass().add("grid");

        bookGrid = bookDetailsLayout(bookLabel);

        ArrayList<String> bookDetails = l1.getDetails(bookLabel);

        Button deleteBtn = new Button("Delete Book");
        deleteBtn.getStyleClass().add("gridButtons");

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteBtn.getScene().setRoot(loadQuestionDeletePane(bookDetails.get(0)));
            }
        });

        Button changeStatus = new Button("Change book status");
        changeStatus.getStyleClass().add("gridButtons");

        bookGrid.add(new Label(""),3,12);
        bookGrid.add(new Label("   "),2,1);
        bookGrid.add(new Label("   "),1,1);
        bookGrid.add(new Label("   "),3,12);

        bookGrid.add(changeStatus,4,13);
        bookGrid.add(deleteBtn,3,13);

        changeStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                changeStatus.getScene().setRoot( loadChangeStatusGrid(bookDetails.get(0)));


            }
        });



        return bookGrid;
    }




    public Accordion loadAllBooksAccordion() {

        Accordion accordion = new Accordion();
        accordion.getStyleClass().add("accordion");

        Iterator it = books.entrySet().iterator();
        ArrayList<String> authorList = new ArrayList<>();
        ArrayList<String> bookListFromAuthor = new ArrayList<>();

        authorList = l1.getAuthors();


        for (int i = 0; i < authorList.size(); i++) {

            TitledPane authorPane = new TitledPane();
            authorPane.getStyleClass().add("titled-pane");

            authorPane.setText(authorList.get(i));
            accordion.getPanes().add(authorPane);
            ListView<Label> books = new ListView<>();
            books.getStyleClass().add("list-cell");
            books.setPrefSize(50.0, 50.0);

            bookListFromAuthor = l1.getBooksFromAuthor(authorList.get(i));

            for(int j = 0; j < bookListFromAuthor.size(); j++){
                Label bookLabel = new Label(bookListFromAuthor.get(j));
                bookLabel.getStyleClass().add("list-cell-text");
                bookLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        bookLabel.getScene().setRoot(loadSelectedBookPane(bookLabel.getText()));
                        currentSelectedBookTitle = bookLabel.getText();
                    }
                });

                books.getItems().add(bookLabel);
            }

            authorPane.setContent(books);

            accordion.setPadding(new Insets(25,25,25,25));

        }

        return accordion;
    }

    public GridPane loadAddBookPane() {

        GridPane newBookGrid = new GridPane();
        newBookGrid.getStyleClass().add("grid");

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
        TextField authorIdT = new TextField();
        TextField publishingHouseT = new TextField();


        RadioButton imprunte = new RadioButton("Imprunte");
        RadioButton disponible = new RadioButton("Disponible");


        newBookGrid.add(isbn, 3, 1);
        newBookGrid.add(isbnT, 4, 1);

        newBookGrid.add(title, 3, 2);
        newBookGrid.add(titleT, 4, 2);

        newBookGrid.add(nrPages, 3, 3);
        newBookGrid.add(nrPagesT, 4, 3);

        newBookGrid.add(anneePub, 3, 4);
        newBookGrid.add(anneePubT, 4, 4);

        newBookGrid.add(state, 3, 5);
        newBookGrid.add(imprunte, 4, 5);
        newBookGrid.add(disponible, 4, 6);


        newBookGrid.add(genre, 3, 7);
        newBookGrid.add(genreT, 4, 7);

        newBookGrid.add(dateIm, 3, 8);
        newBookGrid.add(dateImT, 4, 8);

        newBookGrid.add(dateRet, 3, 9);
        newBookGrid.add(dateRetT, 4, 9);

        newBookGrid.add(authorId, 3, 10);
        newBookGrid.add(authorIdT, 4, 10);

        newBookGrid.add(publishingHouse, 3, 11);
        newBookGrid.add(publishingHouseT, 4, 11);

        Button addBookBtn = new Button("Add Book");

        ToggleGroup tg = new ToggleGroup();
        imprunte.setToggleGroup(tg);
        disponible.setToggleGroup(tg);

        imprunte.getStyleClass().add("radiobutton");
        disponible.getStyleClass().add("radiobutton");


        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton choice = (RadioButton) tg.getSelectedToggle();

                if (choice != null)
                    bookState = choice.getText();
            }
        });

        Button authorIds = new Button("Author IDs");

        authorIds.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> authorList = new ArrayList<>();
                authorList = l1.getAuthors();

                for (int i = 0; i < authorList.size(); i++)
                {
                    Label id = new Label(String.valueOf(i+1) + " - ");
                    Label name = new Label(authorList.get(i));

                    newBookGrid.add(id,10,i+1);
                    newBookGrid.add(name,11,i+1);
                }

            }
        });
        //newBookGrid.add(new Label("     "),8,1);
        newBookGrid.add(authorIds,9,1);

        newBookGrid.add(new Label("  "),3,12);
        newBookGrid.add(addBookBtn,3,13);

        addBookBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Label lShort = new Label("ISBN too short! Please enter a valid ISBN.");
                Label lLong = new Label("ISBN too short! Please enter a valid ISBN.");
                Label lNoExist = new Label("This author id doesn't exist. Please add a new author first or insert a valid id!");


                if(isbnT.getText().length() > 13)
                {
                    newBookGrid.add(lShort, 5,1);

                    insertionIsCorrect = false;
                }
                else
                if(isbnT.getText().length() < 13)
                {

                    newBookGrid.add(lLong, 5,1);

                    insertionIsCorrect = false;

                }
                else
                {
                    lShort.setText(" ");
                    lLong.setText(" ");
                }



                ArrayList<String> authorList = l1.getAuthors();
                int currentAuthorId = Integer.parseInt(authorIdT.getText());
                if((currentAuthorId < 0) || (currentAuthorId > authorList.size()))
                {
                    newBookGrid.add(lNoExist,4,9);

                    insertionIsCorrect = false;
                }
                else
                {
                    lNoExist.setText(" ");
                }


                if((isbnT.getText().length() == 13) && (currentAuthorId < authorList.size()))
                {
                    insertionIsCorrect = true;

                    newBookGrid.add(new Label("Book added successfully"),3,14);
                }


                if(insertionIsCorrect == true) {
                    newLivre.put("isbn", isbnT.getText().toString());
                    newLivre.put("title", titleT.getText().toString());
                    newLivre.put("nrpages", nrPagesT.getText().toString());
                    newLivre.put("anneepub", anneePubT.getText().toString());


                    newLivre.put("etat", bookState.toString());
                    newLivre.put("genre", genreT.getText().toString());
                    // if(dateImT.getText() != null)

                    newLivre.put("dateim", null);


                    newLivre.put("dateret", null);


                    newLivre.put("auteur_id", authorIdT.getText().toString());
                    newLivre.put("editure", publishingHouseT.getText());

                }

                System.out.println(newLivre);

                Livre bookToAdd = new Livre(newLivre);

                try {


                    bookToAdd.addNewLivre();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                newLivre.clear();
            }
        });

        newBookGrid.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("\u2190");
        newBookGrid.add(new Label(" "),2,1);
        newBookGrid.add(backButton,1,1);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backButton.getScene().setRoot(loadMainPane());
            }
        });


        return newBookGrid;



    }

    public BorderPane loadMainPane(){

        BorderPane bPane = new BorderPane();

        Label welcomeText = new Label("Welcome to BookEX!");

        bPane.setCenter(welcomeText);
        bPane.setStyle("-fx-background-color:#005b96;" +
                "-fx-font-size:16px;"+
                "-fx-text-fill:#b3cde0;" +
                "-fx-font-weight:bold;"
        );
        welcomeText.setStyle(" -fx-padding: 5px;"  +
                "    -fx-border-insets: 5px; " +
                " -fx-background-insets: 5px;" +
                "-fx-text-fill:#b3cde0;"+
                "-fx-font-size:30px;");


        Button btn = new Button();
        btn.setText("Menu");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                btn.setStyle("-fx-background-color:	#e8745b");
            }
        });



        final StackPane root = new StackPane();
        AnchorPane editorRoot = new AnchorPane();
        editorRoot.getChildren().add(btn);
        root.getChildren().add(editorRoot);

        bPane.setLeft(root);



        HBox fileRoot = new HBox();
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: #b3cde0;");
        menu.setFillWidth(true);
        Button backBtn = new Button("\u2190");
        backBtn.setPrefWidth(150);
        backBtn.getStyleClass().add("menu-buttons");



        backBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                hideFileRootTransition.setFromValue(1.0);
                hideFileRootTransition.setToValue(0.0);

                FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                showEditorRootTransition.setFromValue(0.0);
                showEditorRootTransition.setToValue(1.0);

                Label welcomeText = new Label("Welcome to BookEX!");
                welcomeText.setStyle(" -fx-padding: 5px;" +
                        " -fx-border-insets: 5px; " +
                        " -fx-background-insets: 5px;" +
                        " -fx-text-fill:#b3cde0;" +
                        "-fx-font-size:30px;");


                showEditorRootTransition.play();
                hideFileRootTransition.play();
                root.getChildren().remove(fileRoot);
                bPane.setCenter(welcomeText);


            }
        });

        Button booksBtn = new Button("Books");
        booksBtn.setPrefWidth(150);
        booksBtn.getStyleClass().add("menu-buttons");
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

                RadioButton searchAfterAuthor = new RadioButton("Author");
                RadioButton searchAfterTitle = new RadioButton("Book Title");
                RadioButton searchAfterEdit = new RadioButton("Publishing House");

                searchAfterAuthor.getStyleClass().add("radiobutton");
                searchAfterTitle.getStyleClass().add("radiobutton");
                searchAfterEdit.getStyleClass().add("radiobutton");

                ToggleGroup tg = new ToggleGroup();

                searchAfterAuthor.setToggleGroup(tg);
                searchAfterEdit.setToggleGroup(tg);
                searchAfterTitle.setToggleGroup(tg);

                tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                    @Override
                    public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                        RadioButton choice = (RadioButton) tg.getSelectedToggle();

                        if(choice != null)
                        {
                            if(choice.getText().equals("Book Title"))
                            {
                                searchCriteria = 1;
                            }
                            else
                            {
                                if(choice.getText().equals("Author"))
                                {
                                    searchCriteria = 2;
                                }
                                else
                                {
                                    if(choice.getText().equals("Publishing House"))
                                    {
                                        searchCriteria = 3;
                                    }
                                }
                            }
                        }

                    }
                });

                bookList.add(searchAfterTitle,7,2);
                bookList.add(searchAfterAuthor,7,3);
                bookList.add(searchAfterEdit,7,4);

                searchButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        searchButton.getScene().setRoot(loadSearchBookDetails(searchInput.getText()));//Make radio buttons to choose criteria
                    }
                });

                Button filterBtn = new Button("Filter");


                filterBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        filterBtn.getScene().setRoot(loadFilterPane());
                    }
                });

                Button goBackBtn = new Button("Back");


                goBackBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        goBackBtn.getScene().setRoot(loadMainPane());
                    }
                });
                GridPane top = new GridPane();
                top.add(goBackBtn,1,1);
                top.add(new Label("      "),2,1);
                top.add(filterBtn,3,1);
                bPane.setLeft(top);

                bPane.setCenter(loadAllBooksAccordion());
            }
        });

        Button addBtn = new Button("Add a book");
        addBtn.setPrefWidth(150);
        addBtn.getStyleClass().add("menu-buttons");

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bPane.setCenter(loadAddBookPane());
                addBtn.getScene().setRoot(loadAddBookPane());
            }
        });







        menu.getChildren().addAll(backBtn, booksBtn, addBtn);
        VBox.setVgrow(booksBtn, Priority.ALWAYS);
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


        return bPane;
    }







    @Override
    public void start(Stage primaryStage) throws Exception{


        Scene scene = new Scene(loadMainPane(), 800, 650);
        scene.getStylesheets().add("styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        launch(args);

    }
}
