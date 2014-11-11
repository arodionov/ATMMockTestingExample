/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.atmtest;

/**
 *
 * @author andrii
 */
public interface Account {

    public double getBalance();

    public boolean withdraw(double ammount);
    
}
