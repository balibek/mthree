package com.ebd.flooringmaster.service;

import com.ebd.flooringmaster.dao.FlooringMasterDao;
import com.ebd.flooringmaster.dao.FlooringMasteryPersistenceException;
import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasterServiceLayerImpl implements FlooringMasterServiceLayer {

    private final FlooringMasterDao dao;

    @Autowired
    public FlooringMasterServiceLayerImpl(FlooringMasterDao dao) {
        this.dao = dao;
    }


    @Override
    public Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException, NoSuchOrderException {
        Order order = dao.getOrder(orderDate, orderNum);

        if (order == null) {
            throw new NoSuchOrderException("No such order.");
        }

        return order;
    }

    @Override
    public List<Order> getOrdersOfDate(LocalDate orderDate) throws FlooringMasteryPersistenceException, NoSuchOrderException {
        List<Order> orders = dao.getOrdersOfDate(orderDate);

        if (orders.isEmpty()) {
            throw new NoSuchOrderException("No order for date: " + orderDate);
        }

        return orders;
    }

    /**
     * @param orderDate
     * @param order
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public Order addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        fillOrderDetails(orderDate, order);
        return dao.addOrder(orderDate, order);
    }
    
    @Override
    public int generateNextOrderNum(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        List<Order> orders = dao.getOrdersOfDate(orderDate);

        int maxOrderNum = orders
                .stream()
                .mapToInt(Order::getOrderNumber)
                .max()
                .orElse(0); // if no file exists

        return maxOrderNum + 1;
    }

    /**
     * Implied that getOrder is called before this method
     * @param orderDate
     * @param updatedOrder
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public Order editOrder(LocalDate orderDate, Order updatedOrder) throws FlooringMasteryPersistenceException {
        return dao.editOrder(orderDate, updatedOrder);
    }

    /**
     * Implied that getOrder is called before this method
     * @param orderDate
     * @param orderNum
     * @return
     * @throws FlooringMasteryPersistenceException
     * @throws NoSuchOrderException
     */
    @Override
    public Order removeOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException {
        return dao.removeOrder(orderDate, orderNum);
    }

    @Override
    public void fillOrderDetails(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        int nextOrderNum = generateNextOrderNum(orderDate);
        order.setOrderNumber(nextOrderNum);
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryPersistenceException {
        return dao.getProducts();
    }

    @Override
    public List<Tax> getTaxes() throws FlooringMasteryPersistenceException {
        return dao.getTaxes();
    }

    @Override
    public void exportData() throws FlooringMasteryPersistenceException {
        dao.exportData();
    }

    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        return dao.getProduct(productType);
    }

    @Override
    public Tax getTax(String stateAbbreviation) throws FlooringMasteryPersistenceException {
        return dao.getTax(stateAbbreviation);
    }

    // validate future,valid customer name, valid area

    @Override
    public boolean isNewOrderDateValid(LocalDate orderDate) {
        return orderDate.isAfter(LocalDate.now());
    }

    @Override
    public boolean isCustomerNameValid(String name) {
        return !name.isBlank() && name.matches("[A-Za-z0-9., ]+");
    }

    @Override
    public boolean isAreaValid(BigDecimal area) {
        return area.compareTo(BigDecimal.valueOf(100)) > -1;
    }
}
