package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    int counter = 0;
    int nr_check = 0;
    int num_Questions = 1;

    public static void addItemsToGrid(GridPane g, ArrayList<Node> arrayList)
    {
        int i = 0,j=0,k=0,t=0;
        for(Node obj : arrayList)
        {
            g.add(obj,i,j++,k++,t++);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");


        GridPane gridPane = new GridPane();
        //gridPane.add(question,0,0,2,1);


        TextField question = new TextField();

        Label test = new Label();

        Button next_button = new Button();
        Button prev_button = new Button();

        RadioButton one_answer =new RadioButton();
        RadioButton multiple_answer = new RadioButton();

        ArrayList<Node> arrayList = new ArrayList<>();
        arrayList.add(next_button);
        arrayList.add(prev_button);

        /** Button for next question*/
        next_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //test.setText(String.valueOf(counter++));
            }
        });

        /** Button for prev question*/
        prev_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //test.setText(String.valueOf(counter--));
            }
        });

        /***/
        one_answer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gridPane.add(new CheckBox("one answer"),2,0);
            }
        });

        multiple_answer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gridPane.add(new CheckBox("1"),2,0);
                gridPane.add(new CheckBox("2"),3,0);
                gridPane.add(new CheckBox("3"),4,0);
            }
        });


        question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //test.setText(question.getText());
            }
        });



        gridPane.add(test,0,1,1,1);
        //gridPane.add(next_button,1,0);
        //gridPane.add(prev_button,0,0);
        gridPane.add(one_answer,1,0);
        gridPane.add(multiple_answer,0,0);


        primaryStage.setScene(new Scene(gridPane,500,800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
