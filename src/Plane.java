
import java.awt.image.BufferedImage;
import java.util.*;

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
    ArrayList<Position> path;
    int i;
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
        this.path = new ArrayList<>();
        i = 0;
    }
    boolean verify(ArrayList<Airport> airports){
        try {
            return height <= maxHeight && startSpeed <= maxSpeed && fuel <= maxFuel && airports.get(startAirport-1).isOpen() && airports.get(endAirport-1).isOpen();
        } catch (ArrayIndexOutOfBoundsException e){
            //System.out.println(startAirport);
            return false;
        }
    }
    void calculatePath(ArrayList<Airport> airports) {
        Position current = null;
        Queue<Position> frontier = new LinkedList<>();
        HashSet<Position> visited = new HashSet<>();
        HashMap<Position, Position> cameFrom = new HashMap<>();
        Airport startAir = airports.get(startAirport-1);
        Position start = new Position(startAir.getX(),startAir.getY(),0);
        x = startAir.getX();
        y = startAir.getY();
        Airport endAir = airports.get(endAirport-1);
        Position end = new Position(endAir.getX(),endAir.getY(),0);
        frontier.add(start);
        visited.add(start);
        Position zz = new Position(startAir.getX(),startAir.getY());
        System.out.println(zz.hashCode());
        System.out.println(start.hashCode());

        while(!frontier.isEmpty()) {
            System.out.println(frontier.size());
            current = frontier.remove();
            if(current.equals(end)){
                break;
            }
            for(Position next : current.neighbors()){
                if(!visited.contains(next)){
                    frontier.add(next);
                    visited.add(next);
                    cameFrom.put(next,current);
                }
            }
        }
        while(current != cameFrom.get(current)){
            path.add(current);
            current = cameFrom.get(current);
        }
        for(Position x : path){
            System.out.println(x.toString());
        }
        Collections.reverse(path);
    }
    private boolean updatePosition(){
        try {
            Position current = path.get(i++);
            if(x > current.x && y > current.y){
                orientation = 3.0*Math.PI/4 ;
            }
            if(x > current.x && y < current.y){
                orientation = 3/4;
            }
            if(x < current.x && y > current.y){
                orientation = 3/4;
            }
            if(x == current.x && y > current.y){
                orientation = 0.0;
            }
            if(x == current.x && y < current.y){
                orientation = 1.0*Math.PI;
            }
            if(x < current.x && y == current.y){
                orientation = 1.0*Math.PI/2;
            }
            if(x > current.x && y == current.y){
                orientation = 3.0*Math.PI/2;
            }

            x = current.x;
            y = current.y;
            return true;
        } catch(IndexOutOfBoundsException e){
            return false;
        }
    }
    public void startTimer(Map map){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello world");
                if (!updatePosition()) timer.cancel();
                map.repaint();
            }
        }, 200, 350);
    }
}
