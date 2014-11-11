package ua.yandex.atmtest;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MyATM implements ATM{

    private final Account account;
    
    public MyATM(Account account){
        this.account = account;
    }
    
    @Override
    public double checkBalance() {
        return account.getBalance();
    }
    
    boolean isWorkingDay() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek != DayOfWeek.SUNDAY;        
    }       

    @Override
    public boolean getCash(double ammount) {
        if (!isWorkingDay()) return false;
        if (ammount <= 0) throw new IllegalArgumentException();
        if (ammount > account.getBalance()) return false;
        account.withdraw(ammount);      
        return true;
    }
    
}


