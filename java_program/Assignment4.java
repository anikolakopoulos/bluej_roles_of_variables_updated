package java_program;
import java.util.Scanner;

public class Assignment4 
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the number of items:"); // PROMPTS USER FOR # OF ITEMS
        double size = scan.nextDouble(); //%%size%%fixed value%%

        double meanSum = 0;
        double deviationSum = 0;
        double array[] = new double [(int) size];
        

        System.out.println("Please enter the items");
        for (int i = 0; i < size; i++)
        {
            array[i] = scan.nextDouble();
        }

        for (int i = 0; i < size; i++)
        {
            meanSum += array[i];
        }

        double mean = (meanSum / size); //%%mean%%temporary%%
        System.out.println("The mean is: " + mean);

        //STANDARD DEVIATION CALCULATION
        for (int i = 0; i < size; i++)
        {
            array[i] = (Math.pow((array[i] - mean), 2));
        }
        for (int i = 0; i < size; i++)
        {
            deviationSum += array[i];
        }

        double variance = (deviationSum / size);

        double standardDeviation = Math.sqrt(variance);
        System.out.println("The standard deviation is: " + standardDeviation);

    }

}