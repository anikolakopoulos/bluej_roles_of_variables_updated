package java_program;

// Subclasses and inheritance: Simple subclasses, inheritance, overriding,
// super, polymorphism. Uses the Java ArrayList class. 
// http://www.cs.uef.fi/~saja/oo_metaphors/animations/index.html

// The idea of the program: A user can create cats (using the command c)
// and myriapods (m), advence to the next day (d), print info about
// existing animals (p), and make cats mew (m).
// Myriapods grow daily a new pair of legs.

// ---------- Page 1 of program listing -------------------- 

import java.util.ArrayList;

public class Animals
{

    static UserInputReader Input = new UserInputReader(); 

    public static void main(String[] args) {

        // Java 1.4: ArrayList all = new ArrayList(5);
        // Java 1.5: ArrayList <Animal>all = new ArrayList<Animal>(5);
        ArrayList <Animal> all = new ArrayList <Animal>(5); //%%all%%cr%%
        int i; Animal a;
        //%%i%%stepper%%
        //%%a%%container%%
        char x = 'a'; //%%x%%mrh%%

        while (x != 'q') {
            x = Input.readChar();
            switch(x) {
                case 'c': all.add(new Cat(Input.readInputLine(),
                        Input.readInputLine()));
                break;
                case 'm': all.add(new Myriapod(Input.readInt()));
                break;
                case 'd': for (i = 0; i < all.size(); i++) { 
                    a = (Animal) all.get(i);
                    a.getOlder();
                }
                break;
                case 'p': for (i = 0; i < all.size(); i++) { 
                    a = (Animal) all.get(i); 
                    a.print();
                }
                break;
                case 'w': for (i = 0; i < all.size(); i++) {
                    a = (Animal) all.get(i);
                    if (a instanceof Cat) {
                        Cat cat = (Cat) a;
                        cat.mew();
                    }
                }
                break;
            }
        }
    }

}

// ---------- Page 2 of program listing -------------------- 

class Animal {
    int age; //%%age%%stepper%%

    public Animal () { age = 0; }

    public void getOlder () { age++; }

    public void print () {
        System.out.println(age + " days old");
    }
}

class Cat extends Animal {
    String name, color;
    //%%name%%fixed value%%
    //%%color%%fixed value%%

    public Cat (String n, String c) {
        //%%n%%fixed value%%
        //%%c%%fixed value%%
        super(); name = n; color = c;
    }

    public void print () {
        System.out.println(name + " is a " + color);
        super.print();
        System.out.println("cat.");
    }

    public void mew () {
        System.out.println(name + " says: Miaouw.");
    }
}

class Myriapod extends Animal {
    int legs; //%%legs%%fixed value%%

    public Myriapod (int n) { //%%n%%fixed value%%
        super();
        legs = n;
    }

    public void print () {
        super.print();
        System.out.println (legs + 2*age + " legged myriapod.");
    }

}
