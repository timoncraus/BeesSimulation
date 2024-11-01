import javafx.scene.image.Image;
import javafx.scene.Group;

public class DroneBee extends Bee {
    static Image image = new Image("OrangeBee.png");
    static double birthSec = 5;
    static double lifeTime = 70 ;
    static double birthMaxPart = 0.2;

    static double countSec = 0;
    static int count = 0;

    public DroneBee(double width, double countSec1) {
        super(width, image, countSec1);
    }

    public String getString() {
        return "Трутень";
    }

    public static void update(Group playground, Habitat habitat, double countSec1) {
        DroneBee.countSec += Main.frameSec;
        if(WorkerBee.count != 0 && ((double)DroneBee.count / (double)WorkerBee.count) < DroneBee.birthMaxPart 
                                                        && DroneBee.countSec >= DroneBee.birthSec * Main.frameSec) {
            DroneBee.countSec = 0;
            habitat.addBee(new DroneBee(Habitat.imageWidth, countSec1), playground);
            DroneBee.count += 1;
        }
    }

    public Boolean isDeleted(double countSec) {
        if(countSec - this.birthday > DroneBee.lifeTime * Main.frameSec) {
            DroneBee.count--;
            return true;
        }
        return false;
    }

    public static void resetCounters() {
        countSec = 0;
        count = 0;
    }
}