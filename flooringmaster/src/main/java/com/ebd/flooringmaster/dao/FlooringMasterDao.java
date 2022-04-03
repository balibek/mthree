
package com.ebd.flooringmaster.dao;

import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasterDao {
    Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException;
    List<Order> getOrdersOfDate(LocalDate orderDate) throws FlooringMasteryPersistenceException;
    Order addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException;
    Order editOrder(LocalDate orderDate, Order updatedOrder) throws FlooringMasteryPersistenceException;
    Order removeOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException;
    List<Product> getProducts() throws FlooringMasteryPersistenceException;
    List<Tax> getTaxes() throws FlooringMasteryPersistenceException;
    void exportData() throws FlooringMasteryPersistenceException;
    Product getProduct(String productType) throws FlooringMasteryPersistenceException;
    Tax getTax(String stateAbbreviation) throws FlooringMasteryPersistenceException;
}
