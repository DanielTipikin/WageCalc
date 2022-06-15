import java.util.Scanner;

/*
 * Assumptions:
 * 1. I work only on the weekends (Delivery bonus is 4 instead of 3)
 */
public class WageCalc {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a shift starting point in the format of 17:00:00 2022-06-03");
        String startingTime = scan.nextLine();
        System.out.println(startingTime);
        System.out.println("Please enter a shift ending point in the format of 17:00:00 2022-06-03");
        String endingTime = scan.nextLine();
        System.out.println(endingTime);
        System.out.println("Please enter the num of deliveries done");
        int deliveriesNum = scan.nextInt();


        System.out.println("singleShift starting: ");
        int[] pay =singleShift(startingTime,endingTime,deliveriesNum);
        System.out.println("The pay is: "+pay.toString());
        scan.close();           
    }
    //result is first part the hourly total pay and second part is delivery bonus sum
    public static int[] singleShift(String startingTime, String endingTime, int numOfDeliveries) {
        int[] result = {-1,-1};
        int minutes; 
        System.out.println("TEST in singleShift");
        return result;
    }
    
}  
