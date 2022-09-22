import java.util.*;

public class Lift {



    public Lift(Random random,Building building) {
        this.random = random;
        this.building = building;
        currentFloor =  building.getWaitToUp().first();
        elevatorDurability = 50;
    }


    private int currentElevatorDestination;
    private int freePlaces = 5;

    private int elevatorDirection =0;
    private int elevatorDurability;

    Building building;
    private Random random;
    private boolean isStart = true;



    private Floor currentFloor;

    private NavigableMap<Integer,List<Dude>> liftNavigable = new TreeMap<>();




    private   void unloadDudesFromElevator(){
        boolean notEmptyDirection = elevatorDirection == 0 ?  currentFloor.getDudesToUp() !=null && !currentFloor.getDudesToUp().isEmpty()
                : currentFloor.getDudesToDown() !=null && !currentFloor.getDudesToDown().isEmpty()  ;

        System.out.println(notEmptyDirection + " NOT EMPTY _______________AAAA");
        int floorNumber = currentFloor.getFloorNumber();

        List<Dude> countOfDudesToOut = liftNavigable.get(floorNumber);
        if (freePlaces > 0 && countOfDudesToOut==null){
            if (liftNavigable.isEmpty()){

                findNewDirection();
                System.out.println("elevator is empty// new direction is  "  + elevatorDirection );
            }

            loadDudesIntoElevator();
            System.out.println("was free");
        }else {

            if (countOfDudesToOut != null) {
                freePlaces += countOfDudesToOut.size();
                System.out.println(countOfDudesToOut.size() + "  ______countOfDudesToOut_______ ");
                System.out.println(freePlaces + "  ______freePlaces_______ ");
                liftNavigable.remove(floorNumber);


                NavigableMap<Integer, LinkedList<Dude>> dudesToUp = currentFloor.getDudesToUp();
                NavigableMap<Integer, LinkedList<Dude>> dudesToDown = currentFloor.getDudesToDown();


                //choose a new direction or keep moving in the current direction
                System.out.println(liftNavigable.isEmpty() + " LIFT IS EMPTY___________________________________");
                if (liftNavigable.isEmpty()){

                    findNewDirection();

                    System.out.println("elevator is empty// new direction is  "  + elevatorDirection + "  current floor is-->>" + currentFloor   );
                    System.out.println();


                }


                        loadDudesIntoElevator();




                for (int k = 1; k <= countOfDudesToOut.size(); k++) {
                    int randomForDude = getRandomForDude(random, 1, building.countOfFloor, floorNumber);

                    if (randomForDude > floorNumber){
                        LinkedList<Dude> dudes = dudesToUp.get(randomForDude);
                        dudes = dudes == null ? new LinkedList<>() : dudes;
                        dudes.addLast(countOfDudesToOut.get(k-1));
                        dudesToUp.put(randomForDude,dudes);
                        building.getWaitToUp().add(building.getFloorDudes().get(randomForDude));
                    }else {
                        LinkedList<Dude> dudes = dudesToDown.get(randomForDude);
                        dudes = dudes == null ? new LinkedList<>() : dudes;
                        dudes.addLast(countOfDudesToOut.get(k-1));
                        dudesToDown.put(randomForDude,dudes);
                        building.getWaitToDown().add(building.getFloorDudes().get(randomForDude));
                    }


                }

            }
            System.out.println(floorNumber + "   currentFloor int out____________________!!!____");
        }
    }



    private   void loadDudesIntoElevator(){

        System.out.println(" IN LOAD ");




        System.out.println(currentFloor.getDudesToDown() + " DUDES TO DOWN");
        System.out.println(currentFloor.getDudesToUp() + " DUDES TO UP");
        if (elevatorDirection ==0 ? !currentFloor.getDudesToUp().isEmpty() : !currentFloor.getDudesToDown().isEmpty()) {

            int floorNumber = currentFloor.getFloorNumber();
            System.out.println(building + "  building  " + currentFloor + " currentFloor");
//        TreeMap<Integer, Integer> dudesQue = building.getFloorDudes().get(floorNumber);
            NavigableMap<Integer, LinkedList<Dude>> dudesQue = elevatorDirection == 0 ? currentFloor.getDudesToUp() : currentFloor.getDudesToDown();
            System.out.println(dudesQue + " QUE_________");
            Integer higherKey = dudesQue.firstKey(); //higherKey means the key higher than the current


            while (freePlaces > 0 && higherKey != null) {
                LinkedList<Dude> dudes = dudesQue.get(higherKey);
                System.out.println(higherKey + " NEXT DUDE IS");

                Integer key = higherKey;
                int amountOfDudes = dudes.size();


                if (amountOfDudes <= freePlaces) {
                    dudesQue.remove(key);
                    NavigableSet<Floor> removeWaitingDudes = elevatorDirection == 0 ? building.getWaitToUp() : building.getWaitToDown();
                    removeWaitingDudes.remove(currentFloor);

                    freePlaces -= amountOfDudes;
//                liftNavigable.put(key,liftNavigable.containsKey(key) ? liftNavigable.get(key) + value : value);
                    liftNavigable.put(key, liftNavigable.containsKey(key) ? addDudes(liftNavigable.get(key), dudes) : new ArrayList<>(dudes));
                } else {
//                Integer integer = amountOfDudes - freePlaces;
//                int i = integer - freePlaces;
//                dudesQue.put(key,i);
                    liftNavigable.put(key, liftNavigable.containsKey(key) ? addDudes(liftNavigable.get(key), dudes, freePlaces) : addDudes(new ArrayList<>(), dudes, freePlaces));
                    freePlaces = 0;
                }
                System.out.println(dudesQue + " DUDESQUE");
                System.out.println(freePlaces + " FRESPACES");


                currentElevatorDestination = Math.max(currentElevatorDestination, key);


                higherKey = dudesQue.higherKey(key);

            }
        }
        System.out.println("OOOOOOOOOOOOOOOOOOOO____________" + liftNavigable);
        findNextFloorToUploadDudes();
    }

