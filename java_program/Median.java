package java_program;

public class Median {
    
    static final int sentinel = -999; // %%sentinel%%fixed value%%
    static final int maxCount = 10;   // %%maxCount%%fixed value%%

    public static void main(String[] args) {

        float[] a = new float[maxCount]; // %%a%%organizer%%
        float closest; // %%closest%%most wanted holder%%
        float temp; // %%temp%%most recent holder%%
        int n; // %%n%%stepper%%
        int i; // %%i%%stepper%%
        boolean inOrder; // %%inOrder%%stepper%%

        n = 0; closest = sentinel;
        System.out.print("Enter a number: ");
        temp = UserInputReader.readFloat();
        while((temp != sentinel) && (n < maxCount)) {
            n = n + 1;
            a[n-1] = temp;
            if(Math.abs(temp) < Math.abs(closest)) {
                closest=temp;
            }
            System.out.print("Enter a number: ");
            temp = UserInputReader.readFloat();
        }
        System.out.println("Closest to zero is " + closest);
        inOrder = false;
        while(!inOrder) {
            inOrder = true;
            for(i=1; i<n; i++) {
                if(a[i-1] > a[i]){
                    temp = a[i-1];
                    a[i-1] = a[i];
                    a[i] = temp;
                    inOrder = false;
                }
            }
        }
        System.out.print("Median is ");
        i=(n+1)/2; 
        if(((n+1)&1)==0){
            System.out.println(a[i-1]);
        } else {
            System.out.println(0.5 * (a[i-1]+ a[i+1]));
        }
    }
    
}