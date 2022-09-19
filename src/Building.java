import com.sun.jdi.IntegerType;

import java.util.*;

public class Building {

    public Building(Random random, int min, int max) {
        this.random = random;
        this.min = min;
        this.max = max;
        this.countOfFloor = random.nextInt(max - min) + min;
        floorDudes = new HashMap<>();
        fillMap();
    }

    private Random random;
    private int min;
    private int max;
    int countOfFloor;
    private Map<Integer,Floor> floorDudes;

    private NavigableSet<Floor> QueueForElevator = new TreeSet<>();


    private NavigableSet<Floor> waitToUp = new TreeSet<>();
    private NavigableSet<Floor> waitToDown = new TreeSet<>();

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCountOfFloor() {
        return countOfFloor;
    }

    public void setCountOfFloor(int countOfFloor) {
        this.countOfFloor = countOfFloor;
    }

    public Map<Integer, Floor> getFloorDudes() {
        return floorDudes;
    }

    public void setFloorDudes(Map<Integer, Floor> floorDudes) {
        this.floorDudes = floorDudes;
    }

    public NavigableSet<Floor> getQueueForElevator() {
        return QueueForElevator;
    }

    public void setQueueForElevator(NavigableSet<Floor> queueForElevator) {
        QueueForElevator = queueForElevator;
    }

    public NavigableSet<Floor> getWaitToUp() {
        return waitToUp;
    }

    public void setWaitToUp(NavigableSet<Floor> waitToUp) {
        this.waitToUp = waitToUp;
    }

    public NavigableSet<Floor> getWaitToDown() {
        return waitToDown;
    }

    public void setWaitToDown(NavigableSet<Floor> waitToDown) {
        this.waitToDown = waitToDown;
    }

    private void fillMap(){
                for (int i = 1; i <=countOfFloor; i++) {

            int countOfDudes = random.nextInt(10);
            System.out.println("FLOOR IS--" + i + " countOfDudes is--" + countOfDudes );
            System.out.println();


//            TreeMap<Integer, Integer> integerIntegerTreeMap = new TreeMap<>();
                    Floor floor = new Floor(i);

                for (int k=1; k <= countOfDudes;k++){
                    int id = (int) Math.pow(10, Math.floor(Math.log10(i))+1)*k + i;
                    Dude dude = new Dude(id);

                    int randomForDude = getRandomForDude(random, 1, countOfFloor, i);
                    if (randomForDude > i){
                        waitToUp.add(floor);
                        NavigableMap<Integer, LinkedList<Dude>> dudesToUp = floor.getDudesToUp();
                        LinkedList<Dude> dudes = dudesToUp.get(randomForDude);
                        dudes = dudes == null ? new LinkedList<>() : dudes;
                        dudes.addLast(dude);
                        dudesToUp.put(floor.getFloorNumber(),dudes);
                    }else {
                        NavigableMap<Integer, LinkedList<Dude>> dudesToDown = floor.getDudesToDown();
                        LinkedList<Dude> dudes = dudesToDown.get(randomForDude);
                        dudes = dudes == null ? new LinkedList<>() : dudes;
                        dudes.addLast(dude);
                        dudesToDown.put(floor.getFloorNumber(),dudes);
                        waitToDown.add(floor);
                    }

                    integerIntegerTreeMap.put(randomForDude,integerIntegerTreeMap
                            .containsKey(randomForDude) ?
                            integerIntegerTreeMap.get(randomForDude)+1 : 1);


                }
                    floorDudes.put(i,integerIntegerTreeMap);
                    System.out.println(integerIntegerTreeMap + " MAP IS");


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


}
