package com.balibek.vendingmachine.service;

import com.balibek.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.balibek.vendingmachine.dao.VendingMachineDaoFileImpl.changeCoins;
import com.balibek.vendingmachine.dao.VendingMachinePersistenceException;
import com.balibek.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {

    List<Item> getAllItems() throws
            VendingMachinePersistenceException;

    BigDecimal addMoney(BigDecimal amoutOfMoney) throws
            VendingMachineDataValidationException,
            VendingMachinePersistenceException;

    Item getItem(String id) throws
            VendingMachinePersistenceException;

    BigDecimal getBalance() throws
            VendingMachinePersistenceException;

    boolean decreaseItemCount(Item selectedItem) throws
            VendingMachinePersistenceException;

    boolean decreaseBalance(Item selectedItem) throws
            VendingMachinePersistenceException;

    boolean checkCountOfItem(Item selectedItem) throws
            NoItemInventoryException,
            VendingMachinePersistenceException;
    
    boolean checkBalance(Item selectedItem) throws
            InsufficientFundsException,
            VendingMachinePersistenceException;

    changeCoins returnChange() throws
            VendingMachinePersistenceException;

}
