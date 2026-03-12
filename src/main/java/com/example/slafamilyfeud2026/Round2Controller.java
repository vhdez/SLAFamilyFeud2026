package com.example.slafamilyfeud2026;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Round2Controller {

    public Label phaseLabel;
    public Label totalScoreLabel;
    public AnchorPane pain;
    public ImageView backgroundImage;
    public Label answer1, answer2, answer3, answer4, answer5;
    public Label answer6, answer7, answer8, answer9, answer10;

    public Label score1, score2, score3, score4, score5;

    public Label score6, score7, score8, score9, score10;

    private ArrayList<Question> round2Questions;
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

    @FXML
    public void initialize() throws Exception {
        backgroundImage.setImage(new Image(new FileInputStream("src/round2BG.png")));
        Question.readQuestions();

        ArrayList<Question> all = Question.getAllTheQuestions();
        round2Questions = new ArrayList<>();
        for (int i = 0; i < Math.min(5, all.size()); i++) {
            round2Questions.add(all.get(i));
        }

        player1Answers = new String[5];
        player2Answers = new String[5];
        player1Scores  = new int[5];
        player2Scores  = new int[5];

        p1AnswerLabels = new Label[]{answer1, answer2, answer3, answer4, answer5};
        p1ScoreLabels  = new Label[]{score1,  score2,  score3,  score4,  score5};
        p2AnswerLabels = new Label[]{answer6, answer7, answer8, answer9, answer10};
        p2ScoreLabels  = new Label[]{score6,  score7,  score8,  score9,  score10};

        clearBoard();
        updatePhaseLabel();
        printCurrentQuestion();
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

    public void processKeyEvent(KeyEvent event) throws Exception {
        KeyCode key = event.getCode();

        if (key == KeyCode.SHIFT) {
            FamilyFeudApp.Round1();

        } else if (key == KeyCode.D) {
            printCurrentQuestion();

        } else if (key == KeyCode.H) {
            printInstructions();

        } else if (key == KeyCode.ENTER) {
            handleEnter();

        } else {
            handleAnswerKey(key);
        }
    }


    private void handleEnter() throws Exception {
        if (currentPhase == 1) {
            if (currentQuestionIndex < round2Questions.size()) {
                System.out.println("  [!] Player 1 hasn't answered all questions yet.");
                return;
            }
            currentPhase = 2;
            currentQuestionIndex = 0;
            clearP2Column();
            updatePhaseLabel();
            System.out.println("\n PLAYER 2's TURN");
            printCurrentQuestion();

        } else if (currentPhase == 2) {
            if (currentQuestionIndex < round2Questions.size()) {
                System.out.println("  [!] Player 2 hasn't answered all questions yet.");
                return;
            }
            currentPhase = 3;
            revealAll();
            updatePhaseLabel();
            System.out.println("\n=== REVEAL === Total Score: " + totalScore);

        } else if (currentPhase == 3) {
            resetRound();
        }
    }


    private void handleAnswerKey(KeyCode key) {
        int answerIndex = keyToIndex(key);
        if (answerIndex == -1) return;
        if (currentPhase == 3) return;


        if (answerIndex >= round2Questions.size()) {
            System.out.println("  [!] Only " + round2Questions.size() + " questions loaded.");
            return;
        }

        if (answerIndex != currentQuestionIndex) {
            System.out.println("  [!] Current question is #" + (currentQuestionIndex + 1) + ". Press " + (currentQuestionIndex + 1) + " to answer it.");
            return;
        }

        Question q = round2Questions.get(currentQuestionIndex);

        if (currentPhase == 1) {

            Answer topAnswer = q.getTheAnswers().get(0);
            player1Answers[currentQuestionIndex] = topAnswer.getAnAnswer();
            player1Scores[currentQuestionIndex]  = topAnswer.getItsScore();

            p1AnswerLabels[currentQuestionIndex].setText(topAnswer.getAnAnswer());
            p1ScoreLabels[currentQuestionIndex].setText("?");

            System.out.println("  P1 Q" + (currentQuestionIndex + 1) + ": " + topAnswer.getAnAnswer() + " (" + topAnswer.getItsScore() + " pts) — hidden until reveal");

            currentQuestionIndex++;
            if (currentQuestionIndex < round2Questions.size()) {
                printCurrentQuestion();
            } else {
                System.out.println("\n  Player 1 done! Press ENTER to start Player 2's turn.");
            }

        } else if (currentPhase == 2) {
            Answer topAnswer = q.getTheAnswers().get(0);
            player2Answers[currentQuestionIndex] = topAnswer.getAnAnswer();
            player2Scores[currentQuestionIndex]  = topAnswer.getItsScore();

            p2AnswerLabels[currentQuestionIndex].setText(topAnswer.getAnAnswer());
            p2ScoreLabels[currentQuestionIndex].setText(String.valueOf(topAnswer.getItsScore()));

            System.out.println("  P2 Q" + (currentQuestionIndex + 1) + ": " + topAnswer.getAnAnswer() + " (" + topAnswer.getItsScore() + " pts)");

            currentQuestionIndex++;
            if (currentQuestionIndex < round2Questions.size()) {
                printCurrentQuestion();
            } else {
                System.out.println("\n  Player 2 done! Press ENTER to reveal all answers and total score.");
            }
        }
    }

    private void revealAll() {
        totalScore = 0;
        for (int i = 0; i < round2Questions.size(); i++) {
            p1ScoreLabels[i].setText(String.valueOf(player1Scores[i]));
            totalScore += player1Scores[i];
            totalScore += player2Scores[i];
        }
        totalScoreLabel.setText("Total: " + totalScore);
    }

    private void clearBoard() {
        for (int i = 0; i < 5; i++) {
            p1AnswerLabels[i].setText("");
            p1ScoreLabels[i].setText("");
            p2AnswerLabels[i].setText("");
            p2ScoreLabels[i].setText("");
        }
        totalScoreLabel.setText("Total: 0");
    }

    private void clearP2Column() {
        for (int i = 0; i < 5; i++) {
            p2AnswerLabels[i].setText("");
            p2ScoreLabels[i].setText("");
        }
    }

    private void resetRound() throws Exception {
        currentPhase = 1;
        currentQuestionIndex = 0;
        totalScore = 0;
        player1Answers = new String[5];
        player2Answers = new String[5];
        player1Scores  = new int[5];
        player2Scores  = new int[5];
        clearBoard();
        updatePhaseLabel();
        System.out.println("\n ROUND 2 RESET ");
        printCurrentQuestion();
    }

    private void updatePhaseLabel() {
        if (phaseLabel == null) return;
        switch (currentPhase) {
            case 1 -> phaseLabel.setText("Fast Money — Player 1");
            case 2 -> phaseLabel.setText("Fast Money — Player 2");
            case 3 -> phaseLabel.setText("Fast Money — REVEAL");
        }
    }

    private void printCurrentQuestion() {
        if (currentQuestionIndex >= round2Questions.size()) return;
        Question q = round2Questions.get(currentQuestionIndex);
        System.out.println("\n[Phase " + currentPhase + " | Q" + (currentQuestionIndex + 1) + "] " + q.getTheQuestion());
        System.out.println("  Answers (sorted by score):");
        int i = 1;
        for (Answer a : q.getTheAnswers()) {
            System.out.println("    #" + i + " " + a.getAnAnswer() + " — " + a.getItsScore() + " pts");
            i++;
        }
        System.out.println("  Press [" + (currentQuestionIndex + 1) + "] to confirm answer, [D] to reprint, [H] for help.");
    }

    private void printInstructions() {
        System.out.println("\nROUND 2 INSTRUCTIONS:");
        System.out.println("  [1-5]  Confirm the current question's answer");
        System.out.println("  [D]    Reprint current question to console");
        System.out.println("  [H]    Show this help");
        System.out.println("  [ENTER] Advance phase (P1 done -> P2 turn -> Reveal -> Reset)");
        System.out.println("  [SHIFT] Return to Round 1");
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