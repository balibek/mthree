package com.ebd.flooringmaster.service;

import com.ebd.flooringmaster.dao.FlooringMasteryPersistenceException;
import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasterServiceLayer {
    Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException, NoSuchOrderException;
    List<Order> getOrdersOfDate(LocalDate orderDate) throws FlooringMasteryPersistenceException, NoSuchOrderException;
    Order addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException;
    Order editOrder(LocalDate orderDate, Order updatedOrder) throws FlooringMasteryPersistenceException;
    Order removeOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException;
    void fillOrderDetails(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException;
    List<Product> getProducts() throws FlooringMasteryPersistenceException;
    List<Tax> getTaxes() throws FlooringMasteryPersistenceException;
    void exportData() throws FlooringMasteryPersistenceException;
    Product getProduct(String productType) throws FlooringMasteryPersistenceException;
    Tax getTax(String stateAbbreviation) throws FlooringMasteryPersistenceException;
    int generateNextOrderNum(LocalDate orderDate) throws FlooringMasteryPersistenceException;

    boolean isNewOrderDateValid(LocalDate orderDate);

    boolean isCustomerNameValid(String name);

    boolean isAreaValid(BigDecimal area);
}
