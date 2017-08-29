package java_program;

import java.io.*;

/**
 * The <code>UserInputReader</code> is a class for reading user input
 * from the command line. It includes methods for reading a single <code>char</code>, 
 * <code>int</code>, and <code>float</code>. In addition to reading primitive data
 * types, the class can read a line of input as a <code>String</code>.
 *
 * @author Petri Mikael Gerdt
 * @version 1.0 (May 12, 2003)
 */
public class UserInputReader {

    public static String readInputLine() {
        BufferedReader input;
        String buffer;
        input = new BufferedReader(new InputStreamReader(System.in));
        try {
            buffer = input.readLine();
        } catch (IOException ioe) {
            System.out.println("Error: can't read input");
            buffer = null;
        }
        return buffer;
    }

    public static double readDouble() {
        String s = readInputLine();
        Double retval = null;
        if (s != null) {
            while (retval == null) {
                try {
                    retval = Double.valueOf(s);
                } catch (NumberFormatException nfe) {
                    System.out.println("Error: can't parse a double from string " + s);
                    System.out.print("Enter a new value (example: 3.14): ");
                    s = readInputLine();
                }
            }
        }
        return retval.doubleValue();
    }

    public static float readFloat() {
        String s = readInputLine();
        Float retval = null;
        if (s != null) {
            while (retval == null) {
                try {
                    retval = Float.valueOf(s);
                } catch (NumberFormatException nfe) {
                    System.out.println("Error: can't parse a float from string " + s);
                    System.out.print("Enter a new value (example: 3.14): ");
                    s = readInputLine();
                }
            }
        }
        return retval.floatValue();
    }

    public static int readInt() {
        String s = readInputLine();
        Integer retval = null;
        if (s != null) {
            while (retval == null) {
                try {
                    retval = Integer.valueOf(s);
                } catch (NumberFormatException nfe) {
                    System.out.println("Error: can't parse a int from string " + s);
                    System.out.print("Enter a new value (example: 3): ");
                    s = readInputLine();
                }
            }
        }
        return retval.intValue();
    }

    public static char readChar() {
        String s = readInputLine();
        Character retval = null;
        if (s != null) {
            while (retval == null) {
                if (s.length() == 1) retval = new Character(s.charAt(0)); 
                else {
                    System.out.println("Error: input is a string, not a character: " + s);
                    System.out.print("Enter a new value (example: a): ");
                    s = readInputLine();
                }
            }
        }
        return retval.charValue();
    }

}