
package com.balibek.vendingmachine.dao;

import com.balibek.vendingmachine.dao.VendingMachineDaoFileImpl.changeCoins;
import com.balibek.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;


public interface VendingMachineDao {
    
    void loadInventory();
    
    void writeInventory();
    
    BigDecimal addMoney(BigDecimal amoutOfMoney);
    
    Item getItem(String id);
    
    BigDecimal getBalance();
    
    List<Item> getAllItems();
    
    changeCoins returnChange();
    
    boolean decreaseItemCount(Item selectedItem);

    boolean decreaseBalance(Item selectedItem);
    
    boolean checkCountOfItem(Item selectedItem);
    
    boolean checkBalance(Item selectedItem);
    
}
