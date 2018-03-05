
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
    int currHeight;
    boolean crashed;
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
        this.i = 0;
        this.currHeight = 0;
        this.crashed = false;
    }
    boolean verify(ArrayList<Airport> airports){
        try {
            return height <= maxHeight && speed <= maxSpeed && fuel <= maxFuel && airports.get(startAirport-1).isOpen() && airports.get(endAirport-1).isOpen();
        } catch (ArrayIndexOutOfBoundsException e){
            //System.out.println(startAirport);
            return false;
        }
    }
    void calculatePath(ArrayList<Airport> airports) {
        Position current = null;
        PriorityQueue<Position> frontier = new PriorityQueue<>(1, new Comparator<Position>() {
            @Override
            public int compare(Position position, Position t1) {
                if(position.priority > t1.priority) return 1;
                else return -1;
            }
        });
        HashSet<Position> visited = new HashSet<>();
        HashMap<Position, Position> cameFrom = new HashMap<>();
        Airport startAir = airports.get(startAirport-1);
        Position start = new Position(startAir.getX(),startAir.getY(),0);
        x = startAir.getX();
        y = startAir.getY();
        Airport endAir = airports.get(endAirport-1);
        Position end = endAir.approach();
        frontier.add(startAir.approach());
        visited.add(startAir.approach());
;

        while(!frontier.isEmpty()) {
            //System.out.println(frontier.size());
            //Scanner scan = new Scanner(System.in);
            current = frontier.remove();
            //System.out.println(current.toString());
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
        path.add(new Position(endAir.getX(),endAir.getY()));
        while(current != cameFrom.get(current)){
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(new Position(startAir.getX(),startAir.getY()));

        Collections.reverse(path);
    }
    private boolean updatePosition(Map map) throws CrashedException, SpeedLimitException {
        try {
            Position current = path.get(i++);
            if(x > current.x && y > current.y){
                orientation = 7.0*Math.PI/4 ;
            }
            if(x > current.x && y < current.y){
                orientation = 1.0*Math.PI/4;
            }
            if(x < current.x && y > current.y){
                orientation = 5.0*Math.PI/4;
            }
            if(x < current.x && y < current.y){
                orientation = 3.0*Math.PI/4;
            }
            if(x == current.x && y > current.y){
                orientation = 3.0*Math.PI/2;
            }
            if(x == current.x && y < current.y){
                orientation = 1.0*Math.PI/2;
            }
            if(x < current.x && y == current.y){
                orientation = 1.0*Math.PI;
            }
            if(x > current.x && y == current.y){
                orientation = 0;
            }

            x = current.x;
            y = current.y;
            fuel -= consumption * 20;
            if(currHeight < height) {
                // System.out.println(currHeight);
                currHeight += elevationSpeed;
            }
            if(currHeight > height){
                currHeight = height;
            }
            if(fuel < 0){
                throw new CrashedException(id);
            }
            if(i == 2)
                throw new SpeedLimitException(id);
            if(currHeight < map.heights[x][y]){
                System.out.println("I crashed due to height");
                throw new CrashedException(id);
            }
            return true;
        } catch(IndexOutOfBoundsException e){
            return false;
        }
    }

    public int getStartSpeed() {
        return startSpeed;
    }
    Timer timer;
    public void startTimer(Map map, long delay, long period,Sidebar sidebar){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!updatePosition(map)) {
                        map.landed.incrementAndGet();
                        sidebar.addText("Landed: " + id);
                        timer.cancel();
                    }
                } catch (CrashedException e){
                    crash(map,sidebar);
                } catch (SpeedLimitException e){
                    timer.cancel();
                    startTimer(map,(long) (20.0/speed * 60 / 5 * 1000 ),(long) (20.0/speed * 60 / 5 * 1000 ),sidebar);
                }
                map.repaint();
            }
        }, delay, period);
    }

    public void crash(Map map, Sidebar sidebar) {
        if(!crashed) {
            map.crashed.incrementAndGet();
            crashed = true;
            sidebar.addText("Crash: " + id);
        }
        timer.cancel();
    }
}
