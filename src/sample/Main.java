package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    public int counter = 1;
    public int nr_check = 0;
    public int num_Questions = 1;
    public int row = 0;
    public int colum = 0;


    public ArrayList<Question> questionArrayList = new ArrayList<>();
    public ArrayList<String> answers = new ArrayList<>();
    public ArrayList<String> good_answers = new ArrayList<>();

    public ArrayList<TextField> responseTextFieldArray = new ArrayList<>();
    public ArrayList<CheckBox> responseCheckBoxArray = new ArrayList<>();
    public ArrayList<RadioButton> responseRadio = new ArrayList<>();


    Label question_label = new Label("Question no " + num_Questions);

    Button prev_question = new Button("Prev");
    Button next_question = new Button("Next");

    Label type_question = new Label("Type the question");

    TextField question_text = new TextField();

    Label type_answer = new Label("Type the answer");

    Label choices = new Label("No of choices");

    Button minus_question = new Button("-");
    Button plus_question = new Button("+");

    RadioButton single_answer = new RadioButton("Unique answer");
    RadioButton multiple_answer = new RadioButton("Multiple answers");

    TextField nr_choice = new TextField(String.valueOf(counter));

    Button save = new Button("Save question");
    Button gen_html = new Button("Generate HTML");
    Button clear_reponses = new Button("Clear reponse");

    Button open_test = new Button("Open Test");
    Button save_test = new Button("Save Test");
    Button exit = new Button("Exit");
    Button test = new Button("TEST");

    public GridPane Top_UI(GridPane gp) {
        gp.add(question_label, 0, 0);
        gp.add(prev_question, 0, 1);
        gp.add(next_question, 1, 1);

        return gp;
    }

    public GridPane Left_UI(GridPane gp) {
        gp.add(choices, 0, 0);
        gp.add(minus_question, 0, 1);
        gp.add(nr_choice, 1, 1);
        gp.add(plus_question, 2, 1);

        gp.add(single_answer, 0, 2);
        gp.add(multiple_answer, 0, 3);

        VBox vbox = new VBox();
        vbox.getChildren().add(save);
        vbox.getChildren().add(gen_html);
        vbox.getChildren().add(clear_reponses);

        gp.add(vbox,0,5);

        return gp;
    }

    public GridPane Right_UI(GridPane gp) {
        GridPane m_gird = new GridPane();
        gp.add(type_question, 0, 0);
        gp.add(question_text, 0, 1);

        multiple_answer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dinamicGridMulti(m_gird).getChildren().clear();
                dinamicGridSingle(m_gird).getChildren().clear();
                gp.add(dinamicGridMulti(m_gird), 0, 4);
            }
        });

        single_answer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dinamicGridSingle(m_gird).getChildren().clear();
                dinamicGridMulti(m_gird).getChildren().clear();
                gp.add(dinamicGridSingle(m_gird),0,4);

            }
        });

        clear_reponses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dinamicGridSingle(m_gird).getChildren().clear();
                dinamicGridMulti(m_gird).getChildren().clear();
                single_answer.setSelected(false);
                multiple_answer.setSelected(false);

            }
        });

        nr_choice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(single_answer.isSelected())
                {
                    dinamicGridSingle(m_gird).getChildren().clear();
                    dinamicGridMulti(m_gird).getChildren().clear();
                    gp.add(dinamicGridSingle(m_gird),0,4);
                }
                else if(multiple_answer.isSelected())
                {
                    dinamicGridMulti(m_gird).getChildren().clear();
                    dinamicGridSingle(m_gird).getChildren().clear();
                    gp.add(dinamicGridMulti(m_gird), 0, 4);
                }
            }
        });

        return gp;
    }

    public GridPane dinamicGridMulti(GridPane gridPane) {
        int aux_rep = Integer.parseInt(nr_choice.getText());
        if (aux_rep == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        } else {
            for (int i = 0; i < aux_rep; i++, row++) {
                colum = 0;
                CheckBox checkBox = new CheckBox(String.valueOf(i));
                TextField response1 = new TextField();
                gridPane.add(checkBox, colum, row);
                colum = colum + 1;
                gridPane.add(response1, colum, row);
                responseTextFieldArray.add(response1);
                responseCheckBoxArray.add(checkBox);
            }
        }
        return gridPane;
    }

    public GridPane dinamicGridSingle(GridPane gridPane)
    {
        final ToggleGroup check_reponse = new ToggleGroup();
        int aux_rep = Integer.parseInt(nr_choice.getText());
        if (aux_rep == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        } else {
            for (int i = 0; i < aux_rep; i++, row++) {
                colum = 0;
                RadioButton radioReponse = new RadioButton(String.valueOf(i));
                TextField response1 = new TextField();
                gridPane.add(radioReponse, colum, row);
                colum = colum + 1;
                gridPane.add(response1, colum, row);
                radioReponse.setToggleGroup(check_reponse);
                responseTextFieldArray.add(response1);
                responseRadio.add(radioReponse);
            }
        }
        return gridPane;
    }

    public HBox Button_UI(HBox hbox) {
        hbox.getChildren().add(open_test);
        hbox.getChildren().add(save_test);
        hbox.getChildren().add(exit);

        return hbox;
    }

    public void transformArrayTextBox()
    {
        for(TextField tf : responseTextFieldArray)
        {
            answers.add(tf.getText());
        }
    }

    public void transformArrayCheckBox()
    {
        for(int i=0;i<responseRadio.size();i++)
        {
            if(responseRadio.get(i).isSelected())
            {
                good_answers.add(responseTextFieldArray.get(i).getText());
            }
        }
    }

    public void transformArrayRadio()
    {
        for(int i=0;i<responseCheckBoxArray.size();i++)
        {
            if(responseCheckBoxArray.get(i).isSelected())
            {
                good_answers.add(responseTextFieldArray.get(i).getText());
            }
        }
    }

    public void UI(Stage scene) {

        FileChooser fileChooser = new FileChooser();

        ToggleGroup group = new ToggleGroup();
        single_answer.setToggleGroup(group);
        multiple_answer.setToggleGroup(group);

        next_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                num_Questions++;
                question_text.clear();
                nr_choice.clear();
            }
        });

        plus_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                counter = counter + 1;
                nr_choice.setText(String.valueOf(counter));
            }
        });

        minus_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (counter <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.show();
                } else {
                    counter = counter - 1;
                    nr_choice.setText(String.valueOf(counter));
                }
            }
        });

        GridPane gridPane = new GridPane();
        GridPane gridPane1 = new GridPane();
        GridPane gridPane2 = new GridPane();
        GridPane gridPane3 = new GridPane();
        BorderPane borderPane = new BorderPane();

        borderPane.setBottom(Button_UI(new HBox()));
        borderPane.setTop(Top_UI(gridPane1));
        borderPane.setRight(Left_UI(gridPane2));
        borderPane.setLeft(Right_UI(gridPane3));


        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.equals(KeyCode.ENTER)) {

            } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                System.exit(0);
            }
        });

        transformArrayCheckBox();
        transformArrayTextBox();
        transformArrayRadio();

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Question question = new Question(question_text.getText(),good_answers.size(),answers,good_answers);
                System.out.println(question.toString());
            }
        });

        prev_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gridPane.getChildren().clear();
            }
        });

        scene.setScene(new Scene(borderPane, 1280, 720));
        scene.show();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        UI(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
