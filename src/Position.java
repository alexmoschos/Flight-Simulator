import java.util.ArrayList;
import java.util.Objects;

public class Position {
    int x,y,height;

    public Position(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return Integer.toString(x) + " " + Integer.toString(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    public ArrayList<Position> neighbors() {
        ArrayList<Position> result = new ArrayList<>();
        if(x < 30) result.add(new Position(x+1,y));
        if(x < 30 && y < 60) result.add(new Position(x+1,y+1));

        if(y < 60) result.add(new Position(x,y+1));
        if(x > 0 && y < 60) result.add(new Position(x-1,y+1));

        if(x > 0) result.add(new Position(x-1,y));
        if(x > 0 && y > 0) result.add(new Position(x-1,y-1));

        if(y > 0) result.add(new Position(x,y-1));
        if(x < 30 && y > 0) result.add(new Position(x-1,y+1));


        return result;
    }
}
