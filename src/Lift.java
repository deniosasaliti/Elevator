import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Lift {

    private static int maxSize =5;

    public Lift(Random random,Building building) {
        this.random = random;
        this.building = building;
    }


    private int currentElevatorDestination;
    private int freePlaces =5;

    Building building;
    private Random random;
    private boolean isElevatorStart = false;


    private int currentFloor=1;
    private NavigableMap<Integer,Integer> liftNavigable = new TreeMap<>();



    public NavigableMap<Integer, Integer> getLiftNavigable() {
        return liftNavigable;
    }

    public void setLiftNavigable(NavigableMap<Integer, Integer> liftNavigable) {
        this.liftNavigable = liftNavigable;
    }
    private   void unloadDudesFromElevator(){

        int floorNumber = currentFloor;
        Integer countOfDudesToOut = liftNavigable.get(floorNumber);
        if (freePlaces > 0 && countOfDudesToOut==null){
            loadDudesIntoElevator();
            System.out.println("was free");
        }else {

            if (countOfDudesToOut != null) {
                freePlaces += countOfDudesToOut;
                System.out.println(countOfDudesToOut + "  ______countOfDudesToOut_______ ");
                System.out.println(freePlaces + "  ______freePlaces_______ ");
                liftNavigable.remove(floorNumber);
                TreeMap<Integer, Integer> dudesQue = building.getFloorDudes().get(currentFloor);

                loadDudesIntoElevator();

                for (int k = 1; k <= countOfDudesToOut; k++) {
                    int randomForDude = getRandomForDude(random, 1, building.countOfFloor, currentFloor);

                    dudesQue.put(randomForDude,
                            dudesQue.containsKey(randomForDude)
                                    ? dudesQue.get(randomForDude) + 1
                                    : 1);
                }
                System.out.println(dudesQue + " DudeQue_____AAAAAAAAAAAAAAAAAAAAAAAAAAA___AAA");
            }
            System.out.println(currentFloor + "   currentFloor int out____________________!!!____");
        }
    }



    private   void loadDudesIntoElevator(){

        int floorNumber = currentFloor;
        System.out.println(building + "  building  " + currentFloor + " currentFloor");
        TreeMap<Integer, Integer> dudesQue = building.getFloorDudes().get(floorNumber);
        System.out.println(dudesQue + " QUE_________");
        Integer higherKey = dudesQue.higherKey(currentFloor);

        while (freePlaces > 0 && higherKey !=null ){

            System.out.println(higherKey + " NEXT DUDE IS");

            Integer key = higherKey;
            Integer value = dudesQue.get(higherKey);


            if (value <= freePlaces){
                dudesQue.remove(key);
                freePlaces -=value;
                liftNavigable.put(key,liftNavigable.containsKey(key) ? liftNavigable.get(key) + value : value);
            }else {
                Integer integer = dudesQue.get(key);
                int i = integer - freePlaces;
                dudesQue.put(key,i);
                liftNavigable.put(key,liftNavigable.containsKey(key) ? liftNavigable.get(key) + freePlaces : freePlaces);
                freePlaces=0;
            }
            System.out.println(dudesQue + " DUDESQUE");
            System.out.println(freePlaces + " FRESPACES");


            currentElevatorDestination = Math.max(currentElevatorDestination,key);



            higherKey = dudesQue.higherKey(key);

        }
    }




    private boolean isElevatorFull(){
       return freePlaces >= 0;
    }
    private boolean canMove = !liftNavigable.isEmpty();


    public void start(){

        System.out.println();
        System.out.println(building.countOfFloor + " building.countOfFloor " + liftNavigable.isEmpty());

        System.out.println();


            loadDudesIntoElevator();



        while (!canMove){
            directionToUp();
        }

    }

    private   int getRandomForDude(Random random,int min,int max,int currentFloor){


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

    private void directionToDown(){
        System.out.println();
        System.out.println();
        System.out.println("__________________________________________________DIRECTION WAS CHANCHED__________________________________________________");
        System.out.println();
        System.out.println();
        canMove = true;


    }

    private void directionToUp(){
        System.out.println("we can move " +  currentFloor);

        if (currentFloor !=1) {
            unloadDudesFromElevator();
        }
//        if (currentFloor==1) {
//            loadDudesIntoElevator();
//        }
        Integer waiter = building.getWaitToUp().higher(currentFloor);
        Integer higher = liftNavigable.higherKey(currentFloor);
        if (higher!=null && building.getFloorDudes().get(currentFloor).higherKey(currentFloor)==null
                && building.getFloorDudes().get(currentFloor).lowerKey(currentFloor)!=null){
            directionToDown();
        }else {
            if (freePlaces > 0) {

                System.out.println(waiter + "  WAITERS+_+_+_+_+_+_+_+   " + building.getWaitToUp());
                System.out.println(currentFloor + " CURRENT FLOOR IS +++++++++++");
//                currentFloor = building.getWaitToUp().higher(currentFloor);
                if (waiter != null)
                    currentFloor = Math.min(waiter, higher);
                else currentFloor = higher;

            } else currentFloor = higher;
            System.out.println(liftNavigable + " liftNavigable");
        }

    }



}



