import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    static final double minWidthPlayground = 0;
    static final double widthPlayground = 500;
    static final double minHeightPlayground = 30;
    static final double heightPlayground = 500;

    static final double widthWindow = 900;
    static final double heightWindow = 500;

    static final double frameSec = 0.07;
    static final double boxGap = 10;

    Group playground;
    Rectangle background;
    Button startButton, pauseButton, stopButton, showTimeButton, hideTimeButton, showInfoButton, hideInfoButton;
    Label timeText, droneText, workerText;
    HBox playPauseHBox, toggleTimeHBox, toggleInfoHBox, root;
    VBox interfaceVBox, infoTextVBox;
    Timeline timeLine;
    Habitat habitat;
    Scene scene;

    double countSec = 0;
    boolean pauseGame = false;

    @Override
    public void start(Stage stage) {
        background = new Rectangle(widthPlayground, heightPlayground);
        background.setFill(Color.WHITE);
        playground = new Group(background);
        habitat = new Habitat();
        timeLine = new Timeline(
            new KeyFrame(Duration.seconds(frameSec), action -> {
                habitat.update(countSec, playground);
                countSec += frameSec;
                timeText.setText(String.format("%.2f сек", countSec));
                workerText.setText("Количество рабочих пчёл: " + WorkerBee.count);
                droneText.setText("Количество трутней: " + DroneBee.count);
            })
        );
        timeLine.setCycleCount(100_000);

        makeInterfaceVBox();

        root = new HBox(playground, interfaceVBox);
        
        scene = new Scene(root, widthWindow, heightWindow);
        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.B) play();
            if (e.getCode() == KeyCode.E) stop();
            if (e.getCode() == KeyCode.T) toggleTime();
        });

        stage.setScene(scene);
        stage.setTitle("Laba 5");
        stage.show();
    }

    public void play() {
        togglePlayPauseButtons(true, false, false);

        habitat.deleteAll(playground);
        habitat.resetCounters();
        countSec = 0;

        timeLine.play();
    }

    public void stop() {
        timeLine.stop();
        togglePlayPauseButtons(false, true, true);
    }

    public void pause() {
        if(pauseGame) {
            timeLine.play();
            togglePlayPauseButtons(true, false, false);
        }
        else {
            timeLine.pause();
            togglePlayPauseButtons(true, false, true);
        }
        pauseGame = !pauseGame;
    }

    public void togglePlayPauseButtons(Boolean start, Boolean pause, Boolean stop) {
        startButton.setDisable(start);
        pauseButton.setDisable(pause);
        stopButton.setDisable(stop);
    }

    public void toggleTime() {
        if(timeText.getParent() == null) {
            interfaceVBox.getChildren().add(0, timeText);
        }
        else {
            interfaceVBox.getChildren().remove(timeText);
        }
        showTimeButton.setDisable(!showTimeButton.isDisabled());
        hideTimeButton.setDisable(!hideTimeButton.isDisabled());
    }

    public void toggleInfo() {
        int hasTimeText = timeText.getParent() == null? 0 : 1;
        if(infoTextVBox.getParent() == null) {
            interfaceVBox.getChildren().add(hasTimeText, infoTextVBox);
        }
        else {
            interfaceVBox.getChildren().remove(infoTextVBox);
        }
        showInfoButton.setDisable(!showInfoButton.isDisabled());
        hideInfoButton.setDisable(!hideInfoButton.isDisabled());
    }

    public void makeInterfaceVBox() {
        timeText = new Label("0,00 сек");
        timeText.setFont(new Font("Arial", 24));

        makeInfoTextVBox();
        makePlayPauseHBox();
        makeToggleTimeHBox();
        makeToggleInfoHBox();

        interfaceVBox = new VBox(boxGap, timeText, infoTextVBox, playPauseHBox, toggleTimeHBox, toggleInfoHBox);
    }
    
    public void makeInfoTextVBox() {
        workerText = new Label("Количество рабочих пчёл: 0");
        workerText.setFont(new Font("Arial", 24));
        workerText.setTextFill(Color.GOLD);
        
        droneText = new Label("Количество трутней: 0");
        droneText.setFont(new Font("Arial", 24));
        droneText.setTextFill(Color.ORANGERED);

        infoTextVBox = new VBox(boxGap, workerText, droneText);
    }

    public void makePlayPauseHBox() {
        startButton = new Button();         
        startButton.setText("Старт");
        startButton.setOnAction(event -> play());

        pauseButton = new Button();         
        pauseButton.setText("Пауза");
        pauseButton.setOnAction(event -> pause());

        stopButton = new Button();         
        stopButton.setText("Стоп");
        stopButton.setDisable(true);
        stopButton.setOnAction(event -> stop());

        playPauseHBox = new HBox(boxGap, startButton, pauseButton, stopButton);
    }

    public void makeToggleTimeHBox() {
        showTimeButton = new Button();         
        showTimeButton.setText("Показывать время");
        showTimeButton.setDisable(true);
        showTimeButton.setOnAction(event -> toggleTime());

        hideTimeButton = new Button();         
        hideTimeButton.setText("Скрывать время");
        hideTimeButton.setOnAction(event -> toggleTime());

        toggleTimeHBox = new HBox(boxGap, showTimeButton, hideTimeButton);
    }

    public void makeToggleInfoHBox() {
        showInfoButton = new Button();         
        showInfoButton.setText("Показывать информацию");
        showInfoButton.setDisable(true);
        showInfoButton.setOnAction(event -> toggleInfo());

        hideInfoButton = new Button();         
        hideInfoButton.setText("Скрывать информацию");
        hideInfoButton.setOnAction(event -> toggleInfo());

        toggleInfoHBox = new HBox(boxGap, showInfoButton, hideInfoButton);
    }

    public static void main(String args[]) {
        launch(args);
    }
}

