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
                    registerShift();
                    break;
                case 2:
                    //calcPaycheck()  Calculate next paycheck
                    break;    


            }
            System.out.println("Enter your next decision");
            decision = scan.nextInt();
        }


        
        //int[] pay =singleShift(startingTime,endingTime,deliveriesNum);
        //System.out.println("The pay is: "+pay.toString());
        scan.close();           
    }
    //result is first part the hourly total pay and second part is delivery bonus sum
    public static int registerShift() {
        int pay = -1;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a shift starting point in the format of 17:00 2022-06-03");
        String startingTime = scan.nextLine();
        System.out.println("Please enter a shift ending point in the format of 17:00 2022-06-03");
        String endingTime = scan.nextLine();
        System.out.println("Please enter the num of deliveries done");
        int deliveriesNum = scan.nextInt();
        
        scan.close();
        return pay;
    }
 
}  
