package com.example.slafamilyfeud2026;

public class Answer {
    private String anAnswer;
    private Integer itsScore;
    private Boolean answered;

    public Answer(String anAnswer, Integer itsScore, Boolean answered) {
        this.anAnswer = anAnswer;
        this.itsScore = itsScore;
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "anAnswer='" + anAnswer + '\'' +
                ", itsScore=" + itsScore +
                ", answered=" + answered +
                '}';
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public Integer getItsScore() {
        return itsScore;
    }

    public void setItsScore(Integer itsScore) {
        this.itsScore = itsScore;
    }

    public String getAnAnswer() {
        return anAnswer;
    }

    public void setAnAnswer(String anAnswer) {
        this.anAnswer = anAnswer;
    }
}