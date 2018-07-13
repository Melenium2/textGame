package com.javafx.game.Helpers;

public class QuestionModel {
    private String number;
    private String question;
    private String mainAnswer;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public QuestionModel() {
    }

    public QuestionModel(String number, String question, String mainAnswer, String answer1, String answer2, String answer3, String answer4) {
        this.number = number;
        this.question = question;
        this.mainAnswer = mainAnswer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMainAnswer() {
        return mainAnswer;
    }

    public void setMainAnswer(String mainAnswer) {
        this.mainAnswer = mainAnswer;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }
}
