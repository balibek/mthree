
package com.ebd.flooringmaster.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order {
    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    private Tax tax;
    private Product product;

//    MaterialCost = (Area * CostPerSquareFoot)
//    LaborCost = (Area * LaborCostPerSquareFoot)
//    Tax = (MaterialCost + LaborCost) * (TaxRate/100)
//    Total = (MaterialCost + LaborCost + Tax)


    public Order(int orderNumber, String customerName, BigDecimal area, Tax tax, Product product) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.area = area;
        this.tax = tax;
        this.product = product;
    }

    public BigDecimal getMaterialCost() {
        return this.area
                .multiply(product.getCostPerSquareFoot())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCost() {
        return this.area
                .multiply(product.getLaborCostPerSquareFoot())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalTax() {
        return (this.getMaterialCost().add(this.getLaborCost()))
                .multiply(this.tax.getTaxRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return this.getMaterialCost()
                .add(this.getLaborCost())
                .add(getTotalTax())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getArea() {
        return area.setScale(2, RoundingMode.HALF_UP);
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderNumber != order.orderNumber) return false;
        if (customerName != null ? !customerName.equals(order.customerName) : order.customerName != null) return false;
        if (area != null ? !area.equals(order.area) : order.area != null) return false;
        if (tax != null ? !tax.equals(order.tax) : order.tax != null) return false;
        return product != null ? product.equals(order.product) : order.product == null;
    }

    @Override
    public int hashCode() {
        int result = orderNumber;
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (tax != null ? tax.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
