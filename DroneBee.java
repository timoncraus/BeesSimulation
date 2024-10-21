import javafx.scene.image.Image;
import javafx.scene.Group;

public class DroneBee extends Bee {
    static Image image = new Image("OrangeBee.png");
    static double birthSec = 5 * Main.frameSec;
    static double birthMaxPart = 0.2;

    static double countSec = 0;
    static int count = 0;

    public DroneBee(double width) {
        super(width, image);
    }

    public static void update(Group playground, Habitat habitat) {
        DroneBee.countSec += Main.frameSec;
        if(WorkerBee.count != 0 && ((double)DroneBee.count / (double)WorkerBee.count) < DroneBee.birthMaxPart 
                                                        && DroneBee.countSec >= DroneBee.birthSec) {
            DroneBee.countSec = 0;
            habitat.addBee(new DroneBee(Habitat.imageWidth), playground);
            DroneBee.count += 1;
        }
    }

    public static void resetCounters() {
        countSec = 0;
        count = 0;
    }
}