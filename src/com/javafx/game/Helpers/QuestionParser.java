package com.javafx.game.Helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionParser {
    private ArrayList<String> text;

    public QuestionParser() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(("questions.txt")));
        text = new ArrayList<>();
        try {
            while (reader.ready()) {
                text.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QuestionModel getQuestionFromFile(int number)
    {
        QuestionModel questionModel = new QuestionModel();
        for (String str: text){
            if (str.contains("number:")){
                if (Integer.parseInt(parseString(str)) == number)
                {
                    int index = text.indexOf(str);
                    questionModel.setNumber(parseString(text.get(index++)));
                    questionModel.setQuestion(parseString(text.get(index++)));
                    questionModel.setMainAnswer(parseString(text.get(index++)));
                    questionModel.setAnswer1(parseString(text.get(index++)));
                    questionModel.setAnswer2(parseString(text.get(index++)));
                    questionModel.setAnswer3(parseString(text.get(index++)));
                    questionModel.setAnswer4(parseString(text.get(index++)));
                }
            }
        }

        return questionModel;
    }

    private String parseString(String str){
        String [] numberOfQuestion = str.split(":");

        return numberOfQuestion[1].substring(numberOfQuestion[1].indexOf("\"")+1,
                                             numberOfQuestion[1].lastIndexOf("\""));
    }

    public QuestionModel getQuestion(int number)
    {
        return getQuestionFromFile(number);
    }
}