    private List<Dude> addDudes(List<Dude> to,List<Dude> from) {
         to.addAll(from);
         return to;
    }
    private List<Dude> addDudes(List<Dude> to,LinkedList<Dude> from, int numberOfDudes){

        for (int i = 0; i < numberOfDudes; i++){
            to.add(from.pollFirst());
        }
        return to;
    }



    private boolean isElevatorFull(){
       return freePlaces >= 0;
    }



    public void start(){

        System.out.println();
        System.out.println(building.countOfFloor + " building.countOfFloor " + liftNavigable.isEmpty());
        System.out.println();

            elevatorDirection = 0;



        while (moveUntilElevatorBreaksDown()  && !(liftNavigable.isEmpty() && building.getWaitToDown().isEmpty() && building.getWaitToUp().isEmpty())){
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

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("we can move " +  currentFloor);

        //stage one
        if (isStart) {
            System.out.println("________________________________START_________________________________");
            isStart = false;
            loadDudesIntoElevator();
        }

        //stage three


                unloadDudesFromElevator();






    }

    private void findNewDirection() {
        if (!currentFloor.getDudesToDown().isEmpty() && !currentFloor.getDudesToUp().isEmpty()) {

            NavigableMap<Integer, LinkedList<Dude>> dudesToDown = currentFloor.getDudesToDown();
            NavigableMap<Integer, LinkedList<Dude>> dudesToUp = currentFloor.getDudesToUp();

            elevatorDirection = dudesToUp.size() > dudesToDown.size() ? 0 : 1;


        }else  if (currentFloor.getDudesToDown().isEmpty() && !currentFloor.getDudesToUp().isEmpty()){
            elevatorDirection = 0;
        }else if (!currentFloor.getDudesToDown().isEmpty() && currentFloor.getDudesToUp().isEmpty()){
            elevatorDirection=1;
        }
        else {

                Floor nextHigherToUp = building.getWaitToUp().higher(currentFloor);
                Floor nextHigherToDown = building.getWaitToDown().lower(currentFloor);


                if (nextHigherToUp != null && nextHigherToDown != null) {

                    int a = nextHigherToUp.getFloorNumber() - currentFloor.getFloorNumber();
                    int b = currentFloor.getFloorNumber() - nextHigherToDown.getFloorNumber();

                    if (a < b){
                        elevatorDirection =0;
                        currentFloor = building.getWaitToUp().higher(currentFloor);
                    }else {
                        elevatorDirection =1;
                        currentFloor = building.getWaitToDown().lower(currentFloor);
                    }


                }else  if (nextHigherToUp == null && nextHigherToDown != null){

                    elevatorDirection =1;
                    currentFloor = building.getWaitToDown().lower(currentFloor);
                }else  if (nextHigherToUp != null && nextHigherToDown == null){
                    elevatorDirection =0;
                    currentFloor = building.getWaitToUp().higher(currentFloor);
                }


        }


    }



    private void changeDirection(){
        elevatorDirection = elevatorDirection == 0 ? 1 : 0;
    }

    private void findNextFloorToUploadDudes(){

        elevatorDurability--;
//        System.out.println("OOOOOOOOOOOOOOOOOOOO____________" + liftNavigable);
        Floor waiter = elevatorDirection == 0 ? building.getWaitToUp().higher(currentFloor) : building.getWaitToDown().lower(currentFloor);
        Integer nextNearestFloor = elevatorDirection == 0 ? liftNavigable.higherKey(currentFloor.getFloorNumber()) : liftNavigable.lowerKey(currentFloor.getFloorNumber());

        if (freePlaces > 0) {
            System.out.println(waiter + "  WAITERS+_+_+_+_+_+_+_+   " + building.getWaitToUp());
            System.out.println(currentFloor + " CURRENT FLOOR IS +++++++++++");
            if(nextNearestFloor!=null && waiter !=null){

                int min_Max = elevatorDirection == 0 ?  Math.min(nextNearestFloor, waiter.getFloorNumber())
                        : Math.max(nextNearestFloor, waiter.getFloorNumber());
                currentFloor = building.getFloorDudes().get(min_Max);

            }else if (nextNearestFloor == null && waiter != null){

                currentFloor = waiter;

            }else if(nextNearestFloor !=null && waiter ==null){

                currentFloor = building.getFloorDudes().get(nextNearestFloor);

            }else {
                currentFloor = elevatorDirection == 0 ? building.getWaitToDown()
                        .lower(currentFloor) : building.getWaitToUp().higher(currentFloor);
            }

        }else {
            if (nextNearestFloor!=null){
                currentFloor =   building.getFloorDudes().get(nextNearestFloor);
            }
        }

        System.out.println(" NEXT CURRENT FLOOR IS  " + currentFloor);
    }


}



