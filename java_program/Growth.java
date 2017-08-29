package java_program;

import java.io.*;
public class Growth {
    //Growth of capital on a bank account
    private float capital; //Capital on the bank account %%capital%%gatherer%%
    private float percent; //Interest rate %%percent%%fixed value%%
    private float factor; //Factor for yearly growth
    //%%factor%%fixed value%%
    private float interest; //Interest in current year
    //%%interest%%temporary%%
    private int years; //Time to consider %%years%%fixed value%%
    private int i; //Year counter %%i%%stepper%%
    private boolean inputOk; //Is input valid ? %%inputOk%%owf%%
    public Growth() {
        System.out.println("Enter capital (positive or negative): ");
        capital = getFloat();
        inputOk = false;
        while (!inputOk) {
            System.out.print("Enter interest rate (%): ");
            percent = getFloat();
            System.out.print("Enter time (years) : ");
            years = getInt();
            if (percent > 0 && years > 0) inputOk = true;
            if (!inputOk) {
                System.out.println();
                System.out.println("Invalid data. Re-enter.");
            }
        }
        System.out.println();
        factor = percent / 100;
        for (i = 1; i <= years; i++) {
            interest = capital * factor;
            capital = capital + interest;
            System.out.println("After " + i + " years: interest is " +
                interest + " and total capital is " + capital);
        }
    }

    private float getFloat() {
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

    private int getInt() {
        int returnInt = 0;
        BufferedReader in = new BufferedReader(new
                InputStreamReader(System.in));
        try {
            returnInt = new Integer(in.readLine()).intValue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return returnInt;
    }
}