package com.example.slafamilyfeud2026;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

public class Round2Controller {

    public AnchorPane pain;
    public Label totalScoreLabel;
    public Label phaseLabel;

    public Label answer1, answer2, answer3, answer4, answer5;
    public Label answer6, answer7, answer8, answer9, answer10;
    public Label score1, score2, score3, score4, score5;
    public Label score6, score7, score8, score9, score10;

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
    private int revealIndex = 0;
    private int totalScore = 0;

    private Label[] p1AnswerLabels;
    private Label[] p1ScoreLabels;
    private Label[] p2AnswerLabels;
    private Label[] p2ScoreLabels;

    @FXML
    public void initialize() throws Exception {
        Question.readQuestions();

        ArrayList<Question> all = Question.getAllTheQuestions();
        round2Questions = new ArrayList<>();
        for (int i = 0; i < Math.min(5, all.size()); i++) {
            round2Questions.add(all.get(i));
        }

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
            try {
                processKeyEvent(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void displayInstructions() {
        System.out.println("INSTRUCTIONS: Press key...");
        System.out.println("     ANSWER   keys: Key #1 - 5 to choose that ranked answer");
        System.out.println("     QUESTION key : ENTER to advance / reveal; D to display question");
        System.out.println("     ROUND    key : SHIFT for Round 1");
    }

    public void displayCurrentQuestion() {
        if (currentPhase != 1 && currentPhase != 3) return;
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
            displayInstructions();
        } else {
            handleAnswerKey(event.getCode());
        }
    }

    public void handleAnswerKey(KeyCode key) {
        int rankIndex = keyToIndex(key);
        if (rankIndex == -1) return;
        if (currentPhase != 1 && currentPhase != 3) return;
        if (currentQuestionIndex >= round2Questions.size()) return;

        if (rankIndex >= round2Questions.size()) {
            System.out.println("  [!] Only " + round2Questions.size() + " questions loaded.");
            return;
        }

        Question q = round2Questions.get(currentQuestionIndex);

        if (rankIndex >= q.getTheAnswers().size()) {
            System.out.println("  [!] That answer rank doesn't exist for this question.");
            return;
        }

        Answer chosen = q.getTheAnswers().get(rankIndex);

        if (currentPhase == 1) {
            player1Answers[currentQuestionIndex] = chosen.getAnAnswer();
            player1Scores[currentQuestionIndex]  = chosen.getItsScore();
            p1AnswerLabels[currentQuestionIndex].setText(chosen.getAnAnswer());
            p1ScoreLabels[currentQuestionIndex].setText("?");
            System.out.print(" " + (rankIndex + 1) + " ");

        } else if (currentPhase == 3) {
            player2Answers[currentQuestionIndex] = chosen.getAnAnswer();
            player2Scores[currentQuestionIndex]  = chosen.getItsScore();
            p2AnswerLabels[currentQuestionIndex].setText(chosen.getAnAnswer());
            p2ScoreLabels[currentQuestionIndex].setText("?");
            System.out.print(" " + (rankIndex + 1) + " ");
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < round2Questions.size()) {
            displayCurrentQuestion();
        } else {
            if (currentPhase == 1) {
                System.out.println("\n  Player 1 done! Press ENTER to reveal answers.");
            } else if (currentPhase == 3) {
                System.out.println("\n  Player 2 done! Press ENTER to reveal answers.");
            }
        }
    }

    public void handleEnter() {
        if (currentPhase == 1) {
            if (currentQuestionIndex < round2Questions.size()) {
                System.out.println("  Player 1 still has " + (round2Questions.size() - currentQuestionIndex) + " question(s) left.");
                return;
            }
            currentPhase = 2;
            revealIndex = 0;
            System.out.println("\n=== REVEALING PLAYER 1 === Press ENTER for each answer.");

        } else if (currentPhase == 2) {
            if (revealIndex < round2Questions.size()) {
                int score = player1Scores[revealIndex];
                p1ScoreLabels[revealIndex].setText(String.valueOf(score));
                totalScore += score;
                totalScoreLabel.setText("Total: " + totalScore);
                System.out.println("  P1 Q" + (revealIndex + 1) + ": " + player1Answers[revealIndex] + " = " + score + " pts | Total: " + totalScore);
                revealIndex++;
            }
            if (revealIndex == round2Questions.size()) {
                currentPhase = 3;
                currentQuestionIndex = 0;
                System.out.println("\n=== PLAYER 2's TURN ===");
                displayInstructions();
                displayCurrentQuestion();
            }

        } else if (currentPhase == 3) {
            if (currentQuestionIndex < round2Questions.size()) {
                System.out.println("  Player 2 still has " + (round2Questions.size() - currentQuestionIndex) + " question(s) left.");
                return;
            }
            currentPhase = 4;
            revealIndex = 0;
            System.out.println("\n=== REVEALING PLAYER 2 === Press ENTER for each answer.");

        } else if (currentPhase == 4) {
            if (revealIndex < round2Questions.size()) {
                int score = player2Scores[revealIndex];
                p2ScoreLabels[revealIndex].setText(String.valueOf(score));
                totalScore += score;
                totalScoreLabel.setText("Total: " + totalScore);
                System.out.println("  P2 Q" + (revealIndex + 1) + ": " + player2Answers[revealIndex] + " = " + score + " pts | Total: " + totalScore);
                revealIndex++;
            }
            if (revealIndex == round2Questions.size()) {
                currentPhase = 5;
                System.out.println("\n=== ROUND COMPLETE === Final Total: " + totalScore);
                System.out.println("  Press ENTER to reset.");
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
            default -> -1;
        };
    }
}