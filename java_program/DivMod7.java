package java_program;

import java.io.*;
public class DivMod7 {
    private final int divisor = 7; // Divisor to be used
    public void calculateDivMod7() {
        float value, // Input value read
        q, r, // Current quotient and remainder
        lq, lr; //Largest quotient and remainder
        //%%value%%most recent holder%%
        //%%q%%one way flag%%
        //%%r%%t%%
        //%%lq%%most wanted holder%%
        //%%lr%%most wanted holder%%
        lq = 0;
        lr = 0;
        System.out.print("Enter value (negative number to terminate): ");
        value = getInput();
        while (value >= 0) {
            q = value / divisor;
            System.out.print("Quotient = " + q);
            r = value % divisor;
            System.out.println(", Remainder = " + r);
            if (q > lq) lq = q;
            if (r > lr) lr = r;
            System.out.print("Enter value (negative number to terminate): ");
            value = getInput();
        }
        System.out.println("Largest quotient is " + lq + " and largest remainder is " + lr);
    }

    public static void main(String argv[]) {
        DivMod7 dv7 = new DivMod7();
        dv7.calculateDivMod7();
    }

    public float getInput(){
        float returnFloat = 0;
        BufferedReader in = new BufferedReader(new
                InputStreamReader(System.in));
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