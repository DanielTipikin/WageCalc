import java.time.LocalDateTime;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class Tester {
    public static void main(String[] args) {
        LocalDateTime shiftStart = LocalDateTime.parse("2022-08-03T17:00:00");
        LocalDateTime shiftEnd = LocalDateTime.parse("2022-08-03T22:00:00");
        int deliveriesNum = 30;
        try {
            FileWriter log = new FileWriter("./logs/log1.txt",true);
            log.append(shiftStart + "->" + shiftEnd + " +" + deliveriesNum + " Deliveries\n");
            log.close();
        }
        catch (IOException e) {
            System.out.println("EXECPTION DETECTED!");
            e.printStackTrace();
        }
        System.out.println("Would you like another log?");
        
    }    
}
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14     
//2022-06-04T12:01:03 -> 2022-06-04T20:08:35 21     

//2022-06-10T17:46:53 -> 2022-06-10T21:02:45 9        
//2022-06-11T17:35:01 -> 2022-06-11T22:51:11 14

//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14

/*
 * 
 * 
 * try {
            FileWriter log = new FileWriter("./logs/log1.txt");
            log.append(startingTime + "->" + endingTime + " +" + deliveriesNum + " Deliveries");
            log.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
 */

 /*
  * LocalDateTime friday = LocalDateTime.parse("2022-06-03T20:00:00");
        LocalDateTime saturday = LocalDateTime.parse("2022-06-04T20:00:00");
        int fridaytester = friday.getDayOfWeek().getValue(); //5
        int saturdaytester = saturday.getDayOfWeek().getValue(); //6
        System.out.println(fridaytester +"  "+ saturdaytester);
  */