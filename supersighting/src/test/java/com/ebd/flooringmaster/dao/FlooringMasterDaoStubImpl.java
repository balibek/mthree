package com.ebd.flooringmaster.dao;

import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlooringMasterDaoStubImpl implements FlooringMasterDao {

    public Order onlyOrder;
    public Tax onlyTax;
    public Product onlyProduct;

    public FlooringMasterDaoStubImpl() {
        onlyTax = new Tax("CA", "Calfornia", new BigDecimal(25.00));
        onlyProduct = new Product("Tile", new BigDecimal(3.50), new BigDecimal(4.15));
        onlyOrder = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal(249.00),
                onlyTax,
                onlyProduct);
    }

    public FlooringMasterDaoStubImpl(Order onlyOrder, Tax onlyTax, Product onlyProduct) {
        this.onlyOrder = onlyOrder;
        this.onlyTax = onlyTax;
        this.onlyProduct = onlyProduct;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException {
        LocalDate orderDateSelect = LocalDate.of(2013, 6, 1);
        if (orderDate.equals(orderDateSelect) && orderNum == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersOfDate(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        LocalDate orderDateSelect = LocalDate.of(2013, 6, 1);
        if (orderDate.equals(orderDateSelect)) {
            List<Order> orderList = new ArrayList<>();
            orderList.add(onlyOrder);
            return orderList;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        LocalDate orderDateSelect = LocalDate.of(2013, 6, 1);
        if (orderDate.equals(orderDateSelect)) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order updatedOrder) throws FlooringMasteryPersistenceException {
        LocalDate orderDateSelect = LocalDate.of(2013, 6, 1);
        if (orderDate.equals(orderDateSelect)) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException {
        LocalDate orderDateSelect = LocalDate.of(2013, 6, 1);
        if (orderDate.equals(orderDateSelect) && orderNum == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryPersistenceException {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }

    @Override
    public List<Tax> getTaxes() throws FlooringMasteryPersistenceException {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(onlyTax);
        return taxList;
    }

    @Override
    public void exportData() throws FlooringMasteryPersistenceException {}

    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Tax getTax(String stateAbbreviation) throws FlooringMasteryPersistenceException {
        if (stateAbbreviation.equals(onlyTax.getStateAbbreviation())) {
            return onlyTax;
        } else {
            return null;
        }
    }

}
