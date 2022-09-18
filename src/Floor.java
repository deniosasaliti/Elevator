import java.util.*;

public class Floor implements Comparable<Floor> {

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    private int floorNumber;


    private NavigableMap<Integer,LinkedList<Dude>> dudesToUp  = new TreeMap<>();

    private NavigableMap<Integer,LinkedList<Dude>> dudesToDown = new TreeMap<>();

    public NavigableMap<Integer, LinkedList<Dude>> getDudesToUp() {
        return dudesToUp;
    }

    public void setDudesToUp(NavigableMap<Integer, LinkedList<Dude>> dudesToUp) {
        this.dudesToUp = dudesToUp;
    }

    public NavigableMap<Integer, LinkedList<Dude>> getDudesToDown() {
        return dudesToDown;
    }

    public void setDudesToDown(NavigableMap<Integer, LinkedList<Dude>> dudesToDown) {
        this.dudesToDown = dudesToDown;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }


    @Override
    public int compareTo(Floor o) {
      return   Integer.compare(floorNumber,o.floorNumber);
    }
}
