import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.Duration;

public class Tester {
    public static void main(String[] args) {
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
        
        //Calculates how much money is made before entering saturday hours
        String untilSixHelper = startingTime.substring(0, 10) + "T18:00:00";
        LocalDateTime six = LocalDateTime.parse(untilSixHelper);
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
        System.out.println(totalPay);
        
        
    }    
}
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14