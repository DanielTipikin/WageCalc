import java.time.LocalDateTime;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class Tester {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int decision = 99;
        while (decision != 0) {
            System.out.println("Enter 1 to enter a shift to the database");
            System.out.println("Enter 2 to calc a specific shift's pay");
            System.out.println("Enter 3 to test");
            System.out.println("Enter 0 to exit");
            decision = scan.nextInt();
            scan.nextLine();
            switch (decision) {
                //
                case 0:
                    break;
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                case 3:
                    System.out.println("Case 3");
                    break;    
                default:
                    System.out.println("Please re-enter a valid choice");

            }
            
        }
           
    }
}
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14     
//2022-06-04T12:01:03 -> 2022-06-04T20:08:35 21     

//2022-06-10T17:46:53 -> 2022-06-10T21:02:45 9        
//2022-06-20T17:46:53->2022-06-20T21:02:45 +25 Deliveries

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