package sample;

import java.io.*;
import java.util.ArrayList;

public class Quiz {

    private int num_questions;
    private ArrayList<Question> questionList = new ArrayList<>();

    public Quiz(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public void writeInFile(File file, ArrayList<Question> questionList) throws IOException {
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for(int i=0;i<questionList.size();i++)
        {
            bufferedWriter.write(questionList.get(i).getName() + "/" + questionList.get(i).getNumberOfAnswers() + "/" + questionList.get(i).getResponses() + "/" + questionList.get(i).getAnswers());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public ArrayList<Question> readFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> test_array = new ArrayList<>();

        String lines;
        while ((lines = bufferedReader.readLine()) != null)
        {
            String[] line = lines.split("/");
            line[2]=line[2].replaceAll("[\\[\\]]","");
            line[2]=line[2].replaceAll(" ","");
            System.out.println(line[2]);
            String[] reponse = line[2].split(",");
            line[3] = line[3].replaceAll("[\\[\\]]","");
            line[3]=line[3].replaceAll(" ","");
            String[] answers = line[3].split(",");

            ArrayList<String> reponseArray = new ArrayList<>();
            ArrayList<String> answersArray = new ArrayList<>();

            for(int j=0;j<reponse.length;j++)
            {
                reponseArray.add(reponse[j]);
                System.out.println(reponse[j]);
            }

            for(int j=0;j<answers.length;j++)
            {
                answersArray.add(answers[j]);
                System.out.println(answers[j]);
            }

            Question question = new Question(line[0],Integer.parseInt(line[1]),reponseArray,answersArray);
            questionList.add(question);

        }

        return questionList;
    }

}
