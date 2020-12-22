package sample;

import java.util.ArrayList;

public class Question {
    private String name;
    private int numberOfAnswers;
    private ArrayList<String> responses = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();

    public Question(String name, int numberOfAnswers, ArrayList<String> responses, ArrayList<String> answers) {
        this.name = name;
        this.numberOfAnswers = numberOfAnswers;
        this.responses = responses;
        this.answers = answers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    @Override
    public String toString() {
        return "Question{" +
                "name='" + name + '\'' +
                ", numberOfAnswers=" + numberOfAnswers +
                ", responses=" + responses +
                ", answers=" + answers +
                '}';
    }

    public void setResponses(ArrayList<String> responses) {
        this.responses = responses;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
