package java_program;

import java.util.Set;
import java.io.*;
public class Lexical {
    //Lexical type recognition; spaces ignored
    private String lt; //Lexical type of current character
    //%%lt%%mrh%%
    private char c; //Current character from input
    //%%c%%most recent holder%%
    //%%op%%fixed value%%
    //%%digit%%fixed value%%
    //%%letter%%fixed value%%
    public Lexical() {
        char[] op = {43,45,42,47};
        char[] digit = {48,49,50,51,52,53,54,55,56,57};
        char[] letter = getLetters();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter expression, terminate with '.' : ");
        String stringChar = "";
        try {
            stringChar = in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        int i = 0;
        do {
            c = stringChar.charAt(i);
            if (c == ' ') lt = "Space";
            else if (c == 46) lt = "Period";
            else if (is(c, op)) lt = "Op";
            else if (is(c, digit)) lt = "Digit";
            else if (is(c, letter)) lt = "Letter";
            else lt = "Error";
            if (!lt.equals("Space")) System.out.println(lt);
            i ++;
        } while (!lt.equals("Error") && !lt.equals("Period"));
    }

    private char[] getLetters() {
        char[] returnArray = new char[52];
        int i = 0;
        for (char ch = 65; ch <= 122; ch++) {
            returnArray[i] = ch;
            i ++;
            if (i == 26) ch = 96;
        }
        return returnArray;
    }

    private boolean is(char inputChar, char[] inputArray) {
        boolean is = false;
        for (int i = 0; i < inputArray.length; i++) {
            char tempChar = inputArray[i];
            if (inputChar == tempChar) {
                is = true;
                break;
            }
        }
        return is;
    }
}