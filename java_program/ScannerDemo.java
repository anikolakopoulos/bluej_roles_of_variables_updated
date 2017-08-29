package java_program;

import java.util.*;

public class ScannerDemo {

    public static void main(String[] args) {

        String s = "Hello true World! 3 + 3.0 = 6 ";

        // create a new scanner with the specified String Object
        Scanner scanner = new Scanner(s);

        // find the next boolean token and print it
        // loop for the whole scanner
        while (scanner.hasNext()) {

            // if the next is boolean, print found and the boolean
            if (scanner.hasNextBoolean()) {
                System.out.println("Found :" + scanner.nextBoolean());
            }

            // if a boolean is not found, print "Not Found" and the token
            System.out.println("Not Found :" + scanner.next()); 
        }

        // close the scanner
        scanner.close();
    }
}