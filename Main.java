import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    static public double frameSec = 0.07;

    static final double minWidthPlayground = 0;
    static final double widthPlayground = 500;
    static final double minHeightPlayground = 0;
    static final double heightPlayground = 500;

    static final double widthWindow = 1000;
    static final double heightWindow = 600;

    
    UserInterface menuUserInterface, userInterface;
    HBox menuRoot, root;
    Scene menuScene, scene;
    Group playground;
    Rectangle background;
    Timeline timeLine;
    Habitat habitat;

    double countSec = 0;
    boolean pauseGame = false;

    Stage menuStage, stage;

    @Override
    public void start(Stage menuStage1) {
        menuStage = menuStage1;
        habitat = new Habitat();
        setNewTimer();
        menuUserInterface = new UserInterface(this, true);
        
        menuRoot = new HBox(menuUserInterface.getInterfaceVBox());
        menuRoot.setPadding(new Insets(20));
        

        menuScene = new Scene(menuRoot, 800, 600);

        menuStage.setScene(menuScene);
        menuStage.setTitle("Меню симуляции жизни пчёл");
        menuStage.show();
    }

    public void openGame() {
        menuStage.close();

        userInterface = new UserInterface(this, false);
        stage = new Stage();

        background = new Rectangle(widthPlayground, heightPlayground);
        background.setFill(Color.WHITE);
        playground = new Group(background);

        

        root = new HBox(playground, userInterface.getInterfaceVBox());
        
        scene = new Scene(root, widthWindow, heightWindow);
        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.B) playGame();
            if (e.getCode() == KeyCode.E) stopGame();
            if (e.getCode() == KeyCode.T) userInterface.toggleTime();
        });

        stage.setScene(scene);
        stage.setTitle("Симуляция жизни пчёл");
        stage.show();
    }

    public void playGame() {
        userInterface.togglePlayPauseButtons(true, false, false);

        habitat.deleteAll(playground);
        habitat.resetCounters();
        countSec = 0;

        timeLine.play();
    }

    public void stopGame() {
        timeLine.stop();
        userInterface.togglePlayPauseButtons(false, true, true);
    }

    public void pauseGame() {
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

    public void setNewTimer() {
        boolean wasNull = false;
        if(timeLine != null) {
            timeLine.stop();
            wasNull = true;
        }
        timeLine = new Timeline(
            new KeyFrame(Duration.seconds(frameSec), action -> {
                habitat.update(countSec, playground);
                countSec += frameSec;
                userInterface.updateText(countSec, habitat.bees);
            })
        );
        timeLine.setCycleCount(100_000);
        if(wasNull) {
            timeLine.play();
        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}

