
package ua.yandex.atmtest;

public class MyATM implements ATM{

    private Account account;
    
    public MyATM(Account account){
        this.account = account;
    }
    
    @Override
    public double checkBalance() {
        return account.getBalance();
    }

    @Override
    public boolean getCash(double ammount) { 
        if( ammount <= 0 ) throw new IllegalArgumentException();
        if( ammount > account.getBalance()) return false;
        account.withdraw(ammount);      
        return true;
    }
    
}


