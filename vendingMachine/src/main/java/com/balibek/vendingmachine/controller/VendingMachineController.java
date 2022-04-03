/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balibek.vendingmachine.controller;

import com.balibek.vendingmachine.dao.VendingMachineDao;
import com.balibek.vendingmachine.dao.VendingMachinePersistenceException;
import com.balibek.vendingmachine.dao.VendingMachineDaoFileImpl.changeCoins;
import com.balibek.vendingmachine.dto.Item;
import com.balibek.vendingmachine.service.InsufficientFundsException;
import com.balibek.vendingmachine.service.NoItemInventoryException;
import com.balibek.vendingmachine.service.VendingMachineDataValidationException;
import com.balibek.vendingmachine.service.VendingMachineServiceLayer;
import com.balibek.vendingmachine.ui.UserIo;
import com.balibek.vendingmachine.ui.UserIoConsoleImpl;
import com.balibek.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;


public class VendingMachineController {

    //private UserIo io = new UserIoConsoleImpl();
    private VendingMachineView view; // = new VendingMachineView();
   // private VendingMachineDao vendingMachine; // = new VendingMachineDaoFileImpl();
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            listItems();
            while (keepGoing) {

                menuSelection = view.printMenuAndGetSelection(service.getBalance());

                switch (menuSelection) {
                    case 1:
                        putMoneyToVendingMachine();
                        listItems();
                        Item selectedItem = selectItemToBuy();

                        //check if user can buy this item
                        if (enoughMoney(selectedItem)) {
                            //if user can buy proceed to buy
                            if (processBuy(selectedItem)) {
                                returnChange();
                                keepGoing = false;
                            }
                        } else {
                            keepGoing = false;
                            //if user cannot return
                        }
                        //}
                        break;
                    case 2:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();

        } catch (VendingMachinePersistenceException e) {
            view.displayErrorBanner(e.getMessage());
        }

    }

    private void unknownCommand() {
        view.displayUnknownBanner();
    }

    private void exitMessage() {
        view.displayGoodByeBanner();
    }

    private void listItems() throws VendingMachinePersistenceException {
        view.displayDisplayAllBanner();
        List<Item> ItemsList = service.getAllItems();
        view.displayItemsList(ItemsList);
    }

    private void putMoneyToVendingMachine() throws VendingMachinePersistenceException {

        boolean hasErrors = false;
        do {
            BigDecimal userEnterMoney = view.getMoney();
            try {
                service.addMoney(userEnterMoney);
                view.displayCurrentBalance(service.getBalance());
                hasErrors = false;
            } catch (VendingMachineDataValidationException e) {
                hasErrors = true;
                view.displayErrorBanner(e.getMessage());
            }
        } while (hasErrors);

    }

    private Item selectItemToBuy() throws VendingMachinePersistenceException {

        String id = view.displayChoiceOfItemsBanner();
        try {
            service.checkCountOfItem(service.getItem(id));
        } catch (NoItemInventoryException e) {
            view.displayErrorBanner(e.getMessage());
        }
        return service.getItem(id);
        // } while (hasErrors);
        //return service.getItem(id);// vendingMachine.getItem(id);
    }

    private boolean enoughMoney(Item selectedItem) throws VendingMachinePersistenceException {
        
        try {
            service.checkBalance(selectedItem);
        } catch (InsufficientFundsException e) {
            view.displayErrorBanner(e.getMessage());
        }
        return true;
        //return vendingMachine.checkBalance(selectedItem);
//        BigDecimal balance = vendingMachine.getBalance();
//        BigDecimal costItem = new BigDecimal(selectedItem.getCost());
//        return balance.compareTo(costItem) >= 0;
    }

    private boolean processBuy(Item selectedItem) throws VendingMachinePersistenceException {
        boolean successBuy = false;
        //decrease Item count
        if (!service.decreaseItemCount(selectedItem)) {
            return false;
        }
        //decrease balance
        if (!service.decreaseBalance(selectedItem)) {
            return false;
        }
        //Success buy banner
        view.displaySuccessBuy();
        return true;

    }

    private void returnChange() throws VendingMachinePersistenceException {
        changeCoins change = service.returnChange();
        view.displayAllChangeAmount();
        view.displayCountOfQauter(change.quatersCount);
        view.displayCountOfDime(change.dimesCount);
        view.displayCountOfNickel(change.nickelsCount);
        view.displayCountOfPenny(change.penniesCount);

    }

}
