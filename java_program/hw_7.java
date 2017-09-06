package java_program;
import java.util.Random;

public class hw_7{

    public static void main(String[] args)
    {
        int[] anArray = new int[100]; //%%anArray%%moasvasvr%%
        Random r = new Random(); //%%r%%most recent holder%%
        int ind = 0; //%%ind%%walker%%
        int total1= 0;
        int total2= 0; 
    
        for(int index = 0; index < anArray.length; index++) 
        {
            anArray[index] = (int)(Math.random() * 10);
            ind = r.nextInt() * 10; 
            //total2 = total2 + ind;
        }
        
        for(int element = 0; element < anArray.length; element++)
        {
            System.out.println("anArray[" + element + "]\t" + anArray[element]);
        }
    }
    
}