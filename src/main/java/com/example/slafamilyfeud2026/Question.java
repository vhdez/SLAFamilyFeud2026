package com.example.slafamilyfeud2026;

import java.util.ArrayList;

public class Question {
    public int questionNumber;
    public String theQuestion;
    ArrayList<Answer> theAnswers;
    static ArrayList<Question> allTheQuestions;

    public Question(int questionNumber, String theQuestion, ArrayList<Answer> theAnswers) {
        this.questionNumber = questionNumber;
        this.theQuestion = theQuestion;
        this.theAnswers = theAnswers;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    public ArrayList<Answer> getTheAnswers() {
        return theAnswers;
    }

    public void setTheAnswers(ArrayList<Answer> theAnswers) {
        this.theAnswers = theAnswers;
    }

    public static ArrayList<Question> getAllTheQuestions() {
        return allTheQuestions;
    }

    public static void setAllTheQuestions(ArrayList<Question> allTheQuestions) {
        Question.allTheQuestions = allTheQuestions;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionNumber=" + questionNumber +
                ", theQuestion='" + theQuestion + '\'' +
                ", theAnswers=" + theAnswers +
                '}';
    }

    public static void readQuestions(){


    }
}
