import javafx.scene.image.Image;
public class WorkerBee extends Bee {
    static Image image = new Image("YellowBee.png");
    public WorkerBee(double width) {
        super(width, image);
    }
}