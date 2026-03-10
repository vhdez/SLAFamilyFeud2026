package com.example.slafamilyfeud2026;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Round1Controller {

    public AnchorPane pain;
    public ImageView backgroundImage;
    public Label team1Score;
    public Label team2Score;
    public Label roundScore;
    public GridPane Answers;
    public Label    Answer1;
    public Label	Answer2;
    public Label	Answer3;
    public Label	Answer4;
    public Label	Answer5;
    public Label	Answer6;
    public Label	Answer7;
    public Label	Answer8;
    public Label score1;
    public Label score2;
    public Label score3;
    public Label score4;
    public Label score5;
    public Label score6;
    public Label score7;
    public Label score8;

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

    public void setupHandlers() {
        Scene scene = pain.getScene();;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    processKeyEvent(keyEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void processKeyEvent(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            nextQuestion();
        } else if (event.getCode() == KeyCode.LEFT) {
            selectTeam(1);
        } else if (event.getCode() == KeyCode.RIGHT) {
            selectTeam(2);
        } else if (event.getCode() == KeyCode.SHIFT){
            FamilyFeudApp.Round2();
        } else if (event.getCode() == KeyCode.X) {
            wrongAnswer();
        } else if (event.getCode() == KeyCode.C) {
            closeWrongAnswer();
        }else {
            currentQuestion(event.getCode());
        }
        pain.requestFocus();

    }

    public void selectTeam(int newTeam) throws Exception {
        currentTeam = newTeam;
        if (currentTeam == 1) {
            team1Score.setStyle(
                    "-fx-border-color: blue;"
            );
            team2Score.setStyle(
                    "-fx-border-color: black;"
            );
        } else if (currentTeam == 2) {
            team1Score.setStyle(
                    "-fx-border-color: black;"
            );
            team2Score.setStyle(
                    "-fx-border-color: blue;"
            );
        }
    }

    public void currentQuestion(KeyCode keyCode) throws Exception {
        if (keyCode == KeyCode.DIGIT1) {
            if (!currentQuestion.getTheAnswers().get(0).getAnswered()) {
                currentQuestion.getTheAnswers().get(0).setAnswered(true);
                labelTransition(Answer1, currentQuestion.getTheAnswers().get(0).getAnAnswer());
                labelTransition(score1, currentQuestion.getTheAnswers().get(0).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(0).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT2) {
            if (!currentQuestion.getTheAnswers().get(1).getAnswered()) {
                currentQuestion.getTheAnswers().get(1).setAnswered(true);
                labelTransition(Answer2, currentQuestion.getTheAnswers().get(1).getAnAnswer());
                labelTransition(score2, currentQuestion.getTheAnswers().get(1).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(1).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT3) {
            if (!currentQuestion.getTheAnswers().get(2).getAnswered()) {
                currentQuestion.getTheAnswers().get(2).setAnswered(true);
                labelTransition(Answer3, currentQuestion.getTheAnswers().get(2).getAnAnswer());
                labelTransition(score3, currentQuestion.getTheAnswers().get(2).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(2).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT4) {
            if (!currentQuestion.getTheAnswers().get(3).getAnswered()) {
                currentQuestion.getTheAnswers().get(3).setAnswered(true);
                labelTransition(Answer4, currentQuestion.getTheAnswers().get(3).getAnAnswer());
                labelTransition(score4, currentQuestion.getTheAnswers().get(3).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(3).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT5) {
            if (!currentQuestion.getTheAnswers().get(4).getAnswered()) {
                currentQuestion.getTheAnswers().get(4).setAnswered(true);
                labelTransition(Answer5, currentQuestion.getTheAnswers().get(4).getAnAnswer());
                labelTransition(score5, currentQuestion.getTheAnswers().get(4).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(4).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT6) {
            if (!currentQuestion.getTheAnswers().get(5).getAnswered()) {
                currentQuestion.getTheAnswers().get(5).setAnswered(true);
                labelTransition(Answer6, currentQuestion.getTheAnswers().get(5).getAnAnswer());
                labelTransition(score6, currentQuestion.getTheAnswers().get(5).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(5).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT7) {
            if (!currentQuestion.getTheAnswers().get(6).getAnswered()) {
                currentQuestion.getTheAnswers().get(6).setAnswered(true);
                labelTransition(Answer7, currentQuestion.getTheAnswers().get(6).getAnAnswer());
                labelTransition(score7, currentQuestion.getTheAnswers().get(6).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(6).getItsScore();
            }
        } else if (keyCode == KeyCode.DIGIT8) {
            if (!currentQuestion.getTheAnswers().get(7).getAnswered()) {
                currentQuestion.getTheAnswers().get(7).setAnswered(true);
                labelTransition(Answer8, currentQuestion.getTheAnswers().get(7).getAnAnswer());
                labelTransition(score8, currentQuestion.getTheAnswers().get(7).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(7).getItsScore();
            }
        }
        roundScore.setText("Round: " + currentRoundScore);
    }

    public void labelTransition(Label label, String answer) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), label);

        // 2. Set scale factors (start at 1.0, shrink to 0.5)
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(0.5); // Shrink factor
        st.setToY(0.5);

        // 3. Make it reverse (grow back)
        label.setText(answer);
        st.setAutoReverse(true);
        st.setCycleCount(2); // One cycle down, one cycle up

        st.play();
    }

    public void nextQuestion() throws Exception {
        currentQuestionNum++;
        if (currentQuestionNum < Question.getAllTheQuestions().size()) {
            currentQuestion = Question.getAllTheQuestions().get(currentQuestionNum);
            Answer1.setText(String.valueOf(1));
            Answer2.setText(String.valueOf(2));
            Answer3.setText(String.valueOf(3));
            Answer4.setText(String.valueOf(4));
            Answer5.setText(String.valueOf(5));
            Answer6.setText(String.valueOf(6));
            Answer7.setText(String.valueOf(7));
            Answer8.setText(String.valueOf(8));
            score1.setText("");
            score2.setText("");
            score3.setText("");
            score4.setText("");
            score5.setText("");
            score6.setText("");
            score7.setText("");
            score8.setText("");
        }
        if (currentTeam == 1) {
            team1score += currentRoundScore;
        } else if (currentTeam == 2) {
            team2score += currentRoundScore;
        }
        currentRoundScore = 0;
        team1Score.setText("Team 1: " + team1score);
        team2Score.setText("Team 2: " + team2score);
        roundScore.setText("Round: " + currentRoundScore);
        currentTeam = 0;
        team1Score.setStyle(
                "-fx-border-color: black;"
        );
        team2Score.setStyle(
                "-fx-border-color: black;"
        );
        XsCount = 0;
    }

    public void switchToRound2(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.SHIFT) {
            FamilyFeudApp.Round2();
        }
    }

    public void wrongAnswer() {
        if (XsCount < 3) {
            XsCount++;
            // reveal Xs onscreen
        } else {
            XsCount = 0;
        }
    }

    public void closeWrongAnswer() {

    }
}