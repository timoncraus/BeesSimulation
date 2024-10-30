import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserInterface {
    static final double boxGap = 10;
    static final String allowedChars = "0123456789.";

    ScrollPane scrollInterface, scrollMenuInterface;
    Label timeText, droneText, droneSecText, workerText, workerSecText, frameSecText, 
                            birthProbText, birthMaxPartText, droneLifeTimeText, workerLifeTimeText, workerBirthSecText;
    VBox menuInterfaceVBox, interfaceVBox, infoTextVBox, gameVBox, openGameVBox, changeInfoVBox;
    HBox playPauseHBox, frameSecHBox, changeBirthProbHBox, changeBirthMaxPartHBox, 
                            droneLifeTimeHBox, workerLifeTimeHBox, workerBirthSecHBox;
    Button startButton, pauseButton, stopButton, showTimeButton, showInfoButton, showObjectsButton, openGameButton;
    ComboBox<String> probsComboBox, partsComboBox;
    TextField frameSecInput, droneLifeTimeInput, workerLifeTimeInput, workerBirthSecInput;
    Font usuialFont = new Font("Arial", 24);
    Font smallFont = new Font("Arial", 17);
    VBox rootObjects = new VBox();

    public UserInterface(Main main, boolean isMenu) {
        gameVBox = getGameVBox(isMenu, main);
        changeInfoVBox = getChangeInfoVBox(main);
        openGameVBox = getOpenGameVBox(isMenu, main);

        

        interfaceVBox = new VBox(boxGap, 
                                gameVBox,
                                changeInfoVBox,
                                openGameVBox);
                                
        scrollInterface = new ScrollPane();
        scrollInterface.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInterface.setContent(interfaceVBox); 
        HBox.setHgrow(scrollInterface, Priority.ALWAYS);
    }

    public ScrollPane getInterfaceVBox() {
        return scrollInterface;
    }

    public void updateText(double countSec, ArrayList<Bee> bees) {
        timeText.setText(String.format("%.2f сек", countSec));

        workerText.setText("Количество рабочих пчёл: " + WorkerBee.count);
        workerSecText.setText(String.format("Таймер рабочих пчёл: %.2f", WorkerBee.countSec));

        droneText.setText("Количество трутней: " + DroneBee.count);
        droneSecText.setText(String.format("Таймер трутней: %.2f", DroneBee.countSec));

        updateObjects(bees);
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

    private VBox getGameVBox(boolean isMenu, Main main) {
        if(!isMenu) {
            timeText = new Label("0,00 сек");
            timeText.setFont(usuialFont);

            makeInfoTextVBox();
            makePlayPauseHBox(main);
            makeShowTimeButton();
            makeShowInfoButton();
            makeShowObjectsButton(main.habitat.bees);

            return new VBox(boxGap, 
                            playPauseHBox, 
                            showTimeButton, 
                            showInfoButton,
                            showObjectsButton);
        }
        return new VBox();
    }

    private VBox getOpenGameVBox(boolean isMenu, Main main) {
        if(isMenu) {
            openGameButton = new Button();        
            openGameButton.setText("Начать симуляцию");
            openGameButton.setOnAction(event -> main.openGame());
            return new VBox(boxGap, openGameButton);
        }
        return new VBox();
    }

    private VBox getChangeInfoVBox(Main main) {
        makeChangeFrameSecHBox(main);
        makeChangeBirthProbHBox();
        makeChangeBirthMaxPartHBox();
        makeChangeDroneLifeTimeHBox();
        makeChangeWorkerLifeTimeHBox();
        makeChangeWorkerBirthSecHBox();
        return new VBox(frameSecHBox, 
                        changeBirthProbHBox,
                        changeBirthMaxPartHBox,
                        workerLifeTimeHBox,
                        droneLifeTimeHBox,
                        workerBirthSecHBox);
    }
    
    private void makeInfoTextVBox() {
        workerText = new Label("Количество рабочих пчёл: 0");
        workerText.setFont(usuialFont);
        workerText.setTextFill(Color.GOLD);

        workerSecText = new Label("Таймер рабочих пчёл: 0");
        workerSecText.setFont(smallFont);
        workerSecText.setTextFill(Color.GOLD);
        
        droneText = new Label("Количество трутней: 0");
        droneText.setFont(usuialFont);
        droneText.setTextFill(Color.ORANGERED);

        droneSecText = new Label("Таймер трутней: 0");
        droneSecText.setFont(smallFont);
        droneSecText.setTextFill(Color.ORANGERED);
        

        infoTextVBox = new VBox(boxGap, workerText, workerSecText, droneText, droneSecText);
    }

    private void makePlayPauseHBox(Main main) {
        startButton = new Button();         
        startButton.setText("Старт");
        startButton.setOnAction(event -> main.playGame());

        pauseButton = new Button();         
        pauseButton.setText("Пауза");
        pauseButton.setDisable(true);
        pauseButton.setOnAction(event -> main.pauseGame());

        stopButton = new Button();         
        stopButton.setText("Стоп");
        stopButton.setDisable(true);
        stopButton.setOnAction(event -> main.stopGame());

        playPauseHBox = new HBox(boxGap, startButton, pauseButton, stopButton);
    }

    private void makeShowTimeButton() {
        showTimeButton = new Button();         
        showTimeButton.setText("Показывать время");
        showTimeButton.setOnAction(event -> toggleTime());
    }

    private void makeShowInfoButton() {
        showInfoButton = new Button();         
        showInfoButton.setText("Показывать информацию");
        showInfoButton.setOnAction(event -> toggleInfo());
    }

    private void makeShowObjectsButton(ArrayList<Bee> bees) {
        showObjectsButton = new Button();         
        showObjectsButton.setText("Текущие объекты");
        showObjectsButton.setOnAction(event -> showObjects(bees));
    }

    private void makeChangeFrameSecHBox(Main main) {
        frameSecText = new Label("Частота обновления:");

        frameSecInput = new TextField(Main.frameSec + "");
        frameSecInput.textProperty().addListener((obs, oldVal, newVal)-> {
            Main.frameSec = setDoubleInput(frameSecInput, oldVal, newVal);
            main.setNewTimer();
        });

        frameSecHBox = new HBox(boxGap, frameSecText, frameSecInput);
    }

    private void makeChangeBirthProbHBox() {
        birthProbText = new Label("Вероятность рождения раб. пчелы:");

        probsComboBox = new ComboBox<String>();
        for(int i = 0; i <= 100; i += 10) {
            probsComboBox.getItems().add(i + "%");
        }
        probsComboBox.setValue((int)(WorkerBee.birthProb * 100) + "%");
        probsComboBox.setOnAction(event -> setBirthProb(probsComboBox.getValue()));

        changeBirthProbHBox = new HBox(boxGap, birthProbText, probsComboBox);
    }

    private void makeChangeBirthMaxPartHBox() {
        birthMaxPartText = new Label("Макс. часть для рождения трутней:");

        partsComboBox = new ComboBox<String>();
        for(int i = 0; i <= 100; i += 10) {
            partsComboBox.getItems().add(i + "%");
        }
        partsComboBox.setValue((int)(DroneBee.birthMaxPart * 100) + "%");
        partsComboBox.setOnAction(event -> setBirthMaxPart(partsComboBox.getValue()));

        changeBirthMaxPartHBox = new HBox(boxGap, birthMaxPartText, partsComboBox);
    }

    private void makeChangeWorkerLifeTimeHBox() {
        workerLifeTimeText = new Label("Время жизни рабочих:");

        workerLifeTimeInput = new TextField(WorkerBee.lifeTime + "");
        workerLifeTimeInput.textProperty().addListener((obs, oldVal, newVal)-> {
            WorkerBee.lifeTime = setDoubleInput(workerLifeTimeInput, oldVal, newVal);
        });

        workerLifeTimeHBox = new HBox(boxGap, workerLifeTimeText, workerLifeTimeInput);
    }

    private void makeChangeDroneLifeTimeHBox() {
        droneLifeTimeText = new Label("Время жизни трутней:");

        droneLifeTimeInput = new TextField(DroneBee.lifeTime + "");
        droneLifeTimeInput.textProperty().addListener((obs, oldVal, newVal)-> {
            DroneBee.lifeTime = setDoubleInput(droneLifeTimeInput, oldVal, newVal);
        });

        droneLifeTimeHBox = new HBox(boxGap, droneLifeTimeText, droneLifeTimeInput);
    }

    private void makeChangeWorkerBirthSecHBox() {
        workerBirthSecText = new Label("Таймер для рождения рабочих пчёл:");

        workerBirthSecInput = new TextField(WorkerBee.birthSec + "");

        workerBirthSecInput.textProperty().addListener((obs, oldVal, newVal)-> {
            WorkerBee.birthSec = setDoubleInput(workerBirthSecInput, oldVal, newVal);
        });

        workerBirthSecHBox = new HBox(boxGap, workerBirthSecText, workerBirthSecInput);
        
    }

    

    

    private double setDoubleInput(TextField input, String oldVal, String newVal) {
        newVal.replace(",", ".");
        if(newVal.length() > 0){
            if(newVal.charAt(0) == '.') {
                newVal = "0" + newVal;
            }
            else if(newVal.length() > 1 && 
                            newVal.charAt(0) == '0' && 
                            allowedChars.contains("" + newVal.charAt(1)) && 
                            newVal.charAt(1) != '.') {
                newVal = newVal.substring(1, newVal.length());
            }

            char lastChar = newVal.charAt(newVal.length()-1);
            long count = newVal.chars().filter(c -> c == '.').count();
            if(!allowedChars.contains("" + lastChar) || count > 1) {
                input.setText(oldVal);
                return Double.parseDouble(oldVal);
            }
        }
        else {
            newVal = "0";
        }
        
        try {
            double num = Double.parseDouble(newVal);
            input.setText(newVal);
            return num;
        }
        catch(Exception e) {
            return Double.parseDouble(oldVal);
        }
    }
    

    private void setBirthProb(String probStrig) {
        WorkerBee.birthProb = Double.parseDouble(probStrig.replace("%", ""))/100;
    }

    private void setBirthMaxPart(String partString) {
        DroneBee.birthMaxPart = Double.parseDouble(partString.replace("%", ""))/100;
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

    private void showObjects(ArrayList<Bee> bees) {
        rootObjects.setPadding(new Insets(20));
        ScrollPane scrollObjects = new ScrollPane();
        scrollObjects.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollObjects.setContent(rootObjects); 
        HBox.setHgrow(scrollObjects, Priority.ALWAYS);

        Scene scene = new Scene(scrollObjects, 500, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Текущие объекты");
        stage.show();
    }

    private void updateObjects(ArrayList<Bee> bees) {
        rootObjects.getChildren().clear();
        int n = bees.size();
        Bee currentBee;
        Label currentLabel;
        for(int i = 0; i < n; i++) {
            currentBee = bees.get(i);
            currentLabel = new Label(String.format("%s %.2f сек", currentBee.getString(), currentBee.birthday));
            rootObjects.getChildren().add(currentLabel);
        }
    }
}
