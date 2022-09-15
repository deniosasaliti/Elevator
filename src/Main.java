import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Main {
        public static int min = 5;
        public static int max = 20;

    public static void main(String[] args) {




        Random random = new Random();
//        int countOfFloor = random.nextInt(max - min) + min;

//        for (int i = 1; i <=countOfFloor; i++) {
//
//            int countOfDudes = random.nextInt(10);
//            System.out.println("FLOOR IS--" + i + " countOfDudes is--" + countOfDudes );
//            System.out.println();
//
//
//
//                for (int k=1; k <= countOfDudes;k++){
//
//                    int randomForDude = getRandomForDude(random, 1, countOfFloor, i);
//                    floor.getFloorNavigation().put(randomForDude,floor.getFloorNavigation()
//                            .containsKey(randomForDude) ?
//                                    floor.getFloorNavigation().get(randomForDude)+1 : 1);
//
//                }
//
//        }





        Building building = new Building(random,min,max);
        Lift lift = new Lift(random,building);
        lift.start();





    }

    public static int getRandomForDude(Random random,int min,int max,int currentFloor){

        if (currentFloor == max) {
            max--;
        }

        int number = random.nextInt(max - min) + min;
        if (number == currentFloor){
            number++;
        }
        System.out.println(number + "  random is");
        return number;
    }
}
