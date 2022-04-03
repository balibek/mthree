package com.balibek.vendingmachine.dao;


import com.balibek.vendingmachine.dto.Item;
import com.balibek.vendingmachine.dto.Money;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item onlyItem;
    public Money onlyMoney;

    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("5");
        onlyItem.setTitle("Candy");
        onlyItem.setCountOfItem(7);
        onlyItem.setCost("2.1");

        onlyMoney = new Money();
        onlyMoney.setAmoutOfMoney(new BigDecimal(3));
    }

    public VendingMachineDaoStubImpl(Item testItem, Money testMoney) {
        this.onlyItem = testItem;
        this.onlyMoney = testMoney;
    }

    @Override
    public void loadInventory() {
        //do not nothing
    }

    @Override
    public void writeInventory() {
        //do not nothing
    }

    @Override
    public BigDecimal addMoney(BigDecimal amoutOfMoney) {
        if (amoutOfMoney.equals(onlyMoney.getAmoutOfMoney())) {
            return onlyMoney.getAmoutOfMoney();
        } else {
            return null;
        }
    }

    @Override
    public Item getItem(String id) {
        if (id.equals(onlyItem.getId())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public BigDecimal getBalance() {
        return onlyMoney.getAmoutOfMoney();
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public VendingMachineDaoFileImpl.changeCoins returnChange() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean decreaseItemCount(Item selectedItem) {
        if (onlyItem.getCountOfItem() <= 0) {
            return false;
        }
        onlyItem.setCountOfItem(onlyItem.getCountOfItem() - 1);
        return true;
    }

    @Override
    public boolean decreaseBalance(Item selectedItem) {
        BigDecimal itemCost = new BigDecimal(onlyItem.getCost());
        if (itemCost.compareTo(onlyMoney.getAmoutOfMoney()) == 1) {
            return false;
        }
        onlyMoney.setAmoutOfMoney(onlyMoney.getAmoutOfMoney().subtract(itemCost));
        return true;
    }

    @Override
    public boolean checkCountOfItem(Item selectedItem) {
        if (onlyItem.getCountOfItem() <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkBalance(Item selectedItem) {
        BigDecimal itemCost = new BigDecimal(onlyItem.getCost());
        BigDecimal currentBalance = onlyMoney.getAmoutOfMoney();
        if (currentBalance.compareTo(itemCost) >= 0) {
            return true;
        }
        return false;
    }

}
