package java_program;

import java.io.*;
public class Occur {
    //Count occurences of a value in an array
    private final int last = 7;
    private int[] value; //Array of values %%value%%walker%%
    private int i; //Array index %%i%%stepper%%
    private int key; //Value to search for %%key%%walker%%
    private int count; //Count of occurences %%count%%sr%%
    public Occur() {
        value = new int[last];
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter " + last + " values: ");
        for (i = 0; i < last; i++) {
            try {
                value[i] = new Integer(in.readLine()).intValue();
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        System.out.print("Enter value to search for: ");
        try { 
            key = new Integer(in.readLine()).intValue();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        count = 0;
        for (i = 0; i < last; i++)
            if (value[i] == key) 
                count ++;
        System.out.println("There are " + count + " occurences of " + key + " in the array");
    }
}