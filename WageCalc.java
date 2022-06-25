import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

/* Very basic & personal wage calculater to get some experience before the big projects!
 * Assumptions:
 * 1a.Overtime- Starts at the 7th hour (Business works 6 days+)
 * 1b.Overtime- Holidays are not calculated in
 * 2.Bonus- Delivery bonuses,the only bonus available
 */
public class WageCalc {
    public static void main(String[] args) { 
        Scanner scan = new Scanner(System.in);
        int decision = -1;
        while (decision != 0) {
            System.out.println("Enter 1 to enter a shift to the database");
            System.out.println("Enter 2 to calc a specific month's wage");
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

    /*
     * Returns the shift's worth in NIS via seconds in the shift
     * Takes an array with the shift's data
     * @param data data that contains shift's starting time, ending time and number of deliveries
     * @return the shift's worth in NIS as a double
     * @see LocalDateTime 
     * @see Duration
     */
    private static double calculateShift(LocalDateTime[] data) {
        final int FRIDAY = 5;
        final int SATURDAY = 6;
        final double  SATURDAY_WAGE_MODIFIER = 1.5;
        
        long secondsInShift = 0;
        final int OVERTIME_THRESHOLD = 60 * 60 * 7; //7 hours in seconds
        final int TWO_HOURS_OVERTIME = 60 * 60 * 2;
        
        double totalPay = 0;
        double regPayPerHour = 29.12;
        double regPayPerSecond = regPayPerHour / 60 / 60;
        int regDelivery = 3;
        int deliveryInWeekend = 4;

        LocalDateTime shiftStart = data[0];
        LocalDateTime shiftEnd = data[1];
        int deliveriesNum = data[2].getMinute();

        //Sets up a 18:00 object to make calculations with
        int year = shiftStart.getYear();
        int month = shiftStart.getMonthValue();
        int day = shiftStart.getDayOfMonth();
        LocalDateTime six = LocalDateTime.of(year ,month ,day ,18 ,0 ,0);

        if (shiftStart.getDayOfWeek().getValue() == FRIDAY) {
            Duration fridayFirstDuration = Duration.between(shiftStart,six);
            long secondsUntilSix = fridayFirstDuration.toSeconds();
            secondsInShift += secondsUntilSix;
            totalPay += secondsUntilSix * regPayPerSecond;

            Duration fridaySecondDuration = Duration.between(six,shiftEnd);
            double secondsPastSix = (double)(fridaySecondDuration.toSeconds());
            secondsInShift += secondsPastSix;

            //totalPay += secondsPastSix * regPayPerMinute * SATURDAY_WAGE_MODIFIER;
            totalPay += deliveriesNum * deliveryInWeekend;
            if (secondsInShift > OVERTIME_THRESHOLD) { // overtime activates
                secondsInShift -= OVERTIME_THRESHOLD;
                if (secondsInShift > TWO_HOURS_OVERTIME) { //2+ hours of overtime
                    totalPay += TWO_HOURS_OVERTIME * regPayPerSecond * 1.75;
                    secondsInShift -= TWO_HOURS_OVERTIME;
                    totalPay += secondsInShift * regPayPerSecond * 2;            
                }
                else { //less than 2 hours of overtime
                    totalPay += secondsInShift * regPayPerSecond * 1.75;  
                }

            }
            else { //no overtime
                totalPay += secondsPastSix * regPayPerSecond * SATURDAY_WAGE_MODIFIER;    
            }
        }
        else if (shiftStart.getDayOfWeek().getValue() == SATURDAY) {
            Duration saturdayFirstDuration = Duration.between(shiftStart,six);
            double secondsUntilSix = (double)(saturdayFirstDuration.toSeconds());
            secondsInShift += secondsUntilSix;
            totalPay += secondsUntilSix * regPayPerSecond * SATURDAY_WAGE_MODIFIER;
            Duration saturdaySecondDuration = Duration.between(six,shiftEnd);
            double secondsPastSix = (double)(saturdaySecondDuration.toSeconds());
            secondsInShift += secondsPastSix;
            totalPay += deliveriesNum * deliveryInWeekend;
            if (secondsInShift > OVERTIME_THRESHOLD) { // overtime activates
                secondsInShift -= OVERTIME_THRESHOLD;
                if (secondsInShift > TWO_HOURS_OVERTIME) { //2+ hours of overtime
                    totalPay += TWO_HOURS_OVERTIME * regPayPerSecond * 1.75;
                    secondsInShift -= TWO_HOURS_OVERTIME;
                    totalPay += secondsInShift * regPayPerSecond * 2;            
                }
                else { //less than 2 hours of overtime
                    totalPay += secondsInShift * regPayPerSecond * 1.75;  
                }
            }
            else { //no overtime
                totalPay += secondsPastSix * regPayPerSecond * SATURDAY_WAGE_MODIFIER;    
            }
        }
        else { //non weekend day
            Duration shiftDuration = Duration.between(shiftStart,shiftEnd);
            double shiftSeconds = (double)(shiftDuration.toSeconds()); 
            if (shiftSeconds > OVERTIME_THRESHOLD) {
                totalPay += 7 * regPayPerHour;
                shiftSeconds -= OVERTIME_THRESHOLD;
                if (shiftSeconds > TWO_HOURS_OVERTIME) {
                    totalPay += TWO_HOURS_OVERTIME * regPayPerSecond * 1.25;
                    shiftSeconds -= TWO_HOURS_OVERTIME;
                    totalPay += shiftSeconds * regPayPerSecond * 1.50;
                }
            else {
                totalPay += shiftSeconds * regPayPerSecond * 1.25; 
            } 
            }
            totalPay += deliveriesNum * regDelivery;
        }
        System.out.println("For shift: "+shiftStart.toString()+" -> "+ shiftEnd.toString() + " +"+ data[2].getMinute() +" totalling - "+ totalPay + "NIS");
        return totalPay;
    }
    /*
     * Appends a shift to the end of the database in the logs folder
     */
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
            else //pads the deliveriesnum 7 to 07 when smaller than 10
                log.append(startingTime + "->" + endingTime + " +0" +  deliveriesNum + " Deliveries\n");
            log.close();
        }
        catch (IOException e) {
            System.out.println("EXECPTION DETECTED!");
            e.printStackTrace();
        }
        System.out.println("End of appending");
        scan.close();
    }
    /*
     * Takes a log line and parses it into usable data
     * @param log line to parse from
     * @return an LocalDateTime array with 3 elements-Shiftstart, Shiftend, Delivery num
     */
    private static LocalDateTime[] parseFromLine(String logLine) {
        LocalDateTime[] result = new LocalDateTime[3];
        LocalDateTime startingTime = LocalDateTime.parse(logLine.substring(0, 19));
        LocalDateTime endingTime = LocalDateTime.parse(logLine.substring(21, 40));
        LocalDateTime deliveryHelper = LocalDateTime.parse("2999-12-28T23:" + logLine.substring(42,44) + ":59"); //To pass the deliverynum out of the function
        result[0] = startingTime;
        result[1] = endingTime;
        result[2] = deliveryHelper;
        return result;
    }
    /*
     * Calculates a specific month's wage
     * @param the month to calculate
     * @return the amount of money made in that month
     */
    private static double calcMonthlyWage(String monthToCalc) {
        double monthlyPay = 0;
        try {
            Scanner scan = new Scanner(new File("./logs/log1.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.contains(monthToCalc)) {
                    LocalDateTime[] data = new LocalDateTime[2];
                    data = parseFromLine(line);
                    monthlyPay += calculateShift(data);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        return monthlyPay;
    }
}  
