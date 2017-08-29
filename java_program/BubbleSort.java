package java_program;

public class BubbleSort
{
    public int[] sort(int[] source) {
        /* variables */
        int i, j, temp; //%%temp%%temporary
        boolean swapped; //%%swapped%%stepper%%
        int len = source.length; //%%len%%fixed value%%
        int[] target = new int[len]; //%%target%%Organizer%%
        for (i = 0; i < len; i++) {
            target[i] = source[i];
        }
        for (i = len-1; i > 0; i--) {
            swapped = false;
            for (j = 0; j < i; j++) {
                if (target[j] > target[j+1]) {
                    temp = target[j];
                    target[j] = target[j+1];
                    target[j+1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return target;
    }
    
}