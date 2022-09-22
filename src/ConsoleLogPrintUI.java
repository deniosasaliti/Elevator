import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;

public class ConsoleLogPrintUI {



 void printElevatorBeforeLoad(){
        int direction=1;
     LinkedList<Dude> dudes = new LinkedList<>();
     dudes.add(new Dude(1));
//     dudes.add(new Dude(5));
//     dudes.add(new Dude(13));
//     dudes.add(new Dude(78));
//     dudes.add(new Dude(34));

    Floor floor = new Floor(1);
    int waitToUpSize =7;
    String symbol = "\u263A";

    StringBuilder builder = new StringBuilder();
     String stringQueueToUp = buildString(builder, waitToUpSize, symbol);


     String directionFrom = direction==1 ?  "\u21CA   " : "\u21C8   ";
     System.out.println("load_unload stage");
     System.out.println(directionFrom + directionFrom + directionFrom);
     System.out.println("-----------");


     for (int k =0; k < 5; k++){

         Dude dude = dudes.pollFirst();
         if (dude !=null) {


             if (k ==1){
                 System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp + " \u21C8");
             }else if (k==3){
                 System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp + " \u21CA");
             } else{
                 System.out.println("|"+"\u263A"+"       |");
             }



         } else  {
             if (k==1){
                 System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToUp + " \u21C8" );
             }else if (k==3){
                 System.out.println("|" + "\u2009     "+ "    |" + "      " + stringQueueToUp + " \u21CA" );
             }else {
                 System.out.println("|" + "\u2009     "+ "    |");
             }

         }
     }
     System.out.println("-----------");




     LinkedList<Dude> dudes2 = new LinkedList<>();
     dudes2.add(new Dude(1));
//     dudes2.add(new Dude(5));
//     dudes.add(new Dude(13));
//     dudes.add(new Dude(78));
//     dudes.add(new Dude(34));


     String stringQueueToUp2 = buildString(new StringBuilder(), 2, "\u263A");

     System.out.println("-----------");

    for (int i =0; i < 5; i++){

        Dude dude2 = dudes2.pollFirst();
        if (dude2 !=null) {


            if (i ==1){
                System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp2 + " \u21C8");
            }else if (i==3){
                System.out.println("|"+"\u263A"+"       |" + "      " + stringQueueToUp2 + " \u21CA");
            } else{
                System.out.println("|"+"\u263A"+"       |");
            }



        } else  {
            System.out.println("|" + "\u2009     "+ "    |");
        }
    }
     System.out.println("-----------");
     System.out.println("next floor is " + 3);

 }
 private String buildString(StringBuilder builder,int times,String symbol){
    while (times!=0){
        builder.append(symbol);
        times--;
    }
    return  builder.toString();
 }

}




