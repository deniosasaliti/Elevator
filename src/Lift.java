import java.util.*;

public class Lift {



    public Lift(Random random,Building building) {
        this.random = random;
        this.building = building;
    }


    private int currentElevatorDestination;
    private int freePlaces =5;
    private static int maxSize =5;
    private int elevatorDirection =0;
    private int elevatorDurability = 50;

    Building building;
    private Random random;
    private boolean isElevatorStart = false;


    private Floor currentFloor;
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



    public void start(){

        System.out.println();
        System.out.println(building.countOfFloor + " building.countOfFloor " + liftNavigable.isEmpty());
        System.out.println();

            elevatorDirection = 0;



        while (moveUntilElevatorBreaksDown()  && (liftNavigable.isEmpty() && building.getWaitToDown().isEmpty() && building.getWaitToUp().isEmpty())  ){
            turnOnElevator();
        }

    }

    private boolean moveUntilElevatorBreaksDown() {
       return elevatorDurability > 0;
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



    private void turnOnElevator(){
        System.out.println("we can move " +  currentFloor);

        if (currentFloor !=1) {
            unloadDudesFromElevator();
        }
//        if (currentFloor==1) {
//            loadDudesIntoElevator();
//        }
        Floor waiter = building.getWaitToUp().higher(currentFloor);
        Integer higher = liftNavigable.higherKey(currentFloor.getFloorNumber());

            if (freePlaces > 0) {
                System.out.println(waiter + "  WAITERS+_+_+_+_+_+_+_+   " + building.getWaitToUp());
                System.out.println(currentFloor + " CURRENT FLOOR IS +++++++++++");
//                currentFloor = building.getWaitToUp().higher(currentFloor);
                if (waiter != null && waiter.getFloorNumber() < higher){
                    currentFloor = waiter;
                }else if (higher!=null){
                    currentFloor = higher;
                }
            }
            System.out.println(liftNavigable + " liftNavigable");


        if (liftNavigable.isEmpty()){
            findNewDirection();
        }


    }

    private void findNewDirection() {
        if (!currentFloor.getDudesToDown().isEmpty() && !currentFloor.getDudesToUp().isEmpty()) {

            NavigableMap<Integer, ArrayList<Dude>> dudesToDown = currentFloor.getDudesToDown();
            NavigableMap<Integer, ArrayList<Dude>> dudesToUp = currentFloor.getDudesToUp();

           elevatorDirection = dudesToUp.size() < dudesToDown.size() ? 0 : 1;
        }else  if (currentFloor.getDudesToDown().isEmpty() && !currentFloor.getDudesToUp().isEmpty()){
            elevatorDirection = 1;
        }else if (!currentFloor.getDudesToDown().isEmpty() && currentFloor.getDudesToUp().isEmpty()){
            elevatorDirection=0;
        }else {
            if (elevatorDirection == 0) {
                Floor nextHigherToUp = building.getWaitToUp().higher(currentFloor);
                Floor nextHigherToDown = building.getWaitToDown().higher(currentFloor);


                if (nextHigherToUp == null && nextHigherToDown == null) {
                    changeDirection();
                }//else keepDirection
            }else {
                Floor  nextLowerToUp = building.getWaitToUp().lower(currentFloor);
                Floor nextLowerToDown = building.getWaitToDown().lower(currentFloor);

                if (nextLowerToUp == null && nextLowerToDown == null){
                    changeDirection();
                }//else keepDirection
            }
        }


    }

    private void goneDownStairs(){

    }

    private void changeDirection(){

        elevatorDirection = elevatorDirection == 0 ? 1 : 0;
    }


}



