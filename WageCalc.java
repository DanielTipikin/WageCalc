import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

/*
 * Assumptions:
 * 1.no overtime
 * 2.no other bonuses
 */
public class WageCalc {
    final int FRIDAY = 5;
    final int SATURDAY = 6;
    final static double  SATURDAY_WAGE_MODIFIER = 1.5;
    public static void main(String[] args) { 
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 1 to enter a shift to the database");
        System.out.println("Enter 2 to calc a specific shift's pay");
        System.out.println("Enter 0 to exit");
        int decision = scan.nextInt();
        while (decision != 0) {
            switch (decision) {
                //
                case 0:
                    break;
                case 1:
                    System.out.println("Appending shift!");
                    appendShift();
                    break;
                case 2:
                    System.out.println(calculateShift());
                    break;
                default:
                    System.out.println("Please re-enter a valid choice");

            }
            System.out.println("Enter your next decision");
            decision = scan.nextInt();
        }

        scan.close();           
    }

    //result is first part the hourly total pay and second part is delivery bonus sum
    private static double calculateShift() {
        double minutesInShift = 0; //for overtime in the future
        double totalPay = 0;
        double regPayPerMinute = 29.12 / 60;
        int regDelivery = 3;
        int deliveryInWeekend = 4;

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

        System.out.println("Please enter the num of deliveries done");
        int deliveriesNum = scan.nextInt();
        
        //friday - calculates regular pay until saturday modifier turns on 6
        if (shiftStart.getDayOfWeek().getValue() == 5) {
            Duration fridayFirstDuration = Duration.between(shiftStart,six);
            double minutesUntilSix = (double)(fridayFirstDuration.toMinutes());
            totalPay += minutesUntilSix * regPayPerMinute;
            Duration fridaySecondDuration = Duration.between(six,shiftEnd);
            double minutesFromSix = (double)(fridaySecondDuration.toMinutes());
            totalPay += minutesFromSix * regPayPerMinute * SATURDAY_WAGE_MODIFIER;
            totalPay += deliveriesNum * deliveryInWeekend;

        }
        //saturday - hourly starts with modifier and the modifier ends on 6
        else if (shiftStart.getDayOfWeek().getValue() == 6) {
            Duration saturdayFirstDuration = Duration.between(shiftStart,six);
            double minutesUntilSix = (double)(saturdayFirstDuration.toMinutes());
            totalPay += minutesUntilSix * regPayPerMinute * SATURDAY_WAGE_MODIFIER;
            Duration saturdaySecondDuration = Duration.between(six,shiftEnd);
            double minutesFromSix = (double)(saturdaySecondDuration.toMinutes());
            totalPay += minutesFromSix * regPayPerMinute;
            totalPay += deliveriesNum * deliveryInWeekend;
        }
        else {
            Duration shiftDuration = Duration.between(shiftStart,shiftEnd);
            double shiftMinutes = (double)(shiftDuration.toMinutes()); 
            totalPay += shiftMinutes * regPayPerMinute;
            totalPay += deliveriesNum * regDelivery;
        }
        scan.close();
        return totalPay;
    }
    // assumes the right format is put
    private static void appendShift() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a shift starting point in the format of '2022-06-03T17:00:00' (Y-M-D)");
        String startingTime = scan.nextLine();
        System.out.println("Please enter a shift ending point in the format of '2022-06-03T20:00:00' (Y-M-D)");
        String endingTime = scan.nextLine();
        System.out.println("Please enter the num of deliveries done");
        int deliveriesNum = scan.nextInt();
        
        try {
            FileWriter log = new FileWriter("./logs/log1.txt",true);
            log.append(startingTime + "->" + endingTime + " +" + deliveriesNum + " Deliveries\n");
            log.close();
        }
        catch (IOException e) {
            System.out.println("EXECPTION DETECTED!");
            e.printStackTrace();
        }
        System.out.println("End of appending");
        scan.close();
    }
}  
