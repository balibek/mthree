
package com.balibek.vendingmachine.dao;

import com.balibek.vendingmachine.dao.VendingMachineAuditDao;
import com.balibek.vendingmachine.dao.VendingMachinePersistenceException;

public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        //do nothing
    }
    
}
