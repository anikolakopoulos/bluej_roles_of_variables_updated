package java_program;

import java.io.*;

public class SalesApplication {
    public static void main(String[] args) {
        int x = 0; 
        float sum; 
        Sales domestic = new Sales(3);
        Sales international = new Sales(2);
        while (x >= 0) {
            System.out.print("Enter product (-1 to quit): ");
            x = Input.readInt();
            if (x >= 0) {
                System.out.print("Enter price: ");
                sum = Input.readFloat();
                international.Sale(x, sum);
            }
        }
        System.out.println("\nInternational sales");
        international.printSales();
    }
}

class Sales {
    float[] total; //%%total%%gatherer%%

    public Sales (int count) { //%%count%%fixed value%%
        total = new float[count];
    }

    public void Sale (int product, float price) {
        //%%product%%fixed value%%
        //%%price%%fixed value%%
        if (product >= total.length) 
        increaseSize(product); 
        total[product] += price; 
    }

    public void increaseSize(int n) { //%%n%%fixed value%%
        float [] tmp = new float[n + 1]; //%%tmp%%fixed value%%
        for (int i = 0; i < total.length; i++) //%%i%%stepper%%
            tmp[i] = total[i];
        total = tmp;
    }

    public void printSales() {
        for (int p = 0; p < total.length; p++) { //%%p%%stepper%%
            System.out.println("Product "+ p + ": " + total[p]);
        }
    }
}
