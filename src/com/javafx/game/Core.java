package com.javafx.game;

import com.javafx.game.Helpers.QuestionParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Core
{
    private BufferedReader reader;
    private ArrayList<String> splitedArrayForGame  = new ArrayList<>();
    private int countOfWords;
    private int countWordsNow;
    private QuestionParser questionParser;


    public Core() throws FileNotFoundException
    {
        reader = new BufferedReader(new FileReader("text.txt"));
        questionParser = new QuestionParser();
    }

    public void SplitArray(String text)
    {
        String[] splitArray = text.split(" ");
        for (String s : splitArray) {
            countOfWords++;
            splitedArrayForGame.add(s);
        }
    }

    public ArrayList<String> getFileContent()
    {
        ArrayList<String> fileArray = new ArrayList<>();
        Integer index = 0;
        try {
            while (reader.ready())
            {
                fileArray.add(reader.readLine());
                SplitArray(fileArray.get(index++));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return fileArray;
    }

    public QuestionParser getQuestionParser() {
        return questionParser;
    }

    public Integer getTextLenght()
    {
        return splitedArrayForGame.get(0).length();
    }

    public String getFirstWord()
    {
        return splitedArrayForGame.get(0);
    }

    public int getCountOfWords() {
        return countOfWords;
    }

    public int getCountWordsNow() {
        return countWordsNow;
    }

    public void removeFirstWord()
    {
        countWordsNow++;
        splitedArrayForGame.remove(0);
    }

}
