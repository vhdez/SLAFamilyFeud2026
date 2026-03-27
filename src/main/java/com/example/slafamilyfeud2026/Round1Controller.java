package com.example.slafamilyfeud2026;

import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Round1Controller {

    public AnchorPane pain;
    public StackPane stackPane;
    public ImageView backgroundImage;
    public ImageView x1;
    public ImageView x2;
    public ImageView x3;

    public Label team1Score;
    public Label team2Score;
    public Label team1NameLabel;
    public Label team2NameLabel;
    public Label roundScore;
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
    public Media wrongNoise;
    public Media correctNoise;

    private int team1score = 0;
    private int team2score = 0;
    private int currentTeam = 0;
    private int currentRoundScore = 0;
    private int XsCount = 0;
    private boolean stealRound = false;
    private int currentQuestionNum = -1;
    private Question currentQuestion;
    private final ArrayList<Integer> questionNumbers =  new ArrayList<>();

    private boolean isRevealMode = false;

    public void initialize() throws Exception {
        pain.setPrefSize(1066, 600);

        NumberBinding scaleBinding = Bindings.min(stackPane.widthProperty().divide(1066), stackPane.heightProperty().divide(600));
        stackPane.scaleXProperty().bind(scaleBinding);
        stackPane.scaleYProperty().bind(scaleBinding);


        backgroundImage.setImage(new Image(new FileInputStream("src/round1BG.png")));
        File x1File = new File("src/FamilyFeudX.png");
        FileInputStream img1Input = new FileInputStream(x1File);
        Image x1Image = new Image(img1Input);
        x1.setImage(x1Image);
        File x2File = new File("src/FamilyFeudX(2).png");
        FileInputStream img2Input = new FileInputStream(x2File);
        Image x2Image = new Image(img2Input);
        x2.setImage(x2Image);
        File x3File = new File("src/FamilyFeudX3.png");
        FileInputStream img3Input = new FileInputStream(x3File);
        Image x3Image = new Image(img3Input);
        x3.setImage(x3Image);

        Question.readQuestions();

        Question.setTest1Questions(); // Test Set
        //questionNumbers.addAll(Arrays.asList(6, 8, 12, 1)); // Use this during the actual game, change based on question numbers
        //Question.setRound1Questions(questionNumbers);

        this.nextQuestion();
        System.out.println(Question.getAllTheQuestions().size() + " Questions Read");
        this.displayInstructions();
        this.displayCurrentQuestionAnswers();

        String soundPath1 = Paths.get("src/correct-answer-ff.mp3").toUri().toString();
        correctNoise = new Media(soundPath1);
        String soundPath2 = Paths.get("src/wrong-answer-sound-effect.mp3").toUri().toString();
        wrongNoise = new Media(soundPath2);
    }

    public void displayInstructions() {
        System.out.println("Round 1 INSTRUCTIONS: Press key...");
        System.out.println("     ANSWER   keys: Key #1 - 8 to choose that answer");
        System.out.println("     WRONG    keys: X for Wrong Answer; C to Close Wrong Answer");
        System.out.println("     QUESTION key : ENTER for Next Question; D for display Question/Answers");
        System.out.println("     TEAM     keys: LEFT for Team 1; RIGHT for Team 2");
        System.out.println("     ROUND    key : SHIFT for Round 2");
    }

    public void displayCurrentQuestionAnswers() {
        System.out.println("QUESTION: " + currentQuestion.getTheQuestion());
        int i = 1;
        System.out.print("  ANSWERS:");
        for (Answer answer : currentQuestion.getTheAnswers()) {
            System.out.print("  #" + i + " " + answer.getAnAnswer());
            i = i + 1;
        }
        System.out.print("\n");
    }

    public void setupHandlers() {
        Scene scene = pain.getScene();
        scene.setOnKeyPressed(keyEvent -> {
            try {
                processKeyEvent(keyEvent);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        });
    }

    public void processKeyEvent(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            nextQuestion();
            System.out.print(" ENTER ");
            this.displayInstructions();
            this.displayCurrentQuestionAnswers();
        } else if (event.getCode() == KeyCode.D) {
            displayCurrentQuestionAnswers();
        } else if (event.getCode() == KeyCode.LEFT) {
            selectTeam(1);
            System.out.print(" LEFT ");
        } else if (event.getCode() == KeyCode.RIGHT) {
            selectTeam(2);
            System.out.print(" RIGHT ");
        } else if (event.getCode() == KeyCode.SHIFT){
            System.out.print(" SHIFT ");
            FamilyFeudApp.Round2();
        } else if (event.getCode() == KeyCode.X) {
            wrongAnswer();
            System.out.print(" X ");
        } else if (event.getCode() == KeyCode.C) {
            closeWrongAnswer();
            System.out.print(" C ");
        } else if (event.getCode() == KeyCode.H){
            this.displayInstructions();
        } else if (event.getCode() == KeyCode.DIGIT0) {
            isRevealMode = !isRevealMode;
        } else if (event.getCode() == KeyCode.Z) {
            x1.setVisible(true);
            playSound(wrongNoise);
            System.out.print(" Z (BUZZER WRONG) ");
        } else {
            if (isRevealMode) {
                revealAnswers(event.getCode());
            } else {
                currentQuestion(event.getCode());
            }
        }
    }



    public void selectTeam(int newTeam) {
        currentTeam = newTeam;
        if (currentTeam == 1) {
            team1Score.setStyle(
                    "-fx-background-color: lightblue;" + "-fx-background-radius: 15px;"
            );
            team2Score.setStyle(
                    "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
            );
            team1NameLabel.setStyle(
                    "-fx-background-color: lightblue;" + "-fx-background-radius: 15px;"
            );
            team2NameLabel.setStyle(
                    "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
            );
        } else if (currentTeam == 2) {
            team1Score.setStyle(
                    "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
            );
            team2Score.setStyle(
                    "-fx-background-color: lightblue;" + "-fx-background-radius: 15px;"
            );
            team1NameLabel.setStyle(
                    "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
            );
            team2NameLabel.setStyle(
                    "-fx-background-color: lightblue;" + "-fx-background-radius: 15px;"
            );
        }
    }

    public void currentQuestion(KeyCode keyCode) {
        if (keyCode == KeyCode.DIGIT1) {
            if (currentQuestion.getTheAnswers().isEmpty()) {
                return;
            }
            if (!currentQuestion.getTheAnswers().getFirst().getAnswered()) {
                currentQuestion.getTheAnswers().getFirst().setAnswered(true);
                labelTransition(Answer1, currentQuestion.getTheAnswers().getFirst().getAnAnswer());
                labelTransition(score1, currentQuestion.getTheAnswers().getFirst().getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().getFirst().getItsScore();
                System.out.print(" 1 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT2) {
            if (currentQuestion.getTheAnswers().size() < 2) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(1).getAnswered()) {
                currentQuestion.getTheAnswers().get(1).setAnswered(true);
                labelTransition(Answer2, currentQuestion.getTheAnswers().get(1).getAnAnswer());
                labelTransition(score2, currentQuestion.getTheAnswers().get(1).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(1).getItsScore();
                System.out.print(" 2 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT3) {
            if (currentQuestion.getTheAnswers().size() < 3) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(2).getAnswered()) {
                currentQuestion.getTheAnswers().get(2).setAnswered(true);
                labelTransition(Answer3, currentQuestion.getTheAnswers().get(2).getAnAnswer());
                labelTransition(score3, currentQuestion.getTheAnswers().get(2).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(2).getItsScore();
                System.out.print(" 3 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT4) {
            if (currentQuestion.getTheAnswers().size() < 4) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(3).getAnswered()) {
                currentQuestion.getTheAnswers().get(3).setAnswered(true);
                labelTransition(Answer4, currentQuestion.getTheAnswers().get(3).getAnAnswer());
                labelTransition(score4, currentQuestion.getTheAnswers().get(3).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(3).getItsScore();
                System.out.print(" 4 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT5) {
            if (currentQuestion.getTheAnswers().size() < 5) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(4).getAnswered()) {
                currentQuestion.getTheAnswers().get(4).setAnswered(true);
                labelTransition(Answer5, currentQuestion.getTheAnswers().get(4).getAnAnswer());
                labelTransition(score5, currentQuestion.getTheAnswers().get(4).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(4).getItsScore();
                System.out.print(" 5 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT6) {
            if (currentQuestion.getTheAnswers().size() < 6) {
            return;
            }
            if (!currentQuestion.getTheAnswers().get(5).getAnswered()) {
                currentQuestion.getTheAnswers().get(5).setAnswered(true);
                labelTransition(Answer6, currentQuestion.getTheAnswers().get(5).getAnAnswer());
                labelTransition(score6, currentQuestion.getTheAnswers().get(5).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(5).getItsScore();
                System.out.print(" 6 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT7) {
            if (currentQuestion.getTheAnswers().size() < 7) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(6).getAnswered()) {
                currentQuestion.getTheAnswers().get(6).setAnswered(true);
                labelTransition(Answer7, currentQuestion.getTheAnswers().get(6).getAnAnswer());
                labelTransition(score7, currentQuestion.getTheAnswers().get(6).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(6).getItsScore();
                System.out.print(" 7 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT8) {
            if (currentQuestion.getTheAnswers().size() < 8) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(7).getAnswered()) {
                currentQuestion.getTheAnswers().get(7).setAnswered(true);
                labelTransition(Answer8, currentQuestion.getTheAnswers().get(7).getAnAnswer());
                labelTransition(score8, currentQuestion.getTheAnswers().get(7).getItsScore().toString());
                currentRoundScore += currentQuestion.getTheAnswers().get(7).getItsScore();
                System.out.print(" 8 ");
                playSound(correctNoise);
            }
        }
        roundScore.setText(String.valueOf(currentRoundScore));
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
        int answerLength = answer.length();
        int excessLength = answerLength - 9;
        if (excessLength > 0) {
            label.setFont(new Font("Times New Roman", 60* Math.min ((8f/(answerLength)),1)));
        }
        st.setAutoReverse(true);
        st.setCycleCount(2); // One cycle down, one cycle up

        st.play();
    }

    public void nextQuestion() {
        currentQuestionNum++;
        Answer1.setText("");
        Answer1.setFont(new Font("Times New Roman", 50));
        Answer2.setText("");
        Answer2.setFont(new Font("Times New Roman", 50));
        Answer3.setText("");
        Answer3.setFont(new Font("Times New Roman", 50));
        Answer4.setText("");
        Answer4.setFont(new Font("Times New Roman", 50));
        Answer5.setText("");
        Answer5.setFont(new Font("Times New Roman", 50));
        Answer6.setText("");
        Answer6.setFont(new Font("Times New Roman", 50));
        Answer7.setText("");
        Answer7.setFont(new Font("Times New Roman", 50));
        Answer8.setText("");
        Answer8.setFont(new Font("Times New Roman", 50));
        score1.setText("");
        score2.setText("");
        score3.setText("");
        score4.setText("");
        score5.setText("");
        score6.setText("");
        score7.setText("");
        score8.setText("");
        if (currentQuestionNum < Question.getRound1Questions().size()) {
            currentQuestion = Question.getRound1Questions().get(currentQuestionNum);
            if (!currentQuestion.getTheAnswers().isEmpty()) {
                Answer1.setText(String.valueOf(1));
            }
            if (currentQuestion.getTheAnswers().size() >= 2) {
                Answer2.setText(String.valueOf(2));
            }
            if (currentQuestion.getTheAnswers().size() >= 3) {
                Answer3.setText(String.valueOf(3));
            }
            if (currentQuestion.getTheAnswers().size() >= 4) {
                Answer4.setText(String.valueOf(4));
            }
            if (currentQuestion.getTheAnswers().size() >= 5) {
                Answer5.setText(String.valueOf(5));
            }
            if (currentQuestion.getTheAnswers().size() >= 6) {
                Answer6.setText(String.valueOf(6));
            }
            if (currentQuestion.getTheAnswers().size() >= 7) {
                Answer7.setText(String.valueOf(7));
            }
            if (currentQuestion.getTheAnswers().size() >= 8) {
                Answer8.setText(String.valueOf(8));
            }
        }
        rewardPoint();
        currentTeam = 0;
        team1Score.setStyle(
                "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
        );
        team2Score.setStyle(
                "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
        );
        team1NameLabel.setStyle(
                "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
        );
        team2NameLabel.setStyle(
                "-fx-background-color: blue;" + "-fx-background-radius: 15px;"
        );
        XsCount = 0;
        stealRound = false;
        isRevealMode = false;
    }



    public void wrongAnswer() {
        if (stealRound) {
            XsCount++;
            if (XsCount == 1) {
                x1.setVisible(true);
                if (currentTeam == 1) {
                    selectTeam(2);
                } else {
                    selectTeam(1);
                }

                rewardPoint();
            }
        } else {
            XsCount++;
            if (XsCount == 1) {
                x1.setVisible(true);
            } else if (XsCount == 2) {
                x2.setVisible(true);
            } else {
                x3.setVisible(true);
                XsCount = 0;
                stealRound = true;
                if (currentTeam == 1) {
                    selectTeam(2);
                } else {
                    selectTeam(1);
                }
            }

            System.out.println("  X #" + XsCount);

            if (XsCount == 3) {
                System.out.println("\n 3 STRIKES! STEAL ATTEMPT ");
                System.out.println("  Press [S] if steal is CORRECT");
                System.out.println("  Press [C] if steal is WRONG");
            }
        }
        playSound(wrongNoise);
    }

    public void closeWrongAnswer() {
        x1.setVisible(false);
        x2.setVisible(false);
        x3.setVisible(false);
    }

    public void playSound(Media sound) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Could not play sound: " + e.getMessage());
        }
    }

    public void rewardPoint() {
        if (currentTeam == 1) {
            team1score += currentRoundScore;
        } else if (currentTeam == 2) {
            team2score += currentRoundScore;
        }
        currentRoundScore = 0;
        team1Score.setText(String.valueOf(team1score));
        team2Score.setText(String.valueOf(team2score));
        roundScore.setText(String.valueOf(currentRoundScore));
    }

    public void revealAnswers(KeyCode keyCode) {
        if (keyCode == KeyCode.DIGIT1) {
            if (currentQuestion.getTheAnswers().isEmpty()) {
                return;
            }
            if (!currentQuestion.getTheAnswers().getFirst().getAnswered()) {
                currentQuestion.getTheAnswers().getFirst().setAnswered(true);
                labelTransition(Answer1, currentQuestion.getTheAnswers().getFirst().getAnAnswer());
                labelTransition(score1, currentQuestion.getTheAnswers().getFirst().getItsScore().toString());
                System.out.print(" 1 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT2) {
            if (currentQuestion.getTheAnswers().size() < 2) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(1).getAnswered()) {
                currentQuestion.getTheAnswers().get(1).setAnswered(true);
                labelTransition(Answer2, currentQuestion.getTheAnswers().get(1).getAnAnswer());
                labelTransition(score2, currentQuestion.getTheAnswers().get(1).getItsScore().toString());
                System.out.print(" 2 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT3) {
            if (currentQuestion.getTheAnswers().size() < 3) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(2).getAnswered()) {
                currentQuestion.getTheAnswers().get(2).setAnswered(true);
                labelTransition(Answer3, currentQuestion.getTheAnswers().get(2).getAnAnswer());
                labelTransition(score3, currentQuestion.getTheAnswers().get(2).getItsScore().toString());
                System.out.print(" 3 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT4) {
            if (currentQuestion.getTheAnswers().size() < 4) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(3).getAnswered()) {
                currentQuestion.getTheAnswers().get(3).setAnswered(true);
                labelTransition(Answer4, currentQuestion.getTheAnswers().get(3).getAnAnswer());
                labelTransition(score4, currentQuestion.getTheAnswers().get(3).getItsScore().toString());
                System.out.print(" 4 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT5) {
            if (currentQuestion.getTheAnswers().size() < 5) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(4).getAnswered()) {
                currentQuestion.getTheAnswers().get(4).setAnswered(true);
                labelTransition(Answer5, currentQuestion.getTheAnswers().get(4).getAnAnswer());
                labelTransition(score5, currentQuestion.getTheAnswers().get(4).getItsScore().toString());
                System.out.print(" 5 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT6) {
            if (currentQuestion.getTheAnswers().size() < 6) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(5).getAnswered()) {
                currentQuestion.getTheAnswers().get(5).setAnswered(true);
                labelTransition(Answer6, currentQuestion.getTheAnswers().get(5).getAnAnswer());
                labelTransition(score6, currentQuestion.getTheAnswers().get(5).getItsScore().toString());
                System.out.print(" 6 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT7) {
            if (currentQuestion.getTheAnswers().size() < 7) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(6).getAnswered()) {
                currentQuestion.getTheAnswers().get(6).setAnswered(true);
                labelTransition(Answer7, currentQuestion.getTheAnswers().get(6).getAnAnswer());
                labelTransition(score7, currentQuestion.getTheAnswers().get(6).getItsScore().toString());
                System.out.print(" 7 ");
                playSound(correctNoise);
            }
        } else if (keyCode == KeyCode.DIGIT8) {
            if (currentQuestion.getTheAnswers().size() < 8) {
                return;
            }
            if (!currentQuestion.getTheAnswers().get(7).getAnswered()) {
                currentQuestion.getTheAnswers().get(7).setAnswered(true);
                labelTransition(Answer8, currentQuestion.getTheAnswers().get(7).getAnAnswer());
                labelTransition(score8, currentQuestion.getTheAnswers().get(7).getItsScore().toString());
                System.out.print(" 8 ");
                playSound(correctNoise);
            }
        }
    }
}