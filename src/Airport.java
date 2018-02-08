import java.awt.image.BufferedImage;

public class Airport {
    private int id;
    private AirportCategory category;
    private boolean isOpen;
    private int x,y;

    public BufferedImage getAirportImage() {
        return airportImage;
    }

    public Airport(int id, AirportCategory category, boolean isOpen, int x, int y, BufferedImage airportImage, Direction direction) {
        this.id = id;
        this.category = category;
        this.isOpen = isOpen;
        this.x = x;
        this.y = y;
        this.airportImage = airportImage;
        this.direction = direction;
    }

    public void setAirportImage(BufferedImage airportImage) {
        this.airportImage = airportImage;
    }

    private BufferedImage airportImage;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AirportCategory getCategory() {
        return category;
    }

    public void setCategory(AirportCategory category) {
        this.category = category;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private Direction direction;
    public enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }
    public enum AirportCategory {
        ONE,
        TWO,
        THREE
    }
}
