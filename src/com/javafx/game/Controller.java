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

    private boolean isTreadActive;
    private ArrayList<String> fileContent = new ArrayList<>();
    private int fails = 0;
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
        File file = new File("src/templates/rootTemplate.html");
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
        try {
            loadTextContent(core.getFileContent());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        isGameEnd();
        onButtonClicked();
        onTextChanged();
    }

    private void loadTextContent(ArrayList<String> content)
    {
        isTreadActive = true;
        if (content != null)
        {
            Runnable check = () ->{
                webEngine = webView.getEngine();
                if (webEngine != null)
                {
                    Document document = (Document) webEngine.executeScript("document");
                    if (document != null)
                    {
                        Element element = document.getElementById("textArea");
                        if (element instanceof HTMLDivElement)
                        {
                            HTMLDivElement txtArea = (HTMLDivElement) document.getElementById("textArea");
                            String text = "";
                            for (String s : content)
                            {
                                text += s + " ";
                            }
                            txtArea.setTextContent(text);
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

    private void isGameEnd()
    {
        webEngine = webView.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    Document document = (Document) webEngine.executeScript("document");

                    EventTarget buttonRestart = (EventTarget) document.getElementById("restartGame");
                    buttonRestart.addEventListener("click", new EventListener() {
                        @Override
                        public void handleEvent(Event evt) {
                            webEngine.executeScript("restartGame()");
                            fails = 0;
                            try {
                                loadTextContent(core.getFileContent());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }, false);

                    EventTarget buttonClose = (EventTarget) document.getElementById("closeGame");
                    buttonClose.addEventListener("click", new EventListener() {
                        @Override
                        public void handleEvent(Event evt) {
                            System.exit(0);
                        }
                    }, false);
                }
            }
        });
    }

    private void onButtonClicked()
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


    private void onTextChanged()
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
                                        QuestionParser parser = core.getQuestionParser();
                                        int number = (int) (Math.random()*parser.getQuestionCount())+1;
                                        QuestionModel model = parser.getQuestion(number);
                                        inputElem.setValue("");
                                        fails++;

                                        webEngine.executeScript("startQuestionModal('"+ model.getNumber() +"'," +
                                                            " '"+ model.getQuestion() +"', '"+ model.getMainAnswer() +"', '"+ model.getAnswer1() +"', " +
                                                            "'"+ model.getAnswer2() +"', '"+ model.getAnswer3() +"'," +
                                                            " '"+ model.getAnswer4() +"' )");
                                    }
                                }
                            }

                            if (core.getCountOfWords() == (core.getCountWordsNow())) {
                                String countOfWords = core.getCountOfWords() + "/" + (core.getCountWordsNow());
                                webEngine.executeScript("launchFinalModal('"+ countOfWords +"', '" + fails + "')");
                                webEngine.executeScript("timer('false')");
                            }
                        }
                    }, false);
                }
            }
        });
    }
}
