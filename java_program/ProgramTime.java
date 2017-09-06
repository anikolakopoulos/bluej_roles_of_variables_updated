package java_program;

import java.io.*;

public class ProgramTime{
    //Transform time to seconds
    private int hours; //%%hours%%fixed value%%
    private int mins;  //%%mins%%fixed value%%
    private int secs;  //%%secs%%fixed value%%
    public ProgramTime() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter time separated by ':'(hour min sec , " + "e.g., 13:30:5): ");
        String time = null;
          try {
            time = in.readLine();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        boolean isHour = true;
        String tempString = "";
        for (int i = 0; i < time.length(); i++) {
            tempString += time.substring(i, i + 1);
            if (tempString.endsWith(":")) {
                tempString = tempString.substring(0, tempString.length()-1);
                if (isHour) {
                    hours = new Integer(tempString).intValue();
                    isHour = false;
                    tempString = "";
                }
                else if (!isHour) {
                    mins = new Integer(tempString).intValue();
                    tempString = "";
                }
            } else if(i == time.length()-1) {
                secs = new Integer(tempString).intValue();
            }
        }
        int seconds = secs + 60 * (mins + 60 * hours);
        if (hours < 10) {
              if (mins < 10) {
                System.out.println("At 0" + hours + ":0" + mins + ":" + secs + "\t" + seconds + "seconds has elapsed.");
            } else {
                System.out.println("At 0" + hours + ":" + mins + ":" + secs + "\t" + seconds + "seconds has elapsed.");
            }
        } else {
              if (mins < 10) {
                System.out.println("At " + hours + ":0" + mins + ":" + secs + "\t" + seconds + "seconds has elapsed.");
            } else {
                System.out.println("At " + hours + ":" + mins + ":" + secs + "\t " + seconds + " seconds has elapsed.");
            }
        }
    }
}