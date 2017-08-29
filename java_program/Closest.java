package java_program;

import java.io.*;
public class Closest {
    int original, // %%original%%fixed value%%
    closest; // %%closest%%most wanted holder%%
    public void findClosest() {
        int data; // %%data%%most recent holder%%
        System.out.print("Enter any number: ");
        original = getInput();
        System.out.print("Enter a positive number (negative to end): ");
        data = getInput();
        closest = data;
        while (data >= 0) {
            if (Math.abs(data-original) < Math.abs(closest-original))
            closest = data;
            System.out.print("Enter a positive number (negative to end): + ");
            data = getInput();
        }
        if (closest < 0) System.out.println("No positive value entered.");
        else System.out.println("The closest to " + original + " was " + closest);
    }

    public static void main(String argv[]) {
        Closest c = new Closest();
        c.findClosest();
    }

    public int getInput() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int returnInt = -1;
        try {
            returnInt = in.read() - 48;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return returnInt;
    }
}