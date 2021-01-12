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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public boolean question_saved = false;
    public int counter = 1;
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
    Button update_reponses = new Button("Update");
    Button clear_reponses = new Button("Clear reponse");

    Button open_test = new Button("Open Test");
    Button save_test = new Button("Save Test");
    Button exit = new Button("Exit");


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
        //vbox.getChildren().add(update_reponses);
        vbox.getChildren().add(clear_reponses);

        gp.add(vbox, 0, 5);

        return gp;
    }

    GridPane m_gird = new GridPane();
    GridPane gp = new GridPane();

    public GridPane Right_UI() {

        gp.add(type_question, 0, 0);
        gp.add(question_text, 0, 1);
        gp.add(type_answer, 0, 2);

        multiple_answer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                responseCheckBoxArray.clear();
                responseRadio.clear();
                responseTextFieldArray.clear();
                if (m_gird.getChildren() != null) {
                    m_gird.getChildren().clear();
                    responseTextFieldArray.clear();
                }
                if (m_gird.getChildren() != null) {
                    m_gird.getChildren().clear();
                    responseTextFieldArray.clear();
                }
                responseTextFieldArray.clear();
                try {
                    gp.add(dinamicGridMulti(m_gird), 0, 4);
                } catch (Exception e) {
                }
            }
        });

        single_answer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                responseCheckBoxArray.clear();
                responseRadio.clear();
                responseTextFieldArray.clear();
                if (m_gird.getChildren() != null) {
                    m_gird.getChildren().clear();
                    responseTextFieldArray.clear();
                }
                if (m_gird.getChildren() != null) {
                    m_gird.getChildren().clear();
                    responseTextFieldArray.clear();
                }
                responseTextFieldArray.clear();
                try {
                    gp.add(dinamicGridSingle(m_gird), 0, 4);
                } catch (Exception e) {
                }
            }
        });

        clear_reponses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (m_gird.getChildren() != null) {
                    m_gird.getChildren().clear();
                    responseTextFieldArray.clear();
                }
                if (m_gird.getChildren() != null) {
                    m_gird.getChildren().clear();
                    responseTextFieldArray.clear();
                }
                single_answer.setSelected(false);
                multiple_answer.setSelected(false);
            }
        });

        update_reponses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                question_saved = false;
                System.out.println(num_Questions);
                try {
                    questionArrayList.remove(num_Questions - 1);
                } catch (Exception e) {
                }
                m_gird.getChildren().clear();
                responseCheckBoxArray.clear();
                responseRadio.clear();
                responseTextFieldArray.clear();
                answers.clear();
                good_answers.clear();
                if (single_answer.isSelected()) {
                    try {
                        gp.add(dinamicGridSingle(m_gird), 0, 4);
                    } catch (Exception e) {

                    }
                }
                if (multiple_answer.isSelected()) {
                    try {
                        gp.add(dinamicGridMulti(m_gird), 0, 4);
                    } catch (Exception e) {
                    }
                }
                saveQuestion();
            }
        });

