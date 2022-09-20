import java.util.Objects;

public class Dude  {


    public Dude(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dude{" +
                "id=" + id +
                '}';
    }


}
