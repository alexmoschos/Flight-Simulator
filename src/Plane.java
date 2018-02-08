import java.awt.image.BufferedImage;

public class Plane {
    public int x,y;
    final private int startSpeed;
    final private int maxSpeed;
    final private int maxFuel;
    final private int maxHeight;
    final private int elevationSpeed;
    final private int consumption;
    final public BufferedImage planeImage;
    public double orientation;
    int id;
    int time;
    int startAirport;
    int endAirport;
    String flightName;
    int speed;
    int height;
    int fuel;

    public Plane(BufferedImage plane,int x, int y, int startSpeed, int maxSpeed, int maxFuel, int maxHeight, int elevationSpeed,
                 int consumption, double orientation, int id, int time, int startAirport, int endAirport,
                 String flightName, int speed, int height, int fuel) {
        this.planeImage = plane;
        this.x = x;
        this.y = y;
        this.startSpeed = startSpeed;
        this.maxSpeed = maxSpeed;
        this.maxFuel = maxFuel;
        this.maxHeight = maxHeight;
        this.elevationSpeed = elevationSpeed;
        this.consumption = consumption;
        this.orientation = orientation;
        this.id = id;
        this.time = time;
        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.flightName = flightName;
        this.speed = speed;
        this.height = height;
        this.fuel = fuel;
    }
}
