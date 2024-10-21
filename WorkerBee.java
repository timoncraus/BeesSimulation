import javafx.scene.Group;
import javafx.scene.image.Image;
public class WorkerBee extends Bee {
    static Image image = new Image("YellowBee.png");
    static double countSec = 0;
    static int count = 0;
    static double birthSec = 3 * Main.frameSec;
    static double birthProb = 0.7;

    static double probability;

    public WorkerBee(double width) {
        super(width, image);
    }

    public static void update(Group playground, Habitat habitat) {
        probability = Habitat.random(0, 1);
        
        WorkerBee.countSec += Main.frameSec;
        if(probability <=  WorkerBee.birthProb && WorkerBee.countSec >= WorkerBee.birthSec) {
            WorkerBee.countSec = 0;
            habitat.addBee(new WorkerBee(Habitat.imageWidth), playground);
            WorkerBee.count += 1;
        }
    }

    public static void resetCounters() {
        countSec = 0;
        count = 0;
    }
}