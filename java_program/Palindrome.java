package java_program;

public class Palindrome {

    static final int MaxLen = 8; // %%MaxLen%%fixed value%%

    public static void main(String[] args) {

        int len; // %%len%%most recent holder%%
        int i; // %%i%%stepper%%
        char[] candidate = new char[MaxLen]; // %%candidate%%fixed value%%
        boolean pali; // %%pali%%owf%%
        do {
            System.out.print("Enter the length: ");
            len = UserInputReader.readInt();
            if((len < 1) || (len > MaxLen)) {
                System.out.println("Must be between 1..." + MaxLen);
            }
        } while ((len < 1) || (len > MaxLen));

        for (i = 1; i <= len; i++) {
            System.out.print("Enter the " + i + ".letter: ");
            candidate[i-1] = UserInputReader.readChar();
        }
        pali = true;
        for (i = 1; i <= len; i++) {
            pali = (pali && (candidate[i-1] != candidate[len-i]));
        }
        if (pali) {
            System.out.print("String is");
        } else {
            System.out.print("String is not");
        }
        System.out.print("a palindrome.");
    }
    
}