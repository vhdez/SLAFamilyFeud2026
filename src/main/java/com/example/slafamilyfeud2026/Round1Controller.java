package com.example.slafamilyfeud2026;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class Round1Controller {

    public ImageView backgroundImage;
    public Label team1Score;
    public Label team2Score;
    public Label roundScore;
    public GridPane Answers;
    public Label Answer1;
    public Label Answer2;
    public Label Answer3;
    public Label Answer4;
    public Label Answer5;
    public Label Answer6;
    public Label Answer7;
    public Label Answer8;

    private int team1score = 0;
    private int team2score = 0;
    private int currentTeam = 0;
    private int currentRoundScore = 0;
    private int XsCount = 0;
    private int currentQuestionNum = 0;
    private Question currentQuestion;

    public void initialize() throws Exception {
        Question.readQuestions();
        currentQuestion = Question.getAllTheQuestions().get(currentQuestionNum);
        System.out.println(currentQuestion.getTheQuestion());
    }

    public void processKeyEvent(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            nextQuestion();
        } else if (event.getCode() == KeyCode.LEFT) {
            selectTeam(1);
        } else if (event.getCode() == KeyCode.RIGHT) {
            selectTeam(2);
        } else if (event.getCode() == KeyCode.DOWN) {
            selectTeam(0);
        } else if (event.getCode() == KeyCode.UP) {
            selectTeam(3);
        } else {
            currentQuestion(event.getCode());
        }
    }

    public void selectTeam(int newTeam) throws Exception {
        currentTeam = newTeam;
        if (currentTeam == 0) {

        }
    }

    public void currentQuestion(KeyCode keyCode) throws Exception {

    }

    public void nextQuestion() throws Exception {
        currentQuestionNum++;
        currentQuestion = Question.getAllTheQuestions().get(currentQuestionNum);
        Answer1.setText(String.valueOf(1));
        Answer2.setText(String.valueOf(2));
        Answer3.setText(String.valueOf(3));
        Answer4.setText(String.valueOf(4));
        Answer5.setText(String.valueOf(5));
        Answer6.setText(String.valueOf(6));
        Answer7.setText(String.valueOf(7));
        Answer8.setText(String.valueOf(8));

    }

    public void switchToRound2(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.SHIFT) {
            FamilyFeudApp.Round2();
        }
    }
}