package java_program;

import java.util.Random;

public class Sum 
{
    public static void main(String[] args) {
        Random r = new Random(); //%%r%%most recent holder%%
        int number; // %%number%%wkubku%%
        int sum; // %%sum%%gatherer%%
        System.out.print("Enter the first number: ");
        number = r.nextInt();
        sum = number;
        System.out.print("Enter the second number: ");
        number = r.nextInt();
        sum = sum + number;
        System.out.print("Enter the third number: ");
        number = r.nextInt();
        sum = sum + number;
        System.out.println("Sum of the numbers is " + sum);
    }
    
}