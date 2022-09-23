import java.util.*;

public class Lift {




    public Lift(Random random, Building building, ConsoleLogPrintUI printUI) {
        this.printUI = printUI;
        this.random = random;
        this.building = building;
        currentFloor =  building.getWaitToUp().first();
        elevatorDurability = 50;
    }

    private final ConsoleLogPrintUI printUI;
    private int currentElevatorDestination;
    private int freePlaces = 5;

    private int elevatorDirection =0;
    private int elevatorDurability;

    Building building;
    private final Random random;
    private boolean isStart = true;



    private Floor currentFloor;

    private NavigableMap<Integer,List<Dude>> liftNavigable = new TreeMap<>();




    private   void unloadDudesFromElevator(){


        int floorNumber = currentFloor.getFloorNumber();

        List<Dude> countOfDudesToOut = liftNavigable.get(floorNumber);
        if (freePlaces > 0 && countOfDudesToOut==null){
            if (liftNavigable.isEmpty()){

                findNewDirection();
            }

            loadDudesIntoElevator();

        }else {

            if (countOfDudesToOut != null) {

                freePlaces += countOfDudesToOut.size();

                liftNavigable.remove(floorNumber);
                printUI.printElevatorBeforeLoad(currentFloor,liftNavigable,elevatorDirection,countOfDudesToOut.size());





                NavigableMap<Integer, LinkedList<Dude>> dudesToUp = currentFloor.getDudesToUp();
                NavigableMap<Integer, LinkedList<Dude>> dudesToDown = currentFloor.getDudesToDown();

                if (liftNavigable.isEmpty()){
                    if (!isCurrentFloorEmpty()){
                        findNewDirection();
                    }else {
                        findNextFloorWhenElevatorIsEmpty();
                        findNewDirection();
                    }




                }


                        loadDudesIntoElevator();



                FillFloorWithDudes(floorNumber,countOfDudesToOut,dudesToUp,dudesToDown);


            }

        }
    }

    private void FillFloorWithDudes(int floorNumber,List<Dude> countOfDudesToOut,NavigableMap<Integer, LinkedList<Dude>> dudesToUp,NavigableMap<Integer, LinkedList<Dude>> dudesToDown) {
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

    private void findNextFloorWhenElevatorIsEmpty() {


            Floor nextHigherToUp = building.getWaitToUp().higher(currentFloor);
            Floor nextHigherToDown = building.getWaitToDown().lower(currentFloor);


            if (nextHigherToUp != null && nextHigherToDown != null) {

                int a = nextHigherToUp.getFloorNumber() - currentFloor.getFloorNumber();
                int b = currentFloor.getFloorNumber() - nextHigherToDown.getFloorNumber();


                if (a < b){
                    currentFloor = building.getWaitToUp().higher(currentFloor);

                }else {
                    currentFloor = building.getWaitToDown().lower(currentFloor);

                }



            }else  if (nextHigherToUp == null && nextHigherToDown != null){

                currentFloor = building.getWaitToDown().lower(currentFloor);

            }else  if (nextHigherToUp != null){

                currentFloor = building.getWaitToUp().higher(currentFloor);

            }

    }

    private boolean isCurrentFloorEmpty() {
       return currentFloor.getDudesToDown().isEmpty() && currentFloor.getDudesToUp().isEmpty();
    }


    private   void loadDudesIntoElevator(){


        if (elevatorDirection ==0 ? !currentFloor.getDudesToUp().isEmpty() : !currentFloor.getDudesToDown().isEmpty()) {






            NavigableMap<Integer, LinkedList<Dude>> dudesQue = elevatorDirection == 0 ? currentFloor.getDudesToUp() : currentFloor.getDudesToDown();

            Integer higherKey = dudesQue.firstKey(); //higherKey means the key higher than the current


            while (freePlaces > 0 && higherKey != null) {
                LinkedList<Dude> dudes = dudesQue.get(higherKey);

                Integer key = higherKey;
                int amountOfDudes = dudes.size();


                if (amountOfDudes <= freePlaces) {
                    dudesQue.remove(key);
                    NavigableSet<Floor> removeWaitingDudes = elevatorDirection == 0 ? building.getWaitToUp() : building.getWaitToDown();
                    removeWaitingDudes.remove(currentFloor);

                    freePlaces -= amountOfDudes;
                    liftNavigable.put(key, liftNavigable.containsKey(key) ? addDudes(liftNavigable.get(key), dudes) : new ArrayList<>(dudes));
                } else {

                    liftNavigable.put(key, liftNavigable.containsKey(key) ? addDudes(liftNavigable.get(key), dudes, freePlaces) : addDudes(new ArrayList<>(), dudes, freePlaces));
                    freePlaces = 0;
                }



                currentElevatorDestination = Math.max(currentElevatorDestination, key);


                higherKey = dudesQue.higherKey(key);

            }
        }

        printUI.printElevatorBeforeLoad(currentFloor,liftNavigable,elevatorDirection,true);


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







    public void start(){



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

        return number;
    }



    private void turnOnElevator(){
        System.out.println("____________________________________________________________________________________");

        printUI.printElevatorBeforeLoad(currentFloor,liftNavigable,elevatorDirection,false);


        //stage one
        if (isStart) {

            loadDudesIntoElevator();
        }

        //stage three

                if (!isStart) {
                    unloadDudesFromElevator();
                }

            isStart = false;


        System.out.println("____________________________________________________________________________________");
    }

    private void findNewDirection() {



        if (!currentFloor.getDudesToDown().isEmpty() && !currentFloor.getDudesToUp().isEmpty()) {
            NavigableMap<Integer, LinkedList<Dude>> dudesToDown = currentFloor.getDudesToDown();
            NavigableMap<Integer, LinkedList<Dude>> dudesToUp = currentFloor.getDudesToUp();


            if (dudesToUp.size() > dudesToDown.size()){
                elevatorDirection = 0;
            } else if (dudesToUp.size() < dudesToDown.size()) {
                elevatorDirection = 1;
            }else {
                changeDirection();
            }


        }else  if (currentFloor.getDudesToDown().isEmpty() && !currentFloor.getDudesToUp().isEmpty()){
            elevatorDirection = 0;
        }else if (!currentFloor.getDudesToDown().isEmpty() && currentFloor.getDudesToUp().isEmpty()){
            elevatorDirection=1;
        }

    }



    private void changeDirection(){
        elevatorDirection = elevatorDirection == 0 ? 1 : 0;
    }

    private void findNextFloorToUploadDudes(){

        elevatorDurability--;

        Floor waiter = elevatorDirection == 0 ? building.getWaitToUp().higher(currentFloor) : building.getWaitToDown().lower(currentFloor);
        Integer nextNearestFloor = elevatorDirection == 0 ? liftNavigable.higherKey(currentFloor.getFloorNumber()) : liftNavigable.lowerKey(currentFloor.getFloorNumber());

        if (freePlaces > 0) {

            if(nextNearestFloor!=null && waiter !=null){

                int min_Max = elevatorDirection == 0 ?  Math.min(nextNearestFloor, waiter.getFloorNumber())
                        : Math.max(nextNearestFloor, waiter.getFloorNumber());
                currentFloor = building.getFloorDudes().get(min_Max);

            }else if (nextNearestFloor == null && waiter != null){

                currentFloor = waiter;

            }else if(nextNearestFloor != null){

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


    }


}



