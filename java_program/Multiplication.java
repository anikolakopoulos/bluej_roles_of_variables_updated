package java_program;

import java.io.*;
public class Multiplication {
    //Print a multiplication table
    private int size;// Size of table %%size%%walker%%
    private int row; // Row index %%row%%stepper%%
    private int col; // Column index %%col%%stepper%%
    public Multiplication() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter size: ");
        try {
            size = new Integer(in.readLine()).intValue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(" Multiplication table ");
        System.out.print("\t");
        for (col = 1; col <= 10; col++)
            System.out.print(col + "\t");
        System.out.println();
        for (row = 1; row <= size; row++) {
            System.out.print(row + "\t");
            for (col = 1; col <= 10; col++)
                System.out.print(row * col + "\t");
            System.out.println();
        }
    }
}