import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    static final double minWidthPlayground = 0;
    static final double widthPlayground = 500;
    static final double minHeightPlayground = 30;
    static final double heightPlayground = 500;

    static final double widthWindow = 900;
    static final double heightWindow = 500;

    static final double frameSec = 0.07;
    

    Group playground;
    Rectangle background;
    HBox root;
    Timeline timeLine;
    Habitat habitat;
    UserInterface userInterface;
    Scene scene;

    double countSec = 0;
    boolean pauseGame = false;

    @Override
    public void start(Stage stage) {
        background = new Rectangle(widthPlayground, heightPlayground);
        background.setFill(Color.WHITE);
        playground = new Group(background);
        habitat = new Habitat();
        userInterface = new UserInterface(this);
        timeLine = new Timeline(
            new KeyFrame(Duration.seconds(frameSec), action -> {
                habitat.update(countSec, playground);
                countSec += frameSec;
                userInterface.updateText(countSec);
            })
        );
        timeLine.setCycleCount(100_000);

        root = new HBox(playground, userInterface.getInterfaceVBox());
        
        scene = new Scene(root, widthWindow, heightWindow);
        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.B) play();
            if (e.getCode() == KeyCode.E) stop();
            if (e.getCode() == KeyCode.T) userInterface.toggleTime();
        });

        stage.setScene(scene);
        stage.setTitle("Симуляция жизни пчёл");
        stage.show();
    }

    public void play() {
        userInterface.togglePlayPauseButtons(true, false, false);

        habitat.deleteAll(playground);
        habitat.resetCounters();
        countSec = 0;

        timeLine.play();
    }

    public void stop() {
        timeLine.stop();
        userInterface.togglePlayPauseButtons(false, true, true);
    }

    public void pause() {
        if(pauseGame) {
            timeLine.play();
            userInterface.togglePlayPauseButtons(true, false, false);
        }
        else {
            timeLine.pause();
            userInterface.togglePlayPauseButtons(true, false, true);
        }
        pauseGame = !pauseGame;
    }

    public static void main(String args[]) {
        launch(args);
    }
}

