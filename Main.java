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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    static final double minWidthWindow = 0;
    static final double widthWindow = 500;
    static final double minHeightWindow = 30;
    static final double heightWindow = 500;
    static final double frameSec = 0.07;

    double countSec = 0;
    boolean game = false;
    boolean visibilityTimeText = false;

    Group root;
    Button startButton;
    Button stopButton;
    Label timeText;
    Label droneText;
    Label workerText;
    HBox hbox;
    Timeline timeLine;
    Habitat habitat;
    Scene scene;

    @Override
    public void start(Stage stage) {
        root = new Group();

        startButton = new Button();         
        startButton.setText("Старт");

        stopButton = new Button();         
        stopButton.setText("Стоп");
        stopButton.setDisable(true);

        timeText = new Label();
        timeText.setFont(new Font("Arial", 24));

        hbox = new HBox(10, startButton, stopButton, timeText);
        root.getChildren().add(hbox);


        workerText = new Label();
        workerText.setFont(new Font("Arial", 24));
        workerText.setLayoutX(widthWindow/2 - 150);
        workerText.setLayoutY(heightWindow/2 - 50);
        workerText.setTextFill(Color.color(0.94, 0.86, 0.11));
        workerText.setVisible(false);
        
        droneText = new Label();
        droneText.setFont(new Font("Arial", 24));
        droneText.setLayoutX(widthWindow/2 - 150);
        droneText.setLayoutY(heightWindow/2);
        droneText.setTextFill(Color.color(0.98, 0.51, 0.01));
        droneText.setVisible(false);


        root.getChildren().addAll(workerText, droneText);

        habitat = new Habitat();

        timeLine = new Timeline(
                new KeyFrame(Duration.seconds(frameSec), action -> {
                    habitat.update(countSec, root);
                    countSec += frameSec;
                    timeText.setText(String.format("%.2f сек", countSec));
                })
        );
        timeLine.setCycleCount(100_000);

        startButton.setOnAction(event -> play());

        stopButton.setOnAction(event -> stop());
        

        scene = new Scene(root, widthWindow, heightWindow);
        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.B) play();
            if (e.getCode() == KeyCode.E) stop();
            if (e.getCode() == KeyCode.T) {
                if(visibilityTimeText) timeText.setVisible(false);
                else timeText.setVisible(true);
                visibilityTimeText = !visibilityTimeText;
            };
        });

        stage.setScene(scene);
        stage.setTitle("Laba 5");
        stage.show();
    }

    public void play() {
        workerText.setVisible(false);
        droneText.setVisible(false);

        startButton.setDisable(true);
        stopButton.setDisable(false);

        habitat.resetCounters();
        countSec = 0;

        timeLine.play();
    }

    public void stop() {
        timeLine.stop();
        habitat.deleteAll(root);

        workerText.setText("Количество рабочих пчёл: " + habitat.getWorkerCount());
        workerText.setVisible(true);
        droneText.setText("Количество трутней: " + habitat.getDroneCount());
        droneText.setVisible(true);

        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    public static void main(String args[]) {
        launch(args);
    }
}

