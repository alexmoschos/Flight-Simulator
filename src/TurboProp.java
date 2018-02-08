import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TurboProp extends Plane {
    static BufferedImage TurboPropImage;

    static {
        try {
            TurboPropImage = ImageIO.read(new File("icons/middle_n.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't find TurboProp image file");
            System.exit(1);
        }
    }
    public TurboProp(int id, int time, int startAirport, int endAirport, String flightName, int speed, int height, int fuel) {
        super(TurboProp.TurboPropImage,0, 0, 100, 220, 4200, 16000, 1200, 9,
                0.0, id, time, startAirport, endAirport, flightName, speed, height, fuel);
    }
}

