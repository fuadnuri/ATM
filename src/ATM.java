import java.awt.image.PackedColorModel;
import java.util.Scanner;
import java.util.Vector;

public class ATM{
    private Bank bank=new Bank();
    private Scanner sc =new Scanner(System.in);
    public ATM(){
        bank.makeMrich();
        this.run();
    }
    private void run(){
        System.out.println("Well come to the atm !\n\n\n");
        System.out.println("Enter your credentials below ");
        System.out.print("Enter your account Number");
        int account_number = this.sc.nextInt();
        System.out.print("Enter your login passcode");
        String auth = this.sc.next();
        if (this.authenticate(account_number,auth)){
            System.out.flush();
            atmMenu(account_number);

        }

    }
    private boolean authenticate(int account_number, String auth){
        if (account_number<bank.userCount()){
            return true;
        }
        return false;
    }
    private void atmMenu(int account_number){
        System.out.println("ATM Menu:");
        System.out.println("1)balance Enquiry");
        System.out.println("2)Withdrawal");
        System.out.println("3)Transfer");
        System.out.print("Choice: ");
        int choice =sc.nextInt();
        if(choice==1){
            System.out.print("your account balance is: ");
            System.out.print(balanceEnquiry(account_number));

        }
        else if(choice ==2){
            System.out.print("Enter the amount to withdraw: ");
            double amount = sc.nextDouble();

            try{
               this.widrawal(account_number,amount);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        else if(choice ==3){
            System.out.flush();
            System.out.print("Enter The amount to be transfered");
            double amount = sc.nextDouble();
            System.out.print("Enter the beneficiary account: ");
            int acc_num = sc.nextInt();
            try{
                System.out.print("your account balance after transfer is: ");
                System.out.println(this.transfer(account_number,amount,acc_num));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        else{
            System.out.flush();
            System.out.println("wrong input try again");
        }



    }
    private double balanceEnquiry(int acount_number){
        return bank.getBalance(acount_number);
    }
    private double widrawal(int account_number,double amount){
        double out = bank.credit(account_number,amount);
        System.out.println(out);
        return out;
    }
    private double transfer(int account_number, double amount, int beneficiary){
        try{
            return this.bank.transfer(account_number,amount, beneficiary);
        }catch(Exception e){
            return 0;
        }
    }

}
class User{
    private int account_number;
    private String userAuth;
    private double balance = 0;
    public User(int account_number, String userAuth){
        this.account_number = account_number;
        this.userAuth = userAuth;
    }
    public boolean credit(double amount){
        if (amount > this.balance){
            return false;
        }
        else{
            this.balance = this.balance-amount;
            return true;
        }

    }
    public boolean debit(double amount){
        this.balance = this.balance + amount;
        return true;
    }
    public void setBalance(double amount){
        this.balance = amount;
    }
    public double getBalance(){
        return this.balance;
    }
}


class Bank{
    private Vector<User> users = new Vector<User>(0);
    public Bank(){
        for(int i = 0; i<6;i++){
            User user = new User(i,String.valueOf(i));
            users.add(user);
        }
    }
    public boolean makeMrich(){
        for(int i=0; i < 6; i++){
            users.elementAt(i).setBalance(10000);
        }
        return true;
    }
    public double credit(int user_account,double amount){
        if(user_account<users.capacity()){
            if (users.elementAt(user_account).getBalance()>amount){
                users.elementAt(user_account).credit(amount);
                return users.elementAt(user_account).getBalance();
            }
            else{
                return 0;
            }
        }
        return 0;
    }
    public double debit(int user_account, double amount){
        if(user_account<users.capacity()){
            users.elementAt(user_account).debit(amount);
            return users.elementAt(user_account).getBalance();
        }
        return 0;
    }
    public double transfer(int sender_account, double amount, int recepant){
        if (sender_account<users.capacity() && recepant<users.capacity()){
            if (users.elementAt(sender_account).getBalance()>amount){
                users.elementAt(recepant).debit(amount);
                return users.elementAt(sender_account).getBalance();
            }
        }
        return 0;
    }
    public int userCount(){
        return users.capacity();
    }
    public double getBalance(int user_account){
        return users.elementAt(user_account).getBalance();
    }

}