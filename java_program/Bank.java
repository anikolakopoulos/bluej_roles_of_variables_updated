package java_program;

public class Bank {
  public static void main(String[] args) {

    BankAccount account1 = new BankAccount("John");
    BankAccount account2 = new BankAccount("Sue");
    BankAccount account3 = new BankAccount("Dick");

    account1.deposit(Input.readInt());
    account2.deposit(Input.readInt());
    System.out.println(
      account2.transferTo(account1, Input.readInt()));
    account1.deposit(Input.readInt());
    System.out.println(account1.balance);
    System.out.println(account2.balance);
    System.out.println(account3.balance);
  }
}

class BankAccount {
  String owner; //%%owner%%fixed value%%
  int balance; //%%balance%%gr%%

  public BankAccount (String name) { //%%name%%fixed value%%
    owner = name; balance = 0;
  }

  public void deposit (int amount) { //%%amount%%fixed value%%
    balance += amount;
  }

  public String transferTo(BankAccount accountTo, int amount) { //%%amount%%fixed value%%
    if (balance < amount) return "Overdraft not allowed";
    balance -= amount;
    accountTo.deposit(amount);
    return "Transfer okay";
  }

}
