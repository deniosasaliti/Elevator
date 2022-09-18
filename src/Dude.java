import java.util.Objects;

public class Dude implements Comparable<Dude> {
    public Dude(int destinationFloor) {
        this.destinationFloor = destinationFloor;

    }

    private int destinationFloor;


    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    @Override
    public int compareTo(Dude dude) {
        return Integer.compare(destinationFloor, dude.destinationFloor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dude dude = (Dude) o;
        return destinationFloor == dude.destinationFloor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationFloor);
    }
}
