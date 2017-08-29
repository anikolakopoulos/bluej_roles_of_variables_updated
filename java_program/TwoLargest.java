package java_program;

import java.io.*;
public class TwoLargest {
    //Largest and second largest input value
    private int value; //Input value %%value%%most recent holder%%
    private int largest; //Largest input value so far
    //%%largest%%most wanted holder%%
    private int second; //Second largest so far
    //%%second%%most wanted holder%%
    public TwoLargest() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        largest = -1;
        second = -1;
        try {
            System.out.print("Enter value to begin: ");
            value = new Integer(in.readLine()).intValue();
            while (value >= 0) {
                if (value > largest) {
                    second = largest;
                    largest = value;
                }
                else if (value > second) second = value;
                System.out.print("Enter value (negative to end): ");
                value = new Integer(in.readLine()).intValue();
            }
            if (largest > 0) System.out.println("Largest was " + largest);
            if (second > 0) System.out.println("Second largest was " +
                    second);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}