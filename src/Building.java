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
    private Map<Integer,TreeMap<Integer,Integer>> floorDudes;

    private NavigableSet<Integer> waitToUp = new TreeSet<>();
    private NavigableSet<Integer> waitToDown = new TreeSet<>();

    public NavigableSet<Integer> getWaitToUp() {
        return waitToUp;
    }

    public void setWaitToUp(NavigableSet<Integer> waitToUp) {
        this.waitToUp = waitToUp;
    }

    public NavigableSet<Integer> getWaitToDown() {
        return waitToDown;
    }

    public void setWaitToDown(NavigableSet<Integer> waitToDown) {
        this.waitToDown = waitToDown;
    }

    public Map<Integer, TreeMap<Integer, Integer>> getFloorDudes() {
        return floorDudes;
    }


    public void setFloorDudes(Map<Integer, TreeMap<Integer, Integer>> floorDudes) {
        this.floorDudes = floorDudes;
    }


    private void fillMap(){
                for (int i = 1; i <=countOfFloor; i++) {

            int countOfDudes = random.nextInt(10);
            System.out.println("FLOOR IS--" + i + " countOfDudes is--" + countOfDudes );
            System.out.println();


                    TreeMap<Integer, Integer> integerIntegerTreeMap = new TreeMap<>();
                for (int k=1; k <= countOfDudes;k++){


                    int randomForDude = getRandomForDude(random, 1, countOfFloor, i);
                    if (randomForDude > i){
                        waitToUp.add(i);
                    }else {
                        waitToDown.add(i);
                    }

                    integerIntegerTreeMap.put(randomForDude,integerIntegerTreeMap
                            .containsKey(randomForDude) ?
                            integerIntegerTreeMap.get(randomForDude)+1 : 1);
                    floorDudes.put(i,integerIntegerTreeMap);

                }
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
