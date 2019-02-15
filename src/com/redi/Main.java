package com.redi;

import java.util.Arrays;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        Machine drinks = new Machine("Drink It Up!",9,10,new TreeSet<Double>(Arrays.asList(0.05,0.10,0.20,0.50,1.0,2.0,5.0,10.0)));

        drinks.addItem(new Product("A1","Coca Cola",1.50),3);
        drinks.addItem(new Product("A2","Fanta",1.25),4);
        drinks.addItem(new Product("A3","Sprite",1.25),0);
        drinks.addItem(new Product("B1","Chinotto",2.30),5);
        drinks.addItem(new Product("B2","Cedrata",2.00),7);
        drinks.addItem(new Product("B3","Crodino",2.50),0);

        drinks.refurbish("A2",11);
        drinks.refurbish("A5",1);
        drinks.refurbish("A2",3);

        drinks.info();

        drinks.use();

    }
}
