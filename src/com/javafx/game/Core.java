package com.javafx.game;

import javafx.scene.control.TextFormatter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Core
{
    private BufferedReader reader;
    private ArrayList<String> splitedArrayForGame  = new ArrayList<>();
    private int lenghtNow;


    public Core() throws FileNotFoundException
    {
        reader = new BufferedReader(new FileReader("text.txt"));
    }

    public void SplitArray(String text)
    {
        String[] splitArray = text.split(" ");
        for (String s : splitArray) {
            splitedArrayForGame.add(s);
        }
    }

    public ArrayList<String> getSplitedArrayForGame() {
        return splitedArrayForGame;
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

    public Integer getTextLenght()
    {
        return splitedArrayForGame.get(0).length();
    }

    public String getFirstWord()
    {
        return splitedArrayForGame.get(0);
    }

    public void removeFirstWord()
    {
        splitedArrayForGame.remove(0);
    }

    public int getLenghtNow()
    {
        return lenghtNow += getTextLenght()+1;
    }

    public TextFormatter<String> getTextFromater()
    {
        TextFormatter<String> textFormatter = new TextFormatter<String>(change -> {
            String text = change.getText();
            if (!change.isContentChange())
            {
                return change;
            }
            if (text.matches("[а-я|А-Я]*[$&+,:;=?@#|'<>.-^*()%!]*") || text.isEmpty())
            {
                return change;
            }

            return null;
        });

        return textFormatter;
    }

}

/**
 * Переписать все под веб
 */