/*        Button t = new Button("Test");

        t.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(responseTextFieldArray);
            }
        });

        gp.add(t, 0, 10);*/

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
                CheckBox checkBox1 = new CheckBox(String.valueOf(i));
                TextField response2 = new TextField();
                gridPane.add(checkBox1, colum, row);
                colum = colum + 1;
                gridPane.add(response2, colum, row);
                responseTextFieldArray.add(response2);
                responseCheckBoxArray.add(checkBox1);
            }
        }
        return gridPane;
    }

    public GridPane dinamicGridSingle(GridPane gridPane) {
        ToggleGroup check_reponse = new ToggleGroup();
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

    public void transformArrayTextBox() {
        for (TextField tf : responseTextFieldArray) {
            answers.add(tf.getText());
        }
    }

    public void transformArrayRadio() {
        for (int i = 0; i < responseRadio.size(); i++) {
            if (responseRadio.get(i).isSelected()) {
                good_answers.add(responseTextFieldArray.get(i).getText());
            }
        }
    }

    public void transformArrayCheckBox() {
        for (int i = 0; i < responseCheckBoxArray.size(); i++) {
            if (responseCheckBoxArray.get(i).isSelected()) {
                good_answers.add(responseTextFieldArray.get(i).getText());
            }
        }
    }

    public void clearScene() {
        responseRadio.clear();
        responseCheckBoxArray.clear();
        responseTextFieldArray.clear();
        question_text.clear();
        counter = 1;
        nr_choice.setText(String.valueOf(counter));
        single_answer.setSelected(false);
        multiple_answer.setSelected(false);
        if (m_gird.getChildren() != null) {
            m_gird.getChildren().clear();
            responseTextFieldArray.clear();
        }
        if (m_gird.getChildren() != null) {
            m_gird.getChildren().clear();
            responseTextFieldArray.clear();
        }
        single_answer.setSelected(false);
        multiple_answer.setSelected(false);
    }

    BorderPane borderPane = new BorderPane();

    public BorderPane setUI() {
        borderPane.setBottom(Button_UI(new HBox()));
        borderPane.setTop(Top_UI(new GridPane()));
        borderPane.setRight(Left_UI(new GridPane()));
        borderPane.setLeft(Right_UI());

        ToggleGroup group = new ToggleGroup();
        single_answer.setToggleGroup(group);
        multiple_answer.setToggleGroup(group);
        return borderPane;
    }

    public void UI(Stage scene) {
        setUI();
        final Scene[] var = {new Scene(borderPane, 670, 550)};

        plus_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                counter = counter + 1;
                nr_choice.setText(String.valueOf(counter));
            }
        });

        minus_question.setOnAction(actionEvent -> {
            if (counter <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            } else {
                counter = counter - 1;
                nr_choice.setText(String.valueOf(counter));
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    txtSaveQuiz(new Quiz(questionArrayList));
                } catch (IOException e) {
                }
            } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                System.exit(0);
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveQuestion();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Question saved");
                alert.show();
            }
        });


        next_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                num_Questions++;
                question_label.setText("Question no " + num_Questions);
                clearScene();
                popUI();
            }
        });

        prev_question.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (num_Questions <= 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.show();
                } else {
                    num_Questions = num_Questions - 1;
                    question_label.setText("Question no " + num_Questions);
                    popUI();
                }
            }
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        open_test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    File selectFile = fileChooser.showOpenDialog(scene);
                    openQuiz(selectFile);
                } catch (IOException e) {
                }
            }
        });
        var[num_Questions-1].getStylesheets().add("style.css");
        scene.setScene(var[num_Questions - 1]);
        scene.show();
    }

    public void actionButtons() {
        save_test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    txtSaveQuiz(new Quiz(questionArrayList));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Quiz saved");
                    alert.show();
                } catch (IOException e) {
                }
            }
        });

    }

    public void txtSaveQuiz(Quiz quiz) throws IOException {
        quiz = new Quiz(questionArrayList);
        File save_quiz = new File("Quiz.txt");
        quiz.writeInFile(save_quiz, questionArrayList);
        questionArrayList = quiz.readFile(save_quiz);
    }

    public void openQuiz(File save_quiz) throws IOException {
        Quiz quiz = new Quiz(questionArrayList);
        save_quiz = new File("2.txt");
        questionArrayList = quiz.readFile(save_quiz);
        try {
            gp.add(m_gird, 0, 4);
        } catch (Exception e) {
        }
        popUI();
    }

    public void popUI() {
        try {
            clearScene();
            Question question = questionArrayList.get(num_Questions - 1);
            System.out.println(num_Questions - 1);
            System.out.println(question);
            question_text.setText(question.getName());
            nr_choice.setText(String.valueOf(question.getNrReponses()));
            gen_html.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        generateHTML();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (question.getNumberOfAnswers() > 1) {
                multiple_answer.setSelected(true);
                //m_gird.getChildren().clear();
                dinamicGridMulti(m_gird);
                int i = 0;
                for (TextField tf : responseTextFieldArray) {
                    tf.setText(question.getResponses().get(i));
                    i++;
                }
                for (int t = 0; t < question.getResponses().size(); t++) {
                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        if (question.getResponses().get(t).equals(question.getAnswers().get(j))) {
                            responseCheckBoxArray.get(t).setSelected(true);
                        }
                    }
                }
            } else if (question.getNumberOfAnswers() <= 1) {
                single_answer.setSelected(true);
                // m_gird.getChildren().clear();
                dinamicGridSingle(m_gird);
                int i = 0;
                for (TextField tf : responseTextFieldArray) {
                    tf.setText(question.getResponses().get(i));
                    i++;
                }
                for (int t = 0; t < question.getResponses().size(); t++) {
                    for (int j = 0; j < question.getAnswers().size(); j++) {
                        if (question.getResponses().get(t).equals(question.getAnswers().get(j))) {
                            responseRadio.get(t).setSelected(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void saveQuestion() {
        transformArrayCheckBox();
        transformArrayTextBox();
        transformArrayRadio();
        ArrayList<String> temp_goodAns = new ArrayList<>(good_answers);
        ArrayList<String> temp_answers = new ArrayList<>(answers);
        Question question = new Question(question_text.getText(), temp_goodAns.size(), temp_answers, temp_goodAns);
        try {
            questionArrayList.remove(num_Questions - 1);
        } catch (Exception e) {
        }
        questionArrayList.add(num_Questions - 1, question);
        System.out.println(questionArrayList);
        answers.clear();
        good_answers.clear();
        question_saved = true;
        responseRadio.clear();
        responseCheckBoxArray.clear();
        responseTextFieldArray.clear();
    }

    public void generateHTML() throws IOException {
        StringBuffer sb = new StringBuffer();
        File html = new File("4.html");
        HTML_gen gen = new HTML_gen(html,sb);
        gen.startDoc(sb);

        for(Question question : questionArrayList)
        {
            gen.appendTag(sb,"h1","Question :\n" + question.getName());
            gen.appendTag(sb,"h3","Answers:");
            for(String str : question.getResponses())
            {
                gen.appendTag(sb,"p",str);
            }
            gen.appendTag(sb,"h3","Correct answers:");
            for(String str: question.getAnswers())
            {
                gen.appendTag(sb,"p",str);
            }
        }
        gen.finishDoc(sb);
        gen.createHTMLFile(sb,html);
    }
   @Override
    public void start(Stage primaryStage) throws Exception {
        actionButtons();
        UI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
