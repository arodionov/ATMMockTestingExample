package ua.yandex.atmtest;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import static org.mockito.Matchers.anyDouble;
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
        //when(account.getBalance()).thenReturn(anyDouble());

        ATM atm = new MyATM(account);
        atm.checkBalance();
        verify(account, times(0)).withdraw(anyDouble());
    }

    @Test
    public void testGetCashWithZeroMoneyOnAccount() {
        double moneyOnAccount = 0;
        double ammount = 10;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);

        assertFalse(atm.getCash(ammount));
    }

    @Test
    public void testGetCashWhenNotEnoughMoneyOnAccount() {
        double moneyOnAccount = 5.3;
        double ammount = 5.4;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);

        assertFalse(atm.getCash(ammount));
    }

    @Test
    public void testGetCashWhenEnoughMoneyOnAccount() {
        double moneyOnAccount = 25.3;
        double ammount = 5.4;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);

        assertTrue(atm.getCash(ammount));
    }

    @Test
    public void testGetCashEqualsToMoneyOnAccount() {
        double moneyOnAccount = 25.32;
        double ammount = 25.32;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);

        assertTrue(atm.getCash(ammount));
    }

    @Test
    public void testGetCashWithdrowCalledWithCorrectAmmountOnlyOnce() {
        double ammount = 25;
        double moneyOnAccount = 25.32;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(moneyOnAccount);

        ATM atm = new MyATM(account);
        atm.getCash(ammount);

        verify(account, times(1)).withdraw(ammount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCashEqualsToZeroThrowException() {
        double ammount = 0;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(anyDouble());

        ATM atm = new MyATM(account);
        atm.getCash(ammount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCashNegativeAmmountOfMoneyThrowException() {
        double ammount = -12.1;

        ATM atm = new MyATM(null);
        atm.getCash(ammount);
    }

    @Test
    public void testGetCashMethodsCallsInCorrectOrder() {
        Account account = mock(Account.class);
        ATM atm = new MyATM(account);
        double ammount = 10.6;        
        when(account.getBalance()).thenReturn(ammount);
        atm.getCash(ammount);
        InOrder order = inOrder(account);
        order.verify(account, atLeastOnce()).getBalance();
        order.verify(account, times(1)).withdraw(anyDouble());
    }

}
