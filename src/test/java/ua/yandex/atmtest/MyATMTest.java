package ua.yandex.atmtest;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

public class MyATMTest {

    @Test
    public void testCheckBalanceWithZeroMoneyOnAccount() {
        double moneyOnAccount = 0.0;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);
        double expResult = 0.0;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCheckBalanceWithSomeMoneyOnAccount() {
        double moneyOnAccount = 42.42;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);
        double expResult = moneyOnAccount;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCheckBalanceWithoutWithdrow() {
        Account account = mock(Account.class);
       
        ATM atm = new MyATM(account);
        atm.checkBalance();
        verify(account, never()).withdraw(anyDouble());
    }

    @Test
    public void testGetCashWithZeroMoneyOnAccount() {
        double moneyOnAccount = 0;
        double ammount = 10;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        MyATM atm = new MyATM(account);
        MyATM spyATM = spy(atm);
        when(spyATM.isWorkingDay()).thenReturn(true);        

        assertFalse(atm.getCash(ammount));
    }

    @Test
    public void testGetCashWhenNotEnoughMoneyOnAccount() {
        double moneyOnAccount = 5.3;
        double ammount = 5.4;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        MyATM atm = new MyATM(account);
        MyATM spyATM = spy(atm);
        when(spyATM.isWorkingDay()).thenReturn(true);

        assertFalse(atm.getCash(ammount));
    }

    @Test
    public void testGetCashWhenEnoughMoneyOnAccount() {
        double moneyOnAccount = 25.3;
        double ammount = 5.4;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        MyATM atm = spy(new MyATM(account));       
        when(atm.isWorkingDay()).thenReturn(true);

        assertTrue(atm.getCash(ammount));
    }

    @Test
    public void testGetCashEqualsToMoneyOnAccount() {
        double moneyOnAccount = 25.32;
        double ammount = 25.32;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        MyATM atm = spy(new MyATM(account));       
        when(atm.isWorkingDay()).thenReturn(true);

        assertTrue(atm.getCash(ammount));
    }

    @Test
    public void testGetCashWithdrowCalledWithCorrectAmmountOnlyOnce() {
        double ammount = 25;
        double moneyOnAccount = 25.32;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        MyATM atm = spy(new MyATM(account));       
        when(atm.isWorkingDay()).thenReturn(true);
        
        atm.getCash(ammount);

        verify(account).withdraw(ammount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCashEqualsToZeroThrowException() {
        double ammount = 0;

        MyATM atm = spy(new MyATM(null));       
        when(atm.isWorkingDay()).thenReturn(true);
        
        atm.getCash(ammount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCashNegativeAmmountOfMoneyThrowException() {
        double ammount = -12.1;

        MyATM atm = spy(new MyATM(null));       
        when(atm.isWorkingDay()).thenReturn(true);
        
        atm.getCash(ammount);
    }

    @Test
    public void testGetCashMethodsCallsInCorrectOrder() {
        double moneyOnAccount = 25.32;
        double ammount = 10.6;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);
        
        MyATM atm = spy(new MyATM(account));       
        when(atm.isWorkingDay()).thenReturn(true);
        
        atm.getCash(ammount);
        
        InOrder order = inOrder(account);
        order.verify(account, atLeastOnce()).getBalance();
        order.verify(account).withdraw(anyDouble());
    }

    
    @Test
    public void testGetCashReturnFalseAndDoesntCallWithdrowAtNotWorkingDays() {        
        double ammount = 10.6;
        Account account = mock(Account.class);
                
        MyATM atm = new MyATM(null);
        MyATM spyATM = spy(atm);
        when(spyATM.isWorkingDay()).thenReturn(false);
        
        assertFalse(spyATM.getCash(ammount));
        verify(account, never()).withdraw(anyDouble());
    }
}
