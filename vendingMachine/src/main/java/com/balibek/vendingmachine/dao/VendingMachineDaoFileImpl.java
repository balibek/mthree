package com.balibek.vendingmachine.dao;

import com.balibek.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.balibek.vendingmachine.dto.Money;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private Map<String, Item> items = new HashMap<>();
    private Money balance = new Money();

    private final String INVENTORY_FILE; // = "inventory.txt";
    public static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl() {
       INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String inventoryTextFile) {
        INVENTORY_FILE = inventoryTextFile;
    }

    private Item unmarshallItems(String ItemAsText) {

        String[] ItemTokens = ItemAsText.split(DELIMITER);

        String id = ItemTokens[0];

        Item ItemsFromFile = new Item(id);
        ItemsFromFile.setTitle(ItemTokens[1]);
        ItemsFromFile.setCountOfItem(Integer.parseInt(ItemTokens[2]));
        ItemsFromFile.setCost(ItemTokens[3]);

        return ItemsFromFile;
    }

    @Override
    public void loadInventory() {
        Scanner scanner = null;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            try {
                throw new VendingMachinePersistenceException(
                        "-_- Could not load roster data into memory.", e);
            } catch (VendingMachinePersistenceException ex) {
                Logger.getLogger(VendingMachineDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String currentLine;

        Item currentItems;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentItems = unmarshallItems(currentLine);
            items.put(currentItems.getId(), currentItems);

        }
        scanner.close();
    }

    private String marchallItems(Item aItems) {
        String ItemsAsText = aItems.getId() + DELIMITER;
        ItemsAsText += aItems.getTitle() + DELIMITER;
        ItemsAsText += aItems.getCountOfItem() + DELIMITER;
        ItemsAsText += aItems.getCost() + DELIMITER;
        return ItemsAsText;
    }

    @Override
    public void writeInventory() {

        PrintWriter out = null; //= null;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            try {
                throw new VendingMachinePersistenceException(
                        "Could not save data.", e);
            } catch (VendingMachinePersistenceException ex) {
                Logger.getLogger(VendingMachineDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String ItemsAsText;
        List<Item> ItemsList = this.getAllItems();

        for (Item currentItem : ItemsList) {
            ItemsAsText = marchallItems(currentItem);

            out.println(ItemsAsText);

            out.flush();
        }
        out.close();
    }

    @Override
    public List<Item> getAllItems() {
        loadInventory();
        return new ArrayList<Item>(items.values());

    }

    @Override
    public BigDecimal addMoney(BigDecimal amoutOfMoney) {
        balance.addAmoutOfMoney(amoutOfMoney);
        return balance.getAmoutOfMoney();
    }

    @Override
    public Item getItem(String id) {
        return items.get(id);
    }


    @Override
    public BigDecimal getBalance() {
        return balance.getAmoutOfMoney();
    }

    @Override
    public changeCoins returnChange() {
        changeCoins result = new changeCoins();
        BigDecimal currentBalance = balance.getAmoutOfMoney();
        result.quatersCount = currentBalance.divide(quater, 0, RoundingMode.DOWN);

        currentBalance = currentBalance.subtract(result.quatersCount.multiply(quater.setScale(2, RoundingMode.HALF_UP)));
        result.dimesCount = currentBalance.divide(dime, 0, RoundingMode.DOWN);

        currentBalance = currentBalance.subtract(result.dimesCount.multiply(dime.setScale(2, RoundingMode.HALF_UP)));
        result.nickelsCount = currentBalance.divide(nickel, 0, RoundingMode.DOWN);

        currentBalance = currentBalance.subtract(result.nickelsCount.multiply(nickel.setScale(2, RoundingMode.HALF_UP)));
        result.penniesCount = currentBalance.divide(penny, 0, RoundingMode.UP);

        balance.setAmoutOfMoney(BigDecimal.ZERO);

        return result;

    }

    BigDecimal quater = new BigDecimal(Coins.QUARTER.getValue());
    BigDecimal dime = new BigDecimal(Coins.DIME.getValue());
    BigDecimal nickel = new BigDecimal(Coins.NICKEL.getValue());
    BigDecimal penny = new BigDecimal(Coins.PENNY.getValue());

    @Override
    public boolean decreaseItemCount(Item selectedItem) {
        if (selectedItem == null) {
            return false;
        }
        if (selectedItem.getCountOfItem() <= 0) {
            return false;
        }

        selectedItem.setCountOfItem(selectedItem.getCountOfItem() - 1);
        writeInventory();
        return true;
       
    }

    @Override
    public boolean decreaseBalance(Item selectedItem) {    
        BigDecimal itemCost = new BigDecimal(selectedItem.getCost());
        if (itemCost.compareTo(balance.getAmoutOfMoney()) == 1) {
            return false;
        }
        balance.setAmoutOfMoney(balance.getAmoutOfMoney().subtract(itemCost));
        return true;
    }

    @Override
    public boolean checkCountOfItem(Item selectedItem) {
       // Item selectedToBuyItem = getItem(id);
        if (selectedItem.getCountOfItem() <= 0) {
            return false;
        }
        //int numberOfItem = selectedToBuyItem.getCountOfItem();
        return true;
    }

    @Override
    public boolean checkBalance(Item selectedItem) {
        //Item selectedToBuyItem = getItem(id);
        BigDecimal itemCost = new BigDecimal(selectedItem.getCost());
        BigDecimal currentBalance = balance.getAmoutOfMoney();
        if(currentBalance.compareTo(itemCost) >= 0)
            return true;
        else
            return false;
    }

    public enum Coins {
        QUARTER(0.25),
        DIME(0.10),
        NICKEL(0.05),
        PENNY(0.01);

        private double value;

        private Coins(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    public class changeCoins {

        public BigDecimal quatersCount;
        public BigDecimal dimesCount;
        public BigDecimal nickelsCount;
        public BigDecimal penniesCount;

    }
}
