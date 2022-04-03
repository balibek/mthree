package com.balibek.vendingmachine.service;

import com.balibek.vendingmachine.dao.VendingMachineAuditDao;
import com.balibek.vendingmachine.dao.VendingMachineDao;
import com.balibek.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.balibek.vendingmachine.dao.VendingMachinePersistenceException;
import com.balibek.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    VendingMachineDao vendingMachine;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao vendingMachine, VendingMachineAuditDao auditDao) {
        this.vendingMachine = vendingMachine;
        this.auditDao = auditDao;
    }

    private void validateEnoughBalanceToBuy(Item selectedItem) throws InsufficientFundsException {
        BigDecimal amountOfMoney = vendingMachine.getBalance();
        BigDecimal itemCost = new BigDecimal(selectedItem.getCost());

        if (amountOfMoney.compareTo(itemCost) == -1) {
            throw new InsufficientFundsException("ERROR: Not enough money to buy. Please add money.");
        }

    }

    private void validateInventoryToBuy(Item selectedItem) throws NoItemInventoryException {
        if (selectedItem.getCountOfItem() <= 0) {
            throw new NoItemInventoryException("ERROR: Not enough item to buy.");
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return vendingMachine.getAllItems();
    }

    private void validateBalanceData(BigDecimal amoutOfMoney) throws VendingMachineDataValidationException {
        if (amoutOfMoney == null || amoutOfMoney.compareTo(new BigDecimal(0)) == -1 || amoutOfMoney.compareTo(new BigDecimal(0)) == 0) {
            throw new VendingMachineDataValidationException(
                    "ERROR: You can't enter amount of money less or equal 0");
        }
    }

    @Override
    public BigDecimal addMoney(BigDecimal amoutOfMoney) throws VendingMachineDataValidationException, VendingMachinePersistenceException {
        validateBalanceData(amoutOfMoney);
        auditDao.writeAuditEntry("Balance " + amoutOfMoney + " Added");
        return vendingMachine.addMoney(amoutOfMoney);
    }

    @Override
    public Item getItem(String id) throws VendingMachinePersistenceException {
        return vendingMachine.getItem(id);
    }

    @Override
    public BigDecimal getBalance() throws VendingMachinePersistenceException {
        return vendingMachine.getBalance();
    }

    @Override
    public boolean decreaseItemCount(Item selectedItem) throws VendingMachinePersistenceException {
        return vendingMachine.decreaseItemCount(selectedItem);
    }

    @Override
    public boolean decreaseBalance(Item selectedItem) throws VendingMachinePersistenceException {
        return vendingMachine.decreaseBalance(selectedItem);
    }

    @Override
    public boolean checkCountOfItem(Item selectedItem) throws NoItemInventoryException, VendingMachinePersistenceException {
        validateInventoryToBuy(selectedItem);
        return vendingMachine.checkCountOfItem(selectedItem);
    }

    @Override
    public VendingMachineDaoFileImpl.changeCoins returnChange() throws VendingMachinePersistenceException {
        return vendingMachine.returnChange();
    }

    @Override
    public boolean checkBalance(Item selectedItem) throws InsufficientFundsException, VendingMachinePersistenceException {
        validateEnoughBalanceToBuy(selectedItem);
        return vendingMachine.checkBalance(selectedItem);
    }

}
