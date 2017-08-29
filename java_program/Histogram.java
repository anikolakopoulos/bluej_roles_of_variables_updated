package java_program;

import java.io.*;
public class Histogram {
    //Draw a histogram
    private final int longest = 40; //Longest bar
    private float[] amount = new float[12]; //Data for drawing
    //%%amount%%fixed value%%
    private float max; //Maximum data element %%max%%most wanted holder%%
    private int month; //Current month %%month%%stepper%%
    private int i; //%%i%%stepper%%
    public Histogram() {
        max = 0;
        for (month = 1; month <= 12; month ++) {
            System.out.println("Enter amount for month " + month + ": ");
            amount[month - 1] = getInput();
            if (max < amount[month - 1]) {
                max = amount[month - 1];
            }
        }
        System.out.println();
        for (month = 1; month <= 12; month++) {
            System.out.print(month + ": ");
            if (month < 10) System.out.print(" ");
            for (i = 0; i <= amount[month-1] / max * longest; i++)
                System.out.print('*');
            System.out.println();
        }
    }

    private float getInput(){
        float returnFloat = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String inputString = in.readLine();
            Float tempFloat = new Float(inputString);
            returnFloat = tempFloat.floatValue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return returnFloat;
    }
}