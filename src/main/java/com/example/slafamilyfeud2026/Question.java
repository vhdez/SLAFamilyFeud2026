package com.example.slafamilyfeud2026;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Question {
    private int questionNumber;
    private String theQuestion;
    private ArrayList<Answer> theAnswers;
    private Boolean beenAskedAlready;
    static ArrayList<Question> allTheQuestions = new ArrayList<Question>();
    static ArrayList<Question> round1Questions = new ArrayList<Question>();
    static ArrayList<Question> round2Questions = new ArrayList<Question>();

    public Question(int questionNumber, String theQuestion, ArrayList<Answer> theAnswers) {
        this.questionNumber = questionNumber;
        this.theQuestion = theQuestion;
        this.theAnswers = theAnswers;
        this.beenAskedAlready = false;
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

    public Boolean getBeenAskedAlready() {
        return beenAskedAlready;
    }

    public void setBeenAskedAlready(Boolean beenAskedAlready) {
        this.beenAskedAlready = beenAskedAlready;
    }

    public static ArrayList<Question> getAllTheQuestions() {
        return allTheQuestions;
    }

    public static void setAllTheQuestions(ArrayList<Question> allTheQuestions) {
        Question.allTheQuestions = allTheQuestions;
    }


    public static void setTest1Questions() {
        for (int i = 0; i < 4; i++) {
            Question.round1Questions.add(allTheQuestions.get(i));
        }
    }

    public static void setTest2Questions() {
        for (int i = 4; i < 9; i++) {
            Question.round2Questions.add(allTheQuestions.get(i));
        }
    }

    public static ArrayList<Question> getRound1Questions() {
        return round1Questions;
    }

    public static void setRound1Questions(ArrayList<Integer> questionNumbers) {
        for (Integer questionNumber : questionNumbers) {
            Question.round1Questions.add(allTheQuestions.get(questionNumber-1));
        }
    }

    public static ArrayList<Question> getRound2Questions() {
        return round2Questions;
    }

    public static void setRound2Questions(ArrayList<Integer> questionNumbers) {
        for (Integer questionNumber : questionNumbers) {
            Question.round2Questions.add(allTheQuestions.get(questionNumber-1));
        }
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
            //System.out.println("Question READ: " + theQuestion);

            ArrayList<Answer> answers = new ArrayList<>();

            Question newQuestion = new Question(currentQuestion,theQuestion,answers);
            //System.out.println("READ QUESTION: " + newQuestion.toString());

            while(lineScanner.hasNext()) {
                Answer newAnswer = new Answer("",0,false);
                String answer = lineScanner.next();
                //System.out.println("Answer READ: " + answer);
                newAnswer.setAnAnswer(answer);
                Integer score = lineScanner.nextInt();
                //System.out.println("Score READ: " + score);
                newAnswer.setItsScore(score);
                answers.add(newAnswer);
                //System.out.println("READ ANSWER: " + newAnswer);
            }
            answers.sort(Comparator.comparing(Answer::getItsScore).reversed());

            allTheQuestions.add(newQuestion);
        }
    }
}
