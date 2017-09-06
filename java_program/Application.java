package java_program;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        // Create scanner object
        Scanner input = new Scanner(System.in); //%%input%%most recent holder%%

        // Output the prompt
        System.out.println("Enter a floating point value: ");

        // Wait for the user to enter something.
        double value = input.nextDouble(); //%%value%%walker%%

        // Tell them what they entered.
        System.out.println("You entered: " + value);

        System.out.println("Enter your rollno");  
        int rollno = input.nextInt();  //%%rollno%%w%%
        System.out.println("Enter your name");  
        String name = input.next();  //%%name%%most recent%%
        System.out.println("Enter your fee");  
        double fee = input.nextDouble(); //%%fee%%most recent holder%%
        System.out.println("Rollno:" + rollno + " name:" + name + " fee:" + fee);  
        input.close();

    }
}