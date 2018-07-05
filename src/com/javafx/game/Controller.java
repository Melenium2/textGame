package com.javafx.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLTextAreaElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Button btnStart;

    @FXML private WebView webView;
    private WebEngine webEngine;

    private boolean isTreadActive = true;

    private Core core;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            core = new Core();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        webEngine = webView.getEngine();
        File file = new File("D:\\Work\\Java\\src\\templates\\rootTemplate.html");
        if (file.canRead())
        {
            URL url = null;
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            webEngine.load(url.toString());
        }

        ArrayList<String> textForTextArea = core.getFileContent();

        if (textForTextArea != null)
        {
            Runnable check = () ->{
                WebEngine webEngine = webView.getEngine();
                if (webEngine != null)
                {
                    Document document = webEngine.getDocument();
                    if (document != null)
                    {
                        Element element = document.getElementById("textArea");
                        if (element instanceof HTMLTextAreaElement)
                        {
                            HTMLTextAreaElement txtArea = (HTMLTextAreaElement) element;
                            String content = "";
                            for (String s : textForTextArea)
                            {
                                content += s + " ";
                            }
                            txtArea.setValue(content);
                            isTreadActive = false;
                        }
                    }
                }
            };

            new Thread(() -> {
                while(isTreadActive) {
                    Platform.runLater(check);
                    try
                    {
                        Thread.sleep(1_000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @FXML
    public void onButtonClicked()
    {
        textField.setEditable(true);
        System.out.println("Some message");
        btnStart.setDisable(true);
    }

    @FXML
    public void onTextChanged()
    {
        int lenghtNow;
        textField.setTextFormatter(core.getTextFromater());
        System.out.println(textField.getText());
        if (textField.getText().length() == core.getTextLenght())
        {
            System.out.println(textField.getText().length() + " = " + core.getTextLenght());
            System.out.println(textField.getText() + " " + core.getFirstWord());
            if (textField.getText().equals(core.getFirstWord()))
            {

                textArea.selectRange(0, core.getLenghtNow());
                System.out.println(textField.getText() + " = " + core.getFirstWord());
                textField.clear();
                core.removeFirstWord();
            }
            else
            {
                System.out.println("Not");
            }
        }
    }

}
