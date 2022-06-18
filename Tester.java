import java.time.LocalDateTime;
import java.util.Scanner;
import java.io.File;
import java.time.Duration;

public class Tester {
    public static void main(String[] args) {
        File file = new File("./logs/log1.txt");
        if (file.exists())
            System.out.println("This file exists");

        
        
    }    
}
//2022-06-03T17:35:01 -> 2022-06-03T22:51:11 14

/*
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