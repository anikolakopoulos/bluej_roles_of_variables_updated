package java_program;

import java.io.*;
public class Saw {
    // Read a sequence of values and check if they form a 'saw'
    // i.e. adjacent values go up and then down.
    private final int last = 7;
    private int[] value = new int[last]; //Values to be checked
    //%%value%%fixed value%%
    private int i; //Index of array %%i%%stepper%%
    private boolean up; //Current direction is up ? %%up%%stepper%%
    private boolean ok; //Does saw property still hold ?
    //%%ok%%oDHDEg%%
    public Saw() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter " + last + " values:");
        for (i=0; i < last; i++) {
            try {
                value[i] = new Integer(in.readLine()).intValue();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (value[0] < value[1]) up = true;
        if (value[0] != value[1]) ok = true;
        i = 1;
        while (ok && i < last - 1) {
            ok = (up && (value[i] > value[i+1])) || (!up && (value[i] < value[i+1]));
            up = !up;
            i ++;
        }
        System.out.print("Values ");
        if (!ok) System.out.print("do not ");
        System.out.print("form a saw.");
    }
}