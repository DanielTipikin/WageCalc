import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

/* @Very basic & personal wage calculater to get some experience before the big projects!
 * Assumptions:
 * 1.no overtime
 * 2.no other bonuses
 * 
 */
public class WageCalc {
    final static int FRIDAY = 5;
    final static int SATURDAY = 6;
    final static double  SATURDAY_WAGE_MODIFIER = 1.5;
    final static String SATURDAY_TIME = "T18:00:00";
    public static void main(String[] args) { 
        Scanner scan = new Scanner(System.in);
        int decision = 99;
        while (decision != 0) {
            System.out.println("Enter 1 to enter a shift to the database");
            System.out.println("Enter 2 to calc a specific shift's pay");
            System.out.println("Enter 3 to calc a specific month's wage");
            System.out.println("Enter 0 to exit");
            decision = scan.nextInt();
            scan.nextLine();
            switch (decision) {
                case 0:
                    break;
                case 1:
                    System.out.println("Appending shift!");
                    appendShift();
                    break;
                case 2:
                    //System.out.println(calculateShift());
                    break;
                case 3:
                    System.out.println("Which month would you like to calc? (Enter Y-M in the format of: 2022-06");
                    String month = scan.nextLine();
                    System.out.println("The total for " + month +"  is: " + calcMonthlyWage(month));
                    break;    
                default:
                    System.out.println("Please re-enter a valid choice");

            }
            
        }
        scan.close();   
    }

    //result is first part the hourly total pay and second part is delivery bonus sum
    private static double calculateShift(LocalDateTime[] data) {
        double minutesInShift = 0; //for overtime in the future
        double totalPay = 0;
        double regPayPerMinute = 29.12 / 60;
        int regDelivery = 3;
        int deliveryInWeekend = 4;

        LocalDateTime shiftStart = data[0];

        LocalDateTime shiftEnd = data[1];
        
        //Sets up a 18:00 object to make calculations with
        int year = shiftStart.getYear();
        int month = shiftStart.getMonthValue();
        int day = shiftStart.getDayOfMonth();
        LocalDateTime six = LocalDateTime.of(year ,month ,day ,18 ,0 ,0);

        int deliveriesNum = data[2].getMinute();

        //friday - calculates regular pay until saturday modifier turns on 6
        if (shiftStart.getDayOfWeek().getValue() == FRIDAY) {
            Duration fridayFirstDuration = Duration.between(shiftStart,six);
            double minutesUntilSix = (double)(fridayFirstDuration.toMinutes());
            totalPay += minutesUntilSix * regPayPerMinute;
            Duration fridaySecondDuration = Duration.between(six,shiftEnd);
            double minutesFromSix = (double)(fridaySecondDuration.toMinutes());
            totalPay += minutesFromSix * regPayPerMinute * SATURDAY_WAGE_MODIFIER;
            totalPay += deliveriesNum * deliveryInWeekend;

        }
        //saturday - hourly starts with modifier and the modifier ends on 6
        else if (shiftStart.getDayOfWeek().getValue() == SATURDAY) {
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
        System.out.println("For shift: "+shiftStart.toString()+" -> "+ shiftEnd + " +"+ data[2].getMinute() +" totalling - "+ totalPay + "NIS");
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
        scan.nextLine();
        
        try {
            FileWriter log = new FileWriter("./logs/log1.txt",true);
            if (deliveriesNum > 9)
                log.append(startingTime + "->" + endingTime + " +" + deliveriesNum + " Deliveries\n");
            else //pads the deliveriesnum 1 to 01
                log.append(startingTime + "->" + endingTime + " +" + "0"+ deliveriesNum + " Deliveries\n");
            log.close();
        }
        catch (IOException e) {
            System.out.println("EXECPTION DETECTED!");
            e.printStackTrace();
        }
        System.out.println("End of appending");
        scan.close();
    }
    private static LocalDateTime[] parseFromLine(String logLine) {
        LocalDateTime[] result = new LocalDateTime[3];
        LocalDateTime startingTime = LocalDateTime.parse(logLine.substring(0, 19));
        LocalDateTime endingTime = LocalDateTime.parse(logLine.substring(21, 40));
        LocalDateTime deliveryHelper = LocalDateTime.parse("2100-10-10T20:" + logLine.substring(42,44) + ":00"); //minutes is num of deliveries!
        result[0] = startingTime;
        result[1] = endingTime;
        result[2] = deliveryHelper;
        return result;
    }
    private static double calcMonthlyWage(String monthToCalc) {
        double monthlyPay = 0;
        try {
            Scanner scanner = new Scanner(new File("./logs/log1.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(monthToCalc)) {
                    LocalDateTime[] data = new LocalDateTime[2];
                    data = parseFromLine(line);
                    monthlyPay += calculateShift(data);

                }
                

            }
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        } 
        return monthlyPay;
    }
}  
