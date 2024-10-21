import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;

public class Habitat {
    final static double initialNumberBee = 10;
    final static double imageWidth = 30;

    ArrayList<Bee> bees = new ArrayList<>();

    static Random rand = new Random();
    
    public void generateInitial(Group playground) {
        for(int i = 0; i < initialNumberBee; i++) {
            addBee(new WorkerBee(imageWidth), playground);
            WorkerBee.count += 1;
        }
    }

    public void resetCounters() {
        DroneBee.resetCounters();
        WorkerBee.resetCounters();
    }

    public void update(double countSec, Group playground) {
        DroneBee.update(playground, this);
        WorkerBee.update(playground, this);
    }

    public void deleteAll(Group playground) {
        int n = bees.size();
        for(int i = 0; i < n; i++) {
            playground.getChildren().remove(bees.get(0).getRectangle());
            bees.remove(0);
        }
    }

    public void addBee(Bee bee, Group playground) {
        playground.getChildren().add(
            bee.makeRectangle(
                random(Main.minWidthPlayground, Main.widthPlayground - bee.getWidth()), 
                random(Main.minHeightPlayground, Main.heightWindow - bee.getHeight())
            )
        );
        
        bees.add(bee);
    }

    static public double random(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

}
