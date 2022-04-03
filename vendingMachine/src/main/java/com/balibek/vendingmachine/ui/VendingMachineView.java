package com.balibek.vendingmachine.ui;

import com.balibek.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {

    private UserIo io; // = new UserIoConsoleImpl();

    public VendingMachineView(UserIo io) {
        this.io = io;
    }

    public int printMenuAndGetSelection(BigDecimal balance) {

        io.print("====================================================================");
        io.print("1. Put money");
        io.print("2. Exit");
        io.print("Your balance " + balance);

        return io.readInt("Please select from the above choices.", 1, 2);
    }

    public void displayItemsList(List<Item> items) {
        for (Item item : items) {
            String ItemsInfo
                    = String.format("#%s : Item name: %s, Number of item: %s, Item cost: %s",
                            item.getId(),
                            item.getTitle(),
                            item.getCountOfItem(),
                            item.getCost());
            io.print(ItemsInfo);
        }
    }

    public void displayGoodByeBanner() {
        io.print("  ");
        io.print("Good Bye!!!");
    }

    public void displayUnknownBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorBanner(String Msg) {
        io.print("=====|| ERROR||=====");
        io.print(Msg);
    }

    public void displayDisplayAllBanner() {
        io.print("=== DISPLAY All VENDING MACHINE'S ITEMS ===");
    }

    public BigDecimal getMoney() {

        BigDecimal amoutOfMoney = io.readBigDecimal("Please enter amount of money in 0.00 format.");
        return amoutOfMoney;

    }

    public void displayCurrentBalance(BigDecimal balance) {
        io.print("You current balance: " + balance + " dollar.");
    }

    public String displayChoiceOfItemsBanner() {
        return io.readString("Please enter item id from the above items' list.");
    }


    public void displaySuccessBuy() {
        io.print("=== THANK YOU FOY YOUR PURCHASE! ===");
    }

    public void displayAllChangeAmount() {
        io.print("  ");
        io.print("Your Change: ");
    }

    public void displayCountOfQauter(BigDecimal quatersCount) {
        io.print("QUATERS: " + quatersCount);
    }

    public void displayCountOfDime(BigDecimal dimesCount) {
        io.print("DIMES: " + dimesCount);
    }

    public void displayCountOfNickel(BigDecimal nickelsCount) {
        io.print("NICKELS: " + nickelsCount);
    }

    public void displayCountOfPenny(BigDecimal penniesCount) {
        io.print("PENNIES: " + penniesCount);
    }

}
