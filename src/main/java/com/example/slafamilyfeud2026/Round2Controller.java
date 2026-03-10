package com.example.slafamilyfeud2026;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.Array;

public class Round2Controller {
    public Label phaseLabel;
    public GridPane answersGrid;
    public Label roundTotal;
    public Label answer1;
    public Label answer2;
    public Label answer3;
    public Label answer4;
    public Label answer5;
    public Label answer6;
    public Label answer7;
    public Label answer8;
    public Label answer9;
    public Label answer10;
    public Label score1;
    public Label score2;
    public Label score3;
    public Label score4;
    public Label score5;
    public Label score6;
    public Label score7;
    public Label score8;
    public Label score9;
    public Label score10;

    Integer playerTurn;
    Question currentQuestion;
    ArrayList<Integer> selectedAnswers;
    ArrayList<Question> round2Questions;

    Label[] answerLabels;
    Label[] scoreLabels;

}

@FXML
public void initialize() throws Exception {



}