package java_program;

import java.io.*;
public class Doubles {
    private int counter; //%%counter%%stepper%%
    private int number; //%%number%%most recent holder%%
    public Doubles() {
        do {
            System.out.print("Give amount of loops: ");
            counter = getInput();
        }
        while (counter < 0);
        while (counter > 0) {
            System.out.print("Give some number: ");
            number = getInput();
            System.out.println("Two times " + number + " is " + 2*number);
            counter = counter - 1;
        }
    }

    public int getInput() {
        int returnInt = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            returnInt = new Integer(in.readLine()).intValue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return returnInt;
    }
}