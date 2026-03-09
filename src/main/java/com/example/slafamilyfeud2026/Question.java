package com.example.slafamilyfeud2026;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static void readQuestions() throws Exception{
        File myData = new File("src/main/java/com/example/slafamilyfeud2026/FamilyFeudInputData");
        Scanner myReader = new Scanner(myData);
        allTheQuestions = new ArrayList<Question>();
        int currentQuestion = 0;
        while (myReader.hasNextLine()){
            String dataLine = myReader.nextLine();
            Scanner lineScanner = new Scanner(dataLine);
            lineScanner.useDelimiter("\t");
            currentQuestion+=1;
            String theQuestion = lineScanner.next();

            ArrayList<Answer> answers = new ArrayList<>();

            while(lineScanner.hasNext()) {
                Answer newAnswer = new Answer("",0,false);
                newAnswer.anAnswer=lineScanner.next();
                newAnswer.itsScore=lineScanner.nextInt();
                answers.add(newAnswer);
                System.out.println(newAnswer);
            }

            Question newQuestion = new Question(currentQuestion,theQuestion,answers);
            System.out.println(newQuestion.toString());
            allTheQuestions.add(newQuestion);

        }
    }
}
