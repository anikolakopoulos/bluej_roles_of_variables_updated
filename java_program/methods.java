package java_program;

import java.util.*;
class methods {

    int middle; //%%middle%%fixed value%% 

    public int sum (List<Integer> aX){
        if (aX.size() > 0) {
            int sum = 0;

            for (Integer i : aX) {
                sum += i;
            }
            System.out.println(sum + middle/2);
            return sum;
        }
        return 0;
    }

    public double mean (List<Integer> aY){
        int sum = sum(aY);
        double meanX = 0;
        meanX = sum / (aY.size() * 1.0);
        return meanX;
    }

    public double median (List<Integer> aZ){
        middle = aZ.size()/2;

        if (aZ.size() % 2 == 1) {
            return aZ.get(middle);
        } else {
            return (aZ.get(middle-1) + aZ.get(middle)) / 2.0;
        }
    }

    public double sd (List<Integer> a){
        int sum = 0;
        double mean = mean(a);

        for (Integer i : a)
            sum += Math.pow((i - mean), 2);
        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
    }
}
class t {
    public static void main (String[]args) {

        methods m = new methods();

        List<Integer> c = Arrays.asList(2,49,11,44,88,1,1,5,33,88,5,44,2,44,44,132,6,2,22,22,5,1,22,22);
        Collections.sort(c);

        System.out.println(m.median(c));
        System.out.println(m.mean(c));
        System.out.println(m.sd(c));
    }
}

