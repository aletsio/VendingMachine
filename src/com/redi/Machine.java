package com.redi;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Machine {

    String name;
    int itemSlots; //Maximum number of slots (each containing a certain amount of the same item)
    int slotCapacity; //Maximum capacity of each slot (how many items it can contain)
    TreeSet<Double> acceptedMoneySize;
    ArrayList<Item> inventory = new ArrayList<>();
    TreeMap<String, Integer> inventoryRef = new TreeMap<>(); //Map which links a code to the matching product's index inside the ArrayList inventory

    Scanner scanner = new Scanner(System.in);

    //Constructor
    public Machine(String name, int itemSlots, int slotCapacity, TreeSet<Double> acceptedMoneySize) {
        this.name = name;
        this.itemSlots = itemSlots;
        this.slotCapacity = slotCapacity;
        this.acceptedMoneySize = acceptedMoneySize;
    }

    //Show information about the machine (name, capacity, unused slots, current content, accepted money size).
    public void info() {
        System.out.println("Name: " + name);
        System.out.println("This machine has a total of " + itemSlots + " slots, " + (itemSlots - inventory.size()) + " of which are currently unused.");
        System.out.println("Here is the current content of the " + inventory.size() + " used slots: ");
        for (Item k : inventory) {
            System.out.println(k.quantity + " x " + k.name);
        }
        System.out.print("This machine accepts the following coins and banknotes: ");
        for (double i : acceptedMoneySize) {
            System.out.print("€ " + i + "  ");
        }
        System.out.println();
    }

    //Add a new product.
    public void addItem(String code, String name, double price, int quantity) {
        if (inventory.size() == itemSlots) {
            System.out.println("There are no available slots for new products!");
        } else {
            inventory.add(new Item(code, name, price, quantity));
            inventoryRef.put(code.toLowerCase(), inventory.size() - 1);
        }
    }

    //Select product by code.
    public Item selectItem(String code) {
        return inventory.get(inventoryRef.get(code.toLowerCase()));
    }

    //Add a certain amount of an existing product.
    public void refurbish(String code, int quantity) {
        if (!inventoryRef.containsKey(code.toLowerCase())) {
            System.out.println("This code is not associated to any product! Please enter a correct code.");
        } else {
            if (quantity > slotCapacity || (quantity + selectItem(code).quantity) > slotCapacity) {
                System.out.println("The quantity exceeds the maximum slot capacity! Please enter a smaller amount (max. " + (slotCapacity - selectItem(code).quantity) + ").");
            } else {
                selectItem(code).quantity += quantity;
            }
        }
        System.out.println("The machine has been refurbished!");
    }

    //Withdraw a product.
    public void withdraw(String code) {
        selectItem(code).quantity -= 1;
        System.out.println("Enjoy your " + selectItem(code).name + "!");
    }

    //Show the list of available products along with their codes.
    public void showProducts() {
        for (Item i : inventory) {
            System.out.print(i.code + " " + i.name + "  ");
        }
    }

    //Buy a product.
    public void buy() {
        System.out.println("Select a product by typing its code: ");
        showProducts();
        System.out.println();
        String code = scanner.next();
        while (!inventoryRef.containsKey(code) || selectItem(code).quantity == 0) {
            if(!inventoryRef.containsKey(code)){
                System.out.println("Wrong code! Try again.");
            }else{
                System.out.println(selectItem(code).name + " is sold out, sorry! Please choose another product.");
            }
            code = scanner.next();
        }
        pay(selectItem(code).price);
        withdraw(code);
    }

    //Pay for the selected product.
    public void pay(double price){
        System.out.print("Please pay " + price + " Euro. ");
        System.out.println("Insert coins or banknotes (type the amount - use a comma for decimals).");
        double insertedMoney = scanner.nextDouble();
        double currentAmount = 0.0;
        do{
            if(!acceptedMoneySize.contains(insertedMoney)){
                System.out.print("This machine only accepts the following coins and banknotes: ");
                for (double i : acceptedMoneySize) {
                    System.out.print("€ " + i + "  ");
                }
                System.out.println();
                insertedMoney = scanner.nextDouble();
            }else{
                currentAmount+=insertedMoney;
                if(currentAmount<price){
                    System.out.println("Still due: € " + (price - currentAmount));
                    insertedMoney = scanner.nextDouble();
                }
            }
        }while(!acceptedMoneySize.contains(insertedMoney) || currentAmount<price);
        if(currentAmount>price){
            System.out.println("Thank you! Don't forget your change: € " + (Math.abs(price-currentAmount) + "."));
        }
    }

    //Allow the user to buy one or more products (cycle through the buy() method)
    public void use(){
        String answer;
        do{
            buy();
            System.out.println("Would you like to buy another product? [y/n]");
            answer = scanner.next();
            if(!answer.equals("y")&&!answer.equals("n")&&!answer.equals("yes")){
                System.out.println("I'll take that as a no.");
            }
        }while(answer.equals("y"));
        System.out.println("Ok, have a nice day!");
    }

}