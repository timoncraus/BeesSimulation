import javafx.scene.Group;
import javafx.scene.image.Image;

public class WorkerBee extends Bee {
    static Image image = new Image("YellowBee.png");
    static double countSec = 0;
    static int count = 0;
    static double birthSec = 3;
    static double lifeTime = 50;
    static double birthProb = 0.7;

    static double probability;

    public WorkerBee(double width, double countSec1) {
        super(width, image, countSec1);
    }

    public static void update(Group playground, Habitat habitat, double countSec1) {
        probability = Habitat.random(0, 1);
        
        WorkerBee.countSec += Main.frameSec;
        if(probability <=  WorkerBee.birthProb && WorkerBee.countSec >= WorkerBee.birthSec * Main.frameSec) {
            WorkerBee.countSec = 0;
            habitat.addBee(new WorkerBee(Habitat.imageWidth, countSec1), playground);
            WorkerBee.count += 1;
        }
    }
    
    public Boolean isDeleted(double countSec) {
        if(countSec - this.birthday > WorkerBee.lifeTime * Main.frameSec) {
            WorkerBee.count--;
            return true;
        }
        return false;
    }

    public static void resetCounters() {
        countSec = 0;
        count = 0;
    }
}