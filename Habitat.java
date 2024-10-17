import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;

public class Habitat {
    final double initialNumberBee = 10;

    final double droneBirthSec = 5 * Main.frameSec;
    final double droneBirthMaxPart = 0.2;

    final double workerBirthSec = 3 * Main.frameSec;
    final double workerBirthProb = 0.7;

    final double imageWidth = 30;

    ArrayList<Bee> bees = new ArrayList<>();

    double droneCountSec = 0;
    double workerCountSec = 0;

    int droneCount = 0;
    int workerCount = 0;

    double probability;

    static Random rand = new Random();
    
    public void generateInitial(Group root) {
        for(int i = 0; i < initialNumberBee; i++) {
            addBee(new WorkerBee(imageWidth), root);
            workerCount += 1;
        }
    }

    public void resetCounters() {
        droneCountSec = 0;
        workerCountSec = 0;

        droneCount = 0;
        workerCount = 0;
    }

    public void update(double countSec, Group root) {
        droneCountSec += Main.frameSec;
        if(workerCount != 0 && ((double)droneCount / (double)workerCount) < droneBirthMaxPart && droneCountSec >= droneBirthSec) {
            droneCountSec = 0;
            addBee(new DroneBee(imageWidth), root);
            droneCount += 1;
        }

        probability = random(0, 1);
        
        workerCountSec += Main.frameSec;
        if(probability <=  workerBirthProb && workerCountSec >= workerBirthSec) {
            workerCountSec = 0;
            addBee(new WorkerBee(imageWidth), root);
            workerCount += 1;
        }
    }

    public void deleteAll(Group root) {
        int n = bees.size();
        for(int i = 0; i < n; i++) {
            root.getChildren().remove(bees.get(0).getRectangle());
            bees.remove(0);
        }
    }

    public void addBee(Bee bee, Group root) {
        
        root.getChildren().add(
            bee.makeRectangle(
                random(Main.minWidthWindow, Main.widthWindow - bee.getWidth()), 
                random(Main.minHeightWindow, Main.heightWindow - bee.getHeight())
            )
        );
        if(bee.getRectangle().getX() >  Main.widthWindow - bee.getWidth() ||
                bee.getRectangle().getY() >  Main.heightWindow - bee.getHeight()) {
            System.out.println(bee.getRectangle().getX() + " " + bee.getRectangle().getY());
        }
        
        bees.add(bee);
    }

    public int getDroneCount() {
        return droneCount;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    static public double random(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

}
