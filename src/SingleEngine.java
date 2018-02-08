import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SingleEngine extends Plane{
    static BufferedImage SingleEngineImage;

    static {
        try {
            SingleEngineImage = ImageIO.read(new File("icons/small_n.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't find SingleEngine image file");
            System.exit(1);
        }
    }
    public SingleEngine(int id, int time, int startAirport, int endAirport, String flightName, int speed, int height, int fuel) {
        super(SingleEngine.SingleEngineImage,0,0,60,110,280,8000, 700,3,
                0.0, id,time,startAirport,endAirport,flightName,speed,height,fuel);
    }
}
