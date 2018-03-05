import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    @Override
    public boolean verify(ArrayList<Airport> airports) {
        //call super.verify and also check categories
        Airport s = airports.get(startAirport-1);
        Airport e = airports.get(endAirport-1);
        boolean st = s.getCategory() == Airport.AirportCategory.ONE || s.getCategory() == Airport.AirportCategory.THREE;
        boolean en = e.getCategory() == Airport.AirportCategory.ONE || e.getCategory() == Airport.AirportCategory.THREE;
        return super.verify(airports) && st && en;
    }
}
