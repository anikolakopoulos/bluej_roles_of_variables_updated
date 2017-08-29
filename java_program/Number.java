package java_program;

import java.io.*;
public class Number {
    //Read a number with embedded commas, e.g., 123,456
    private final char sep = 44;
    private char c; //The character read %%c%%most recent holder%%
    private int val; //Accumulated value of number %%val%%gatherer%%
    public Number() {
        val = 0;
        System.out.print("Enter number: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = "";
        try {
            inputLine = in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        int i = 0;
        do {
            c = inputLine.charAt(i);
            if (c != sep) val = val * 10 + c - 48;
            i++;
        } while ((c >= 48 && c <= 57 || c == sep) && i < inputLine.length());
        System.out.println("Value is " + val);
    }
}