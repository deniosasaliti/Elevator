import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleLogPrintUI {



 void printElevatorBeforeLoad(Floor floor,NavigableMap<Integer,List<Dude>> elevator,int currentElevatorDirection,int amountOfUnloadDudes){



     String directionFrom = currentElevatorDirection==1 ?  "\u21CA   " : "\u21C8   ";
     System.out.println("load_unload stage");
     System.out.println(directionFrom + directionFrom + directionFrom);


     printLoadUnloadStage(floor,elevator,amountOfUnloadDudes);


 }
    void printElevatorBeforeLoad(Floor floor,NavigableMap<Integer,List<Dude>> elevator,int currentElevatorDirection,boolean isAngryFace){



        String directionFrom = currentElevatorDirection==1 ?  "\u21CA   " : "\u21C8   ";
        System.out.println("load_unload stage");
        System.out.println(directionFrom + directionFrom + directionFrom);


        printLoadUnloadStage(floor,elevator,isAngryFace);


    }




 private String buildString(StringBuilder builder,int times,String symbol){
    while (times!=0){
        builder.append(symbol);
        times--;
    }
    return  builder.toString();
 }


 private void printLoadUnloadStage(Floor floor, NavigableMap<Integer,List<Dude>> elevator,boolean isAngryFace){

     int sumDudesToUp = floor.getDudesToUp().values().stream().mapToInt(List::size).sum();
     int sumDudesToDown = floor.getDudesToDown().values().stream().mapToInt(List::size).sum();
     List<Dude> dudes =  elevator.values().stream().flatMap(List::stream).toList();



    String face = isAngryFace ? "\uD83D\uDE21" : "\u263A";



     System.out.println("-----------");

     for (int k =0; k < 5; k++){
         String stringQueueToUp = buildString(new StringBuilder(), sumDudesToUp, face);
         String stringQueueToDown = buildString(new StringBuilder(), sumDudesToDown, face);

         boolean isContain = dudes.size() > k;



         if (isContain && k ==1) {
             System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp + " \u21C8");

         }
         else if (isContain && k ==3){
             System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToDown + " \u21CA");
         } else if (isContain){
             System.out.println("|"+"\u263A"+"       |");
         }else if (k == 1){
             System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToUp + " \u21C8" );
         } else if (k == 3) {
             System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToDown + " \u21CA" );
         } else {
             System.out.println("|" + "\u2009     "+ "    |");
         }
     }
     System.out.println("-----------");

    }



    private void printLoadUnloadStage(Floor floor, NavigableMap<Integer,List<Dude>> elevator,int amountOfUnloadDudes){

        int sumDudesToUp = floor.getDudesToUp().values().stream().mapToInt(List::size).sum();
        int sumDudesToDown = floor.getDudesToDown().values().stream().mapToInt(List::size).sum();
        List<Dude> dudes =  elevator.values().stream().flatMap(List::stream).toList();







        System.out.println("-----------");

        for (int k =0; k < 5; k++){
            String stringQueueToUp = buildString(new StringBuilder(), sumDudesToUp, "\u263A");
            String stringQueueToDown = buildString(new StringBuilder(), sumDudesToDown, "\u263A");

            String dudesToOut= buildString(new StringBuilder(),amountOfUnloadDudes,"\uD83D\uDE01");

            boolean isContain = dudes.size() > k;



            if (isContain && k ==1) {
                System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp + " \u21C8");

            }
            else if (isContain && k ==3){
                System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToDown + " \u21CA");
            }else if (amountOfUnloadDudes > 0 && k==2 && isContain){
                System.out.println("|"+"\u263A"+"       ------------>>>" + dudesToOut);
            }
            else if (isContain){
                System.out.println("|"+"\u263A"+"       |");
            }else if (k == 1){
                System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToUp + " \u21C8" );
            } else if (k == 3) {
                System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToDown + " \u21CA" );
            }else if (amountOfUnloadDudes > 0 && k==2){
                System.out.println("|" + "\u2009     "+ "    ------------>>>" + dudesToOut);

            } else {
                System.out.println("|" + "\u2009     "+ "    |");
            }
        }
        System.out.println("-----------");

    }





}



//    private void printLoadUnloadStage(Floor floor, NavigableMap<Integer,List<Dude>> elevator,boolean isUnload){
//
//        int sumDudesToUp = floor.getDudesToUp().values().stream().mapToInt(List::size).sum();
//        int sumDudesToDown = floor.getDudesToDown().values().stream().mapToInt(List::size).sum();
//        List<Dude> dudes =  elevator.values().stream().flatMap(List::stream).toList();
//
//
//
//
//
//
//
//        System.out.println("-----------");
//
//        for (int k =0; k < 5; k++){
//            String stringQueueToUp = buildString(new StringBuilder(), sumDudesToUp, "\u263A");
//            String stringQueueToDown = buildString(new StringBuilder(), sumDudesToDown, "\u263A");
//
//            boolean isContain = dudes.size() > k;
//
//
//
//            if (isContain && k ==1) {
//                System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp + " \u21C8");
//
//            }
//            else if (isContain && k ==3){
//                System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToDown + " \u21CA");
//
//            }else if (isContain){
//                System.out.println("|"+"\u263A"+"       |");
//            }else if (k == 1){
//                System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToUp + " \u21C8" );
//            } else if (k == 3) {
//                System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToDown + " \u21CA" );
//            }else if (isUnload && k==2){
//
//                System.out.println("|"+"\u263A"+"       __" + "      " + stringQueueToUp + " \u21C8");
//
//
//
//            } else {
//                System.out.println("|" + "\u2009     "+ "    |");
//            }
//        }
//        System.out.println("-----------");
//
//    }