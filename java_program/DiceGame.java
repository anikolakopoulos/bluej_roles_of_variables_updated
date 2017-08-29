package java_program;

import java.util.Random;
public class DiceGame {
    int die1;// Values of dice thrown %%die1%%most recent holder%%
    int die2; //%%die2%%most recent holder%%
    int total1;// Sums of values of each player %%total1%%gatherer%%
    int total2; //%%total2%%gatherer%%
    boolean firstPlayer; // First player's turn ? %%firstPlayer%%stepper%%
    Random r = null; //%%total2%%gatherer%%
    
    public static void main(String argv[]) {
        DiceGame dc = new DiceGame();
        dc.play();
    }
    
    private int randomize(int i) {
        if (r == null)
            r = new Random();
        return r.nextInt(i);
    }

    public void play() {
        total1 = 0;
        total2 = 0;
        firstPlayer = true;
        while(total1 < 100 && total2 < 100) {
            die1 = randomize(6) + 1;
            die2 = randomize(6) + 1;
            if (firstPlayer)
                System.out.print("First");
            else
                System.out.print("Second");
            System.out.println(" player throws: " + die1 + ", " + die2);
            if (firstPlayer) total1 = total1 + die1 + die2;
            else total2 = total2 + die1 + die2;
            firstPlayer = !firstPlayer;
        }
        System.out.println("First player: " + total1);
        System.out.println("Second player: " + total2);
        if (total1 > total2) System.out.print("First");
        else System.out.print("Second");
        System.out.println(" player won the game!");
    }

}