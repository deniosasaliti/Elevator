import java.util.*;

public class Floor {

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    private int floorNumber;

    private int dudesSizeToUp;
    private int dudesSizeToDown;
    private NavigableMap<Integer,Integer> floorNavigation = new TreeMap<>();

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }



    public NavigableMap<Integer, Integer> getFloorNavigation() {
        return floorNavigation;
    }

    public void setFloorNavigation(NavigableMap<Integer, Integer> floorNavigation) {
        this.floorNavigation = floorNavigation;
    }
}
