package com.example.slafamilyfeud2026;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Round2Controller {

    public AnchorPane pain;
    public StackPane stackPane;
    public Label totalScoreLabel;
    public Label phaseLabel;
    public ImageView backgroundImage;

    private Media dingNoise;
    private Media youSaidNoise;
    private Media duplicateNoise;
    private Media zeroPointsNoise;

    public Label answer1, answer2, answer3, answer4, answer5;
    public Label answer6, answer7, answer8, answer9, answer10;
    public Label score1, score2, score3, score4, score5;
    public Label score6, score7, score8, score9, score10;

    public Label timeLabel;
    private Timeline timer;
    private int secondsRemaining;
    private static final int TIMER_DURATION = 30;

    Integer playerTurn;
    Question currentQuestion;
    ArrayList<Integer> selectedAnswers;
    ArrayList<Question> round2Questions;
    Label[] answerLabels;
    Label[] scoreLabels;

    private String[] player1Answers;
    private String[] player2Answers;
    private int[] player1Scores;
    private int[] player2Scores;

    private int currentPhase = 1;
    private int currentQuestionIndex = 0;
    private int totalScore = 0;

    private Label[] p1AnswerLabels;
    private Label[] p1ScoreLabels;
    private Label[] p2AnswerLabels;
    private Label[] p2ScoreLabels;
    private final ArrayList<Integer> questionNumbers =  new ArrayList<>();

    private String[] hiddenAnswers;
    private String[] hiddenScores;

    private Scanner scanner;

    @FXML
    public void initialize() throws Exception {
        pain.setPrefSize(1066, 600);

        NumberBinding scaleBinding = Bindings.min(stackPane.widthProperty().divide(1066), stackPane.heightProperty().divide(600));
        stackPane.scaleXProperty().bind(scaleBinding);
        stackPane.scaleYProperty().bind(scaleBinding);


        backgroundImage.setImage(new Image(new FileInputStream("src/round2BG.png")));

//        ArrayList<Question> all = Question.getAllTheQuestions();
//        int nextQuestionNumber = 0;
//        while (nextQuestionNumber < all.size() && all.get(nextQuestionNumber).getBeenAskedAlready()) {
//            nextQuestionNumber++;
//        }
//        if (nextQuestionNumber + 5 > all.size()) {
//            System.out.println("  [!] Round 2 does not have 5 unused questions available!");
//            return;
//        }
//        round2Questions = new ArrayList<>();
//        for (int i = nextQuestionNumber; i < nextQuestionNumber + 5; i++) {
//            round2Questions.add(all.get(i));
//        }

        Question.setTest2Questions(); // Test Set
        //questionNumbers.addAll(Arrays.asList(13, 11, 14, 2, 3)); // Use this during the actual game, change based on question numbers
        //Question.setRound2Questions(questionNumbers);

        round2Questions = Question.getRound2Questions();

        player1Answers = new String[round2Questions.size()];
        player2Answers = new String[round2Questions.size()];
        player1Scores  = new int[round2Questions.size()];
        player2Scores  = new int[round2Questions.size()];

        p1AnswerLabels = new Label[]{answer1, answer2, answer3, answer4, answer5};
        p1ScoreLabels  = new Label[]{score1,  score2,  score3,  score4,  score5};
        p2AnswerLabels = new Label[]{answer6, answer7, answer8, answer9, answer10};
        p2ScoreLabels  = new Label[]{score6,  score7,  score8,  score9,  score10};

        selectedAnswers = new ArrayList<>();

        clearBoard();
        displayInstructions();
        displayCurrentQuestion();

        String soundPath3 = Paths.get("src/family-feud-ding.mp3").toUri().toString();
        dingNoise = new Media(soundPath3);
        String soundPath5 = Paths.get("src/family-feud-you-said.mp3").toUri().toString();
        youSaidNoise = new Media(soundPath5);
        String soundPath6 = Paths.get("src/family-feud-duplicate-answer.mp3").toUri().toString();
        duplicateNoise = new Media(soundPath6);
        String soundPath7 = Paths.get("src/fm-wrong.mp3").toUri().toString();
        zeroPointsNoise = new Media(soundPath7);

        scanner = new Scanner(System.in);
    }

    public void setupHandlers() {
        Scene scene = pain.getScene();
        scene.setOnKeyPressed(event -> {
            try {
                processKeyEvent(event);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        });
    }

    public void displayInstructions() {
        System.out.println("Round 2 INSTRUCTIONS: Press key...");
        System.out.println("     WRONG    key : 0 to play wrong answer sound");
        System.out.println("     ANSWER   keys: 1 - 8 for correct answer; W for wrong answer");
        System.out.println("     SCORE    key : ENTER to reveal score");
        System.out.println("     HIDE     key : H to hide answers; U to unhide answers");
        System.out.println("     TIMER    keys: T to start, S to stop, R to reset");
        System.out.println("     ROUND    key : SHIFT back to Round 1");
    }

    public void displayCurrentQuestion() {
        if (currentPhase == 5) return;
        if (currentQuestionIndex >= round2Questions.size()) return;
        currentQuestion = round2Questions.get(currentQuestionIndex);
        System.out.println("QUESTION: " + currentQuestion.getTheQuestion());
        int i = 1;
        System.out.print("  ANSWERS:");
        for (Answer answer : currentQuestion.getTheAnswers()) {
            System.out.print("  #" + i + " " + answer.getAnAnswer());
            i++;
        }
        System.out.print("\n");
    }

    public void processKeyEvent(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            handleEnter();
        } else if (event.getCode() == KeyCode.D) {
            displayCurrentQuestion();
        } else if (event.getCode() == KeyCode.SHIFT) {
            System.out.print(" SHIFT ");
            FamilyFeudApp.Round1();
        } else if (event.getCode() == KeyCode.H) {
            hidePlayer1Answers();
        } else if (event.getCode() == KeyCode.U) {
            revealPlayer1Answers();
        } else if (event.getCode() == KeyCode.DIGIT0) {
            playSound(duplicateNoise);
            System.out.print(" WRONG ");
        } else if (event.getCode() == KeyCode.T) {
            startTimer();
            System.out.println(" TIMER STARTED ");
        } else if (event.getCode() == KeyCode.R) {
            resetTimer();
            System.out.println(" TIMER RESET ");
        } else if (event.getCode() == KeyCode.S) {
            stopTimer();
            System.out.println(" TIMER STOPPED ");
        } else {
            handleAnswerKey(event.getCode());
        }
    }

    public void handleAnswerKey(KeyCode key) throws Exception {
        if (currentPhase == 5) return;

        if (currentQuestionIndex >= round2Questions.size()) {
            System.out.println("  [!] Only " + round2Questions.size() + " questions loaded.");
            return;
        }

        if (key == KeyCode.W) {
            System.out.print("Type wrong answer: ");
            String wrongAnswer = scanner.nextLine();
            if (currentPhase == 1) {
                playSound(youSaidNoise);
                player1Answers[currentQuestionIndex] = wrongAnswer;
                player1Scores[currentQuestionIndex] = 0;
                p1AnswerLabels[currentQuestionIndex].setText(wrongAnswer);
                fitTextToLabel(p1AnswerLabels[currentQuestionIndex]);
                p1ScoreLabels[currentQuestionIndex].setText("?");
            } else if (currentPhase == 3) {
                if (player1Answers[currentQuestionIndex] != null &&
                        player1Answers[currentQuestionIndex].equalsIgnoreCase(wrongAnswer)) {
                    System.out.println("  [!] Player 2 can't give the same answer as Player 1!");
                    playSound(duplicateNoise);
                    return;
                }
                playSound(youSaidNoise);
                player2Answers[currentQuestionIndex] = wrongAnswer;
                player2Scores[currentQuestionIndex] = 0;
                p2AnswerLabels[currentQuestionIndex].setText(wrongAnswer);
                fitTextToLabel(p2AnswerLabels[currentQuestionIndex]);
                p2ScoreLabels[currentQuestionIndex].setText("?");
            }
            System.out.print(" WRONG ");
        } else {
            int answerIndex = keyToIndex(key);
            if (answerIndex == -1) return;

            Question q = round2Questions.get(currentQuestionIndex);

            if (answerIndex >= q.getTheAnswers().size()) {
                System.out.println("  [!] Answer #" + (answerIndex + 1) + " doesn't exist for this question.");
                return;
            }

            Answer chosen = q.getTheAnswers().get(answerIndex);

            if (currentPhase == 1) {
                player1Answers[currentQuestionIndex] = chosen.getAnAnswer();
                player1Scores[currentQuestionIndex] = chosen.getItsScore();
                p1AnswerLabels[currentQuestionIndex].setText(chosen.getAnAnswer());
                fitTextToLabel(p1AnswerLabels[currentQuestionIndex]);
                p1ScoreLabels[currentQuestionIndex].setText("?");
                playSound(youSaidNoise);
                System.out.print(" " + (answerIndex + 1) + " ");
            } else if (currentPhase == 3) {
                if (player1Answers[currentQuestionIndex] != null &&
                        player1Answers[currentQuestionIndex].equalsIgnoreCase(chosen.getAnAnswer())) {
                    System.out.println("  [!] Player 2 can't give the same answer as Player 1!");
                    playSound(duplicateNoise);
                    return;
                }
                player2Answers[currentQuestionIndex] = chosen.getAnAnswer();
                player2Scores[currentQuestionIndex] = chosen.getItsScore();
                p2AnswerLabels[currentQuestionIndex].setText(chosen.getAnAnswer());
                fitTextToLabel(p2AnswerLabels[currentQuestionIndex]);
                playSound(youSaidNoise);
                p2ScoreLabels[currentQuestionIndex].setText("?");
                System.out.print(" " + (answerIndex + 1) + " ");
            }
        }

        System.out.println("  Press ENTER to reveal score.");
    }

    public void handleEnter() throws Exception {
        if (currentPhase == 1 || currentPhase == 3) {
            if (currentQuestionIndex >= round2Questions.size()) {
                if (currentPhase == 1) {
                    currentPhase = 3;
                    currentQuestionIndex = 0;
                    System.out.println("\n=== PLAYER 2's TURN ===");
                    displayInstructions();
                    displayCurrentQuestion();
                } else {
                    currentPhase = 5;
                    System.out.println("\n=== ROUND COMPLETE === Final Total: " + totalScore);
                    System.out.println("  Press ENTER to reset.");
                }
                return;
            }

            if (currentPhase == 1) {
                if (p1ScoreLabels[currentQuestionIndex].getText().equals("?")) {
                    int score = player1Scores[currentQuestionIndex];
                    p1ScoreLabels[currentQuestionIndex].setText(String.valueOf(score));
                    totalScore += score;
                    totalScoreLabel.setText("TOTAL: " + totalScore);
                    if (score == 0) {
                        playSound(zeroPointsNoise);
                    } else {
                        playSound(dingNoise);
                    }
                    System.out.println("  Revealed: " + player1Answers[currentQuestionIndex] + " = " + score + " pts | Total: " + totalScore);
                    currentQuestionIndex++;
                    if (currentQuestionIndex < round2Questions.size()) {
                        displayCurrentQuestion();
                    } else {
                        System.out.println("\n  Player 1 done! Press ENTER to start Player 2.");
                    }
                } else {
                    System.out.println("  [!] Answer this question first with a number key.");
                }
            } else if (currentPhase == 3) {
                if (p2ScoreLabels[currentQuestionIndex].getText().equals("?")) {
                    int score = player2Scores[currentQuestionIndex];
                    p2ScoreLabels[currentQuestionIndex].setText(String.valueOf(score));
                    totalScore += score;
                    if (score == 0) {
                        playSound(zeroPointsNoise);
                    } else {
                        playSound(dingNoise);
                    }
                    totalScoreLabel.setText("TOTAL: " + totalScore);
                    System.out.println("  Revealed: " + player2Answers[currentQuestionIndex] + " = " + score + " pts | Total: " + totalScore);
                    currentQuestionIndex++;
                    if (currentQuestionIndex < round2Questions.size()) {
                        displayCurrentQuestion();
                    } else {
                        System.out.println("\n  Player 2 done! Press ENTER to finish.");
                    }
                } else {
                    System.out.println("  [!] Answer this question first with a number key.");
                }
            }

        } else if (currentPhase == 5) {
            resetRound();
        }
    }

    public void fitTextToLabel(Label label) {
        int answerLength = label.getText().length();
        int excessLength = answerLength - 16;
        if (excessLength > 0) {
            label.setFont(new Font("Times New Roman", 40-(excessLength*2)));
        } else {
            label.setFont(new Font("Times New Roman", 40));
        }
    }

    public void hidePlayer1Answers() {
        hiddenAnswers = new String[10];
        hiddenScores  = new String[11];

        for (int i = 0; i < 5; i++) {
            hiddenAnswers[i] = p1AnswerLabels[i].getText();
            hiddenScores[i] = p1ScoreLabels[i].getText();
            p1AnswerLabels[i].setText("");
            p1ScoreLabels[i].setText("");
        }
        for (int i = 0; i < 5; i++) {
            hiddenAnswers[i+5] = p2AnswerLabels[i].getText();
            hiddenScores[i+5] = p2ScoreLabels[i].getText();
            p2AnswerLabels[i].setText("");
            p2ScoreLabels[i].setText("");
        }
        hiddenScores[10] = totalScoreLabel.getText();
        totalScoreLabel.setText("");
    }

    public void revealPlayer1Answers() {
        playSound(youSaidNoise);
        for (int i = 0; i < 5; i++) {
            p1AnswerLabels[i].setText(hiddenAnswers[i]);
            p1ScoreLabels[i].setText(hiddenScores[i]);
        }
        for (int i = 0; i < 5; i++) {
            p2AnswerLabels[i].setText(hiddenAnswers[i+5]);
            p2ScoreLabels[i].setText(hiddenScores[i+5]);
        }
        totalScoreLabel.setText(hiddenScores[10]);
    }

    public void clearBoard() {
        for (int i = 0; i < 5; i++) {
            p1AnswerLabels[i].setText("");
            p1ScoreLabels[i].setText("");
            p2AnswerLabels[i].setText("");
            p2ScoreLabels[i].setText("");
        }
        totalScoreLabel.setText("TOTAL: 0");
    }

    public void resetRound() {
        currentPhase = 1;
        currentQuestionIndex = 0;
        totalScore = 0;
        player1Answers = new String[round2Questions.size()];
        player2Answers = new String[round2Questions.size()];
        player1Scores  = new int[round2Questions.size()];
        player2Scores  = new int[round2Questions.size()];
        clearBoard();
        System.out.println("\n=== ROUND 2 RESET ===");
        displayInstructions();
        displayCurrentQuestion();
    }

    public void playSound(Media sound) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Could not play sound: " + e.getMessage());
        }
    }

    private int keyToIndex(KeyCode key) {
        return switch (key) {
            case DIGIT1 -> 0;
            case DIGIT2 -> 1;
            case DIGIT3 -> 2;
            case DIGIT4 -> 3;
            case DIGIT5 -> 4;
            case DIGIT6 -> 5;
            case DIGIT7 -> 6;
            case DIGIT8 -> 7;
            default -> -1;
        };
    }

    public void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        secondsRemaining = TIMER_DURATION;
        timeLabel.setText(String.valueOf(secondsRemaining));

        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining--;
            timeLabel.setText(String.valueOf(secondsRemaining));

            if (secondsRemaining <= 0) {
                timer.stop();
                System.out.println("TIME'S UP!");
            }
        }));

        timer.setCycleCount(TIMER_DURATION);
        timer.play();
    }
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        secondsRemaining = TIMER_DURATION;
        timeLabel.setText(String.valueOf(secondsRemaining));
    }
}