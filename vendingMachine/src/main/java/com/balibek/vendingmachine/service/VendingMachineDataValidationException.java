
package com.balibek.vendingmachine.service;


public class VendingMachineDataValidationException extends Exception {
    
    public VendingMachineDataValidationException(String message) {
        super(message);
    }
    
    public VendingMachineDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
