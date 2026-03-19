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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Round2Controller {
    public AnchorPane pain;
    public StackPane stackPane;
    public Label totalScoreLabel;
    public Label phaseLabel;
    public ImageView backgroundImage;

    public Label answer1, answer2, answer3, answer4, answer5;
    public Label answer6, answer7, answer8, answer9, answer10;
    public Label score1, score2, score3, score4, score5;
    public Label score6, score7, score8, score9, score10;

    Question currentQuestion;
    ArrayList<Integer> selectedAnswers;
    ArrayList<Question> round2Questions;

    private String[] player1Answers;
    private String[] player2Answers;
    private int[] player1Scores;
    private int[] player2Scores;

    private int currentPhase = 1;
    private int currentQuestionIndex = 0;
    private int revealIndex = 0;
    private int totalScore = 0;

    private Label[] p1AnswerLabels;
    private Label[] p1ScoreLabels;
    private Label[] p2AnswerLabels;
    private Label[] p2ScoreLabels;

    private ArrayList<Integer> questionNumbers =  new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    @FXML
    public void initialize() throws Exception {
        pain.setPrefSize(1066, 600);

        NumberBinding scaleBinding = Bindings.min(stackPane.widthProperty().divide(1066), stackPane.heightProperty().divide(600));
        stackPane.scaleXProperty().bind(scaleBinding);
        stackPane.scaleYProperty().bind(scaleBinding);
        Question.readQuestions();
        backgroundImage.setImage(new Image(new FileInputStream("src/round2BG.png")));
        ArrayList<Question> all = Question.getAllTheQuestions();
        int nextQuestionNumber = 0;
        Question.setTest2Questions(); // Test Set
        // questionNumbers.addAll(Arrays.asList(1, 2, 3, 4, 5)); // Use this during the actual game, change based on question numbers
        // Question.setRound2Questions(questionNumbers);


//        while (Question.getAllTheQuestions().get(nextQuestionNumber).getBeenAskedAlready()) {
//            nextQuestionNumber = nextQuestionNumber + 1;
//        }
//        if (nextQuestionNumber + 5 > all.size()) {
//            System.out.println("  [!] Round 2 does not have 5 unused questions available!");
//            return;
//        }

        round2Questions = Question.getRound2Questions();

//        for (int i = nextQuestionNumber; i < nextQuestionNumber + 5; i++) {
//            round2Questions.add(all.get(i));
//        }

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
    }

    public void setupHandlers() {
        Scene scene = pain.getScene();
        scene.setOnKeyPressed(event -> {
            processKeyEvent(event);
        });
    }

    public void displayInstructions() {
        System.out.println("Round 2 INSTRUCTIONS: Press key...");
        System.out.println("     ANSWER   keys: 1 - 8 for correct answer; W for wrong answer (then type it)");
        System.out.println("     QUESTION key : ENTER to advance / reveal; D to display question");
        System.out.println("     ROUND    key : SHIFT back to Round 1");
    }

    public void displayCurrentQuestion() {
        if (currentPhase == 2 || currentPhase == 4 || currentPhase == 5) {
            return;
        }
        if (currentQuestionIndex >= round2Questions.size()) {
            return;
        }
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

    public void processKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleEnter();
        } else if (event.getCode() == KeyCode.D) {
            displayCurrentQuestion();
        } else if (event.getCode() == KeyCode.SHIFT) {
            System.out.print(" SHIFT ");
            try {
                FamilyFeudApp.Round1();
            } catch (Exception ex) {
                System.out.println("Can't switch to Round 1: " + ex);
            }
        } else if (event.getCode() == KeyCode.H) {
            displayInstructions();
        } else {
            handleAnswerKey(event.getCode());
        }
    }

    public void handleAnswerKey(KeyCode key) {
        if (currentPhase == 2 || currentPhase == 4) {
            return;
        }

        if (currentQuestionIndex >= round2Questions.size()) {
            System.out.println("  [!] Only " + round2Questions.size() + " questions loaded.");
            return;
        }

        if (key == KeyCode.W) {
            System.out.print("Type wrong answer: ");
            String wrongAnswer = scanner.nextLine();
            if (currentPhase == 1) {
                player1Answers[currentQuestionIndex] = wrongAnswer;
                player1Scores[currentQuestionIndex] = 0;
                p1AnswerLabels[currentQuestionIndex].setText(wrongAnswer);
                p1ScoreLabels[currentQuestionIndex].setText("?");
            } else if (currentPhase == 3) {
                player2Answers[currentQuestionIndex] = wrongAnswer;
                player2Scores[currentQuestionIndex] = 0;
                p2AnswerLabels[currentQuestionIndex].setText(wrongAnswer);
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
                p1ScoreLabels[currentQuestionIndex].setText("?");
                System.out.print(" " + (answerIndex + 1) + " ");
            } else if (currentPhase == 3) {
                player2Answers[currentQuestionIndex] = chosen.getAnAnswer();
                player2Scores[currentQuestionIndex] = chosen.getItsScore();
                p2AnswerLabels[currentQuestionIndex].setText(chosen.getAnAnswer());
                p2ScoreLabels[currentQuestionIndex].setText("?");
                System.out.print(" " + (answerIndex + 1) + " ");
            }
        }

        System.out.println("  Press ENTER to reveal score.");
    }

    public void handleEnter() {
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
                    totalScoreLabel.setText("Total: " + totalScore);
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
                    totalScoreLabel.setText("Total: " + totalScore);
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

    public void clearBoard() {
        for (int i = 0; i < 5; i++) {
            p1AnswerLabels[i].setText("");
            p1ScoreLabels[i].setText("");
            p2AnswerLabels[i].setText("");
            p2ScoreLabels[i].setText("");
        }
        totalScoreLabel.setText("Total: 0");
    }

    public void resetRound() {
        currentPhase = 1;
        currentQuestionIndex = 0;
        revealIndex = 0;
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
}