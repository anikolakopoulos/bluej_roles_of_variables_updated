package java_program;

import java.io.*;
public class SmoothedAverage {
    // Largest average of three consecutive months
    public void calculateAverage() {
        int month; // Current month %%month%%stepper%%
        float current, previous, preceding, average, largest;
        //Data for three months
        //%%current%%fixed value%%MRH
        //%%previous%%temporary%%
        //%%preceding%%most recent holder%%
        //Current average%%average%%most recent holder%%
        //Largest one found so far%%largest%%most wanted holder%%
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter 1. value: ");
            preceding = new Float(in.readLine()).floatValue();
            System.out.print("Enter 2. value: ");
            previous = new Float(in.readLine()).floatValue();
            System.out.print("Enter 3. value: ");
            current = new Float(in.readLine()).floatValue();
            largest = (current + previous + preceding) / 3;
            for (month = 4; month <= 12; month++) {
                preceding = previous;
                previous = current;
                System.out.print("Enter " + month + ". value: ");
                current = new Float(in.readLine()).floatValue();
                average = (current + previous + preceding) / 3;
                if (average > largest) largest = average;
            }
            System.out.println("Largest three month average was " + largest);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String argv[]) {
        SmoothedAverage sa = new SmoothedAverage();
        sa.calculateAverage();
    }
}