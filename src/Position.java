import java.util.ArrayList;
import java.util.Objects;

public class Position {
    int x,y,height;
    double priority;
    //Position constructor without priority for starting positions
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //Position constructor with priority for internal positions used by dijkstra
    public Position(int x, int y,double priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
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
        //hash code is needed for HashMap
        return Objects.hash(x, y);
    }

    public ArrayList<Position> neighbors() {
        //create the right list of neighbors
        ArrayList<Position> result = new ArrayList<>();
        if(x < 30) result.add(new Position(x+1,y,priority + 1));
        if(x < 30 && y < 60) result.add(new Position(x+1,y+1,priority+Math.sqrt(2)));

        if(y < 60) result.add(new Position(x,y+1,priority + 1));
        if(x > 0 && y < 60) result.add(new Position(x-1,y+1,priority+Math.sqrt(2)));

        if(x > 0) result.add(new Position(x-1,y,priority + 1));
        if(x > 0 && y > 0) result.add(new Position(x-1,y-1,priority+Math.sqrt(2)));

        if(y > 0) result.add(new Position(x,y-1,priority + 1));
        if(x < 30 && y > 0) result.add(new Position(x+1,y-1,priority+Math.sqrt(2)));

        return result;
    }
}
