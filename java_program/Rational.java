package java_program;

public class Rational { 

    private int num; //%%num%%fixed value%%
    private int dem; //%%dem%%fixed value%%

    public Rational (int x, int y) {
        int g = gcd(Math.abs(x), Math.abs(y)); 
        num = x / g;
        dem = Math.abs(y) / g;
        if (y < 0) {
            num = -num;   
        }
    }

    private int gcd (int x, int y) {
        int r = x % y;
        while (r != 0){
            x = y;
            y = r; 
            r = x % y;
        }
        return y;
    }

}