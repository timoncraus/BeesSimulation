import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Random;
import javafx.scene.Group;

public class Habitat {
    final static double initialNumberBee = 10;
    final static double imageWidth = 30;

    ArrayList<Bee> bees = new ArrayList<>();
    HashSet<Bee> hashSetBees = new HashSet<>();
    TreeMap<Double, Bee> mapBees = new TreeMap<>(Comparator.comparingDouble(o -> o));

    static Random rand = new Random();
    
    public void generateInitial(Group playground, double countSec1) {
        for(int i = 0; i < initialNumberBee; i++) {
            addBee(new WorkerBee(imageWidth, countSec1), playground);
            WorkerBee.count += 1;
        }
    }

    public void resetCounters() {
        DroneBee.resetCounters();
        WorkerBee.resetCounters();
    }

    public void update(double countSec, Group playground) {
        DroneBee.update(playground, this, countSec);
        WorkerBee.update(playground, this, countSec);


        Iterator<Bee> iter = hashSetBees.iterator();
        Bee iterBee;

        int n = bees.size();
        int deletedCount = 0;
        Bee currentBee;
        for(int i = 0; i < n; i++) {
            currentBee = bees.get(i - deletedCount);
            if(currentBee.isDeleted(countSec)) {
                playground.getChildren().remove(currentBee.getRectangle());
                bees.remove(i - deletedCount);
                deletedCount++;
            }

            
            if(iter.hasNext()) {
                iterBee = iter.next();
                System.out.println(iterBee);
            }
        }

        System.out.println("- - -");
        try {
            double lastBee = mapBees.navigableKeySet().stream().filter(o -> o > 2).findFirst().get();
            System.out.println("Самая первая: " + lastBee + " ");
        }
        catch(Exception e){

        }

       
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
                random(Main.minHeightPlayground, Main.heightPlayground - bee.getHeight())
            )
        );
        
        bees.add(bee);
        hashSetBees.add(bee);
        mapBees.put(bee.birthday, bee);
    }

    static public double random(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

}
