import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UserInterface {
    static final double boxGap = 10;

    public Label timeText, droneText, workerText;
    VBox interfaceVBox, infoTextVBox;
    HBox playPauseHBox, toggleTimeHBox, toggleInfoHBox, root;
    Button startButton, pauseButton, stopButton, showTimeButton, hideTimeButton, showInfoButton, hideInfoButton;


    public UserInterface(Main main) {
        timeText = new Label("0,00 сек");
        timeText.setFont(new Font("Arial", 24));

        makeInfoTextVBox();
        makePlayPauseHBox(main);
        makeToggleTimeHBox();
        makeToggleInfoHBox();

        interfaceVBox = new VBox(boxGap, timeText, infoTextVBox, playPauseHBox, toggleTimeHBox, toggleInfoHBox);
    }

    public VBox getInterfaceVBox() {
        return interfaceVBox;
    }

    public void updateText(double countSec) {
        timeText.setText(String.format("%.2f сек", countSec));
        workerText.setText("Количество рабочих пчёл: " + WorkerBee.count);
        droneText.setText("Количество трутней: " + DroneBee.count);
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
    
    private void makeInfoTextVBox() {
        workerText = new Label("Количество рабочих пчёл: 0");
        workerText.setFont(new Font("Arial", 24));
        workerText.setTextFill(Color.GOLD);
        
        droneText = new Label("Количество трутней: 0");
        droneText.setFont(new Font("Arial", 24));
        droneText.setTextFill(Color.ORANGERED);

        infoTextVBox = new VBox(boxGap, workerText, droneText);
    }

    private void makePlayPauseHBox(Main main) {
        startButton = new Button();         
        startButton.setText("Старт");
        startButton.setOnAction(event -> main.play());

        pauseButton = new Button();         
        pauseButton.setText("Пауза");
        pauseButton.setOnAction(event -> main.pause());

        stopButton = new Button();         
        stopButton.setText("Стоп");
        stopButton.setDisable(true);
        stopButton.setOnAction(event -> main.stop());

        playPauseHBox = new HBox(boxGap, startButton, pauseButton, stopButton);
    }

    private void makeToggleTimeHBox() {
        showTimeButton = new Button();         
        showTimeButton.setText("Показывать время");
        showTimeButton.setDisable(true);
        showTimeButton.setOnAction(event -> toggleTime());

        hideTimeButton = new Button();         
        hideTimeButton.setText("Скрывать время");
        hideTimeButton.setOnAction(event -> toggleTime());

        toggleTimeHBox = new HBox(boxGap, showTimeButton, hideTimeButton);
    }

    private void makeToggleInfoHBox() {
        showInfoButton = new Button();         
        showInfoButton.setText("Показывать информацию");
        showInfoButton.setDisable(true);
        showInfoButton.setOnAction(event -> toggleInfo());

        hideInfoButton = new Button();         
        hideInfoButton.setText("Скрывать информацию");
        hideInfoButton.setOnAction(event -> toggleInfo());

        toggleInfoHBox = new HBox(boxGap, showInfoButton, hideInfoButton);
    }

    private void toggleInfo() {
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
}
