import javafx.scene.image.Image;
public class DroneBee extends Bee {
    static Image image = new Image("OrangeBee.png");
    public DroneBee(double width) {
        super(width, image);
    }
}