import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

/*
 * Assumptions:
 * 1. I work only on the weekends (Delivery bonus is 4 instead of 3)
 */
public class WageCalc {
    public static void main(String[] args) { 
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 1 to calc shift's pay");
        System.out.println("Enter 2 to calc next paycheck");
        System.out.println("Enter 0 to exit");
        int decision = scan.nextInt();
        while (decision != 0) {
            switch (decision) {
                //get shift's pay (and put in the DATABASE)
                case 1:
                    System.out.println(registerShift());
                    break;
                case 2:
                    //calcPaycheck()  Calculate next paycheck
                    break;    


            }
            System.out.println("Enter your next decision");
            decision = scan.nextInt();
        }

        scan.close();           
    }
    //result is first part the hourly total pay and second part is delivery bonus sum
    public static double registerShift() {
        double totalPay = 0;
        double regPayPerMinute = 29.12 / 60;
        final double SATURDAY_WAGE_MODIFIER = 1.5;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a shift starting point in the format of '2022-06-03T17:00:00' (Y-M-D)");
        String startingTime = scan.nextLine();
        LocalDateTime shiftStart = LocalDateTime.parse(startingTime);
        
        System.out.println("Please enter a shift ending point in the format of '2022-06-03T20:00:00' (Y-M-D)");
        String endingTime = scan.nextLine();
        LocalDateTime shiftEnd = LocalDateTime.parse(endingTime);
        
        //Sets up a 18:00 object to make calculations with
        String untilSixHelper = startingTime.substring(0, 10) + "T18:00:00";
        LocalDateTime six = LocalDateTime.parse(untilSixHelper);
        
        //if () A morning or evening shift fork!

        Duration firstDuration = Duration.between(shiftStart,six);
        int minutesUntilSix = (int)(firstDuration.toMinutes());
        totalPay += minutesUntilSix * regPayPerMinute;
        
        //Calculates how much money is made from 6 to end of shift (1.5x modifier)
        Duration secondDuration = Duration.between(six,shiftEnd);
        int minutesFromSix = (int)(secondDuration.toMinutes());
        totalPay += minutesFromSix * regPayPerMinute * SATURDAY_WAGE_MODIFIER;

        //Calculates how much money is made from delivery bonuses
        System.out.println("Please enter the num of deliveries done");
        int deliveriesNum = scan.nextInt();
        totalPay += deliveriesNum * 4;

        scan.close();



        return totalPay;
    }
 
}  
