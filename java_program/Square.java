package java_program;

import java.io.*;

public class Square {
    public static void main(String argv[]) {
        final int maxSide = 78; // Max length for sides
        char c; //%%c%%fixed value%%
        int side; // Length of sides %%side%%fixed value%%
        int i, j; // Counters for side lengths %%i%%stepper%%//%%j%%stepper
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter character for drawing: ");
        try {
            c = in.readLine().charAt(0);
            System.out.print("Enter side length: ");
            side = new Integer(in.readLine()).intValue();
            while (side < 0 || side > maxSide) {
                System.out.print("Length incorrect. Re-enter: ");
                side = new Integer(in.readLine()).intValue();
            }
            System.out.println();
            for (i = 1; i <= side; i++) {
                for (j = 1; j <= side; j++)
                    System.out.print(c + " ");
                    System.out.println();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}