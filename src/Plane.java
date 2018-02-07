public class Plane {
    public int x,y;
    private int startSpeed;
    private int maxSpeed;
    private int fuel;
    private int maxHeight;
    private int elevationSpeed;
    private int consumption;
    public double orientation;
    public Plane(int x, int y, double orientation, int startSpeed, int maxSpeed, int fuel, int maxHeight, int elevationSpeed, int consumption) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.startSpeed = startSpeed;
        this.maxSpeed = maxSpeed;
        this.fuel = fuel;
        this.maxHeight = maxHeight;
        this.elevationSpeed = elevationSpeed;
        this.consumption = consumption;
    }

}
