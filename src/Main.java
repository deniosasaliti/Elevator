import com.sun.jdi.IntegerType;

import java.util.*;

public class Main {
    public static int min = 5;
    public static int max = 20;

    public static void main(String[] args) {


        Random random = new Random();

        ConsoleLogPrintUI printUI = new ConsoleLogPrintUI();
        Building building = new Building(random,min,max);
        Lift lift = new Lift(random,building,printUI);
        lift.start();













    }
}
