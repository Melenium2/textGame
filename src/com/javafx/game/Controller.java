package com.javafx.game;

import com.javafx.game.Helpers.QuestionModel;
import com.javafx.game.Helpers.QuestionParser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLButtonElement;
import org.w3c.dom.html.HTMLDivElement;
import org.w3c.dom.html.HTMLInputElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
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
                        if (element instanceof HTMLDivElement)
                        {
                            HTMLDivElement txtArea = (HTMLDivElement) element;
                            String content = "";
                            for (String s : textForTextArea)
                            {
                                content += s + " ";
                            }
                            txtArea.setTextContent(content);
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

        onButtonClicked();
        onTextChanged();
    }


    public void onButtonClicked()
    {
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED)
                {
                    Document document = (Document) webEngine.executeScript("document");
                    EventTarget buttonStop = (EventTarget) document.getElementById("btnStop");
                    buttonStop.addEventListener("click", new EventListener() {
                            @Override
                        public void handleEvent(Event evt) {

                                webEngine.executeScript(" invokeModal(' "+ core.getFirstWord() +" '," +
                                                                    " ' "+ core.getCountWordsNow() +" '," +
                                                                    " ' "+ core.getCountOfWords() +" ') ");
                                webEngine.executeScript("timer('false')");
                        }
                    }, false);

                    EventTarget buttonStart = (EventTarget) document.getElementById("btnStart");
                    buttonStart.addEventListener("click", new EventListener() {
                        @Override
                        public void handleEvent(Event evt) {
                           HTMLInputElement inputElement = (HTMLInputElement) document.getElementById("inpText");
                           if (inputElement != null)
                           {
                               inputElement.setDisabled(false);
                           }
                           HTMLButtonElement buttonStopElement = (HTMLButtonElement) document.getElementById("btnStop");
                           if (buttonStopElement != null)
                           {
                               buttonStopElement.setDisabled(false);
                           }
                           HTMLButtonElement buttonStartElement = (HTMLButtonElement) document.getElementById("btnStart");
                           if (buttonStartElement != null)
                           {
                               buttonStartElement.setDisabled(true);
                           }

                           webEngine.executeScript("timer('true')");
                        }
                    }, false);

                    EventTarget buttonClose = (EventTarget) document.getElementById("btnClose");
                    buttonClose.addEventListener("click", new EventListener() {
                        @Override
                        public void handleEvent(Event evt) {
                            webEngine.executeScript("timer('true')");
                        }
                    },false);
                }
            }
        });
    }


    public void onTextChanged()
    {
        webEngine = webView.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED)
                {
                    Document document = (Document) webEngine.getDocument();
                    EventTarget inpText = (EventTarget) document.getElementById("inpText");
                    inpText.addEventListener("input", new EventListener() {
                        @Override
                        public void handleEvent(Event evt) {
                            HTMLInputElement inputElem = (HTMLInputElement) document.getElementById("inpText");
                            if (inputElem != null)
                            {
                                if (inputElem.getValue().length() == core.getTextLenght())
                                {
                                    if (inputElem.getValue().equals(core.getFirstWord()))
                                    {
                                        webEngine.executeScript(" textAreaChanger(' "+ core.getFirstWord() +" ') ");
                                        core.removeFirstWord();
                                        inputElem.setValue("");
                                    }
                                    else
                                    {
                                        int number = (int) (Math.random()*3)+1;
                                        System.out.println(number);
                                        QuestionParser parser = core.getQuestionParser();
                                        QuestionModel model = parser.getQuestion(number);

                                        webEngine.executeScript("startQuestionModal('"+ model.getNumber() +"'," +
                                                            " '"+ model.getQuestion() +"', '"+ model.getMainAnswer() +"', '"+ model.getAnswer1() +"', " +
                                                            "'"+ model.getAnswer2() +"', '"+ model.getAnswer3() +"'," +
                                                            " '"+ model.getAnswer4() +"' )");
                                    }
                                }
                            }
                        }
                    }, false);
                }
            }
        });
    }
}
