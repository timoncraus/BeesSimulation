import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UserInterface {
    static final double boxGap = 10;

    public Label timeText, droneText, workerText, birthProbText;
    VBox interfaceVBox, infoTextVBox;
    HBox playPauseHBox, changeBirthProbHBox;
    Button startButton, pauseButton, stopButton, showTimeButton, showInfoButton;
    ComboBox<String> probsComboBox;

    public UserInterface(Main main) {
        timeText = new Label("0,00 сек");
        timeText.setFont(new Font("Arial", 24));

        makeInfoTextVBox();
        makePlayPauseHBox(main);
        makeShowTimeButton();
        makeShowInfoButton();
        makeChangeBirthProbHBox();

        interfaceVBox = new VBox(boxGap, timeText, infoTextVBox, playPauseHBox, showTimeButton, showInfoButton, changeBirthProbHBox);
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
            showTimeButton.setText("Скрывать время");
        }
        else {
            interfaceVBox.getChildren().remove(timeText);
            showTimeButton.setText("Показывать время");
        }
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
        pauseButton.setDisable(true);
        pauseButton.setOnAction(event -> main.pause());

        stopButton = new Button();         
        stopButton.setText("Стоп");
        stopButton.setDisable(true);
        stopButton.setOnAction(event -> main.stop());

        playPauseHBox = new HBox(boxGap, startButton, pauseButton, stopButton);
    }

    private void makeShowTimeButton() {
        showTimeButton = new Button();         
        showTimeButton.setText("Скрывать время");
        showTimeButton.setOnAction(event -> toggleTime());
    }

    private void makeShowInfoButton() {
        showInfoButton = new Button();         
        showInfoButton.setText("Скрывать информацию");
        showInfoButton.setOnAction(event -> toggleInfo());
    }

    private void makeChangeBirthProbHBox() {
        birthProbText = new Label("Вероятность рождения рабочей пчелы:");

        probsComboBox = new ComboBox<String>();
        for(int i = 0; i <= 100; i += 10) {
            probsComboBox.getItems().add(i + "%");
        }
        probsComboBox.setValue("70%");
        probsComboBox.setOnAction(event -> setBirthProb(probsComboBox.getValue()));

        changeBirthProbHBox = new HBox(boxGap, birthProbText, probsComboBox);
    }

    private void setBirthProb(String probStrig) {
        WorkerBee.birthProb = Double.parseDouble(probStrig.replace("%", ""))/100;
    }

    private void toggleInfo() {
        if(infoTextVBox.getParent() == null) {
            int hasTimeText = timeText.getParent() == null? 0 : 1;
            interfaceVBox.getChildren().add(hasTimeText, infoTextVBox);
            showInfoButton.setText("Скрывать информацию");
        }
        else {
            interfaceVBox.getChildren().remove(infoTextVBox);
            showInfoButton.setText("Показывать информацию");
        }
    }
}
