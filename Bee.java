import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public abstract class Bee {
    Rectangle rectangle;
    double x, y, width, height, birthday;
    Image image;

    public Bee(double width1, Image image1) {
        image = image1;
        width = width1;
        height = image.getHeight() * (width / image.getWidth());
    }

    public static void update(Group playground, Habitat habitat) {}

    public static void resetCounters() {}

    public Rectangle makeRectangle(double x, double y) {
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(new ImagePattern(image, x, y, width, height, false));
        return rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}