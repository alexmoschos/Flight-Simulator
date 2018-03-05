import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Jet extends Plane{
    static BufferedImage JetImage;

    static {
        try {
            JetImage = ImageIO.read(new File("icons/big_n.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't find jet image file");
            System.exit(1);
        }
    }

    public Jet(int id, int time, int startAirport, int endAirport, String flightName, int speed, int height, int fuel) {
        super(Jet.JetImage,0,0,140,280,16000,28000,2300,15,
                0.0, id,time,startAirport,endAirport,flightName,speed,height,fuel);
    }

    @Override
    boolean verify(ArrayList<Airport> airports) {
        Airport s = airports.get(startAirport-1);
        Airport e = airports.get(endAirport-1);
//        System.out.println(s.getCategory().toString());
//        System.out.println(e.getCategory().toString());
        boolean st = s.getCategory() == Airport.AirportCategory.TWO || s.getCategory() == Airport.AirportCategory.THREE;
        boolean en = e.getCategory() == Airport.AirportCategory.TWO || e.getCategory() == Airport.AirportCategory.THREE;
        return super.verify(airports) && st && en;
    }
}
