import java.time.LocalDateTime;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class Tester {
    public static void main(String[] args) {
        String line = "2022-06-10T17:46:53->2022-06-10T21:02:45 +9 Deliveries";
        String subLine = line.substring(0,19);
        String subLine2= line.substring(21, 40);
        String delivery = line.substring(42,44);
        System.out.println(subLine);
        System.out.println(subLine2);
        System.out.println(delivery);

    }
}

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