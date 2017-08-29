package java_program;

import java.io.*;

public class Fibonacci {
    private int last_fib; //%%last_fib%%temporary%%
    private int fib; //%%fib%%gatherer%%
    private int temp; //%%temp%%most wanted holder%%
    private int number; //%%number%%fixed value%%
    private int i; //%%i%%stepper%%
    /**
     * Constructor for objects of class Fibonachi
     */
    public Fibonacci(){
        last_fib = 1;
        fib = 1;
        number = getNumber();
        if (number <= 2) {
            System.out.println("The first and second numbers are 1.");
        } else {
            System.out.println("Value 1 is: 1");
            System.out.println("Value 2 is: 1");
            for (i = 3; i <= number; i++) {
                temp = last_fib;
                last_fib = fib;
                fib = fib + temp;
                System.out.println("Value " + i + " is: " + fib);
            }
        }
    }

    /**
     * Read number of integers in sequence from terminal
     * @return The number of values to be in the sequence
     */
    private int getNumber() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = 0;
        while (n < 1) {
            String tempN = "";
            System.out.print("Enter number in sequence: ");
            try {
                tempN = in.readLine(); /* this is a comment,
                so is this,
                this too*/} catch (IOException e) {
                System.out.println(e.getMessage());
            }
            Integer tempInt = new Integer(tempN);
            n = tempInt.intValue();
        }
        return n;
    }
}