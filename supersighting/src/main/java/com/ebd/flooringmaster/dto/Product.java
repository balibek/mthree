package com.ebd.flooringmaster.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    private final String productType;
    private final BigDecimal costPerSquareFoot;
    private final BigDecimal laborCostPerSquareFoot;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productType != null ? !productType.equals(product.productType) : product.productType != null) return false;
        if (costPerSquareFoot != null ? !costPerSquareFoot.equals(product.costPerSquareFoot) : product.costPerSquareFoot != null)
            return false;
        return laborCostPerSquareFoot != null ? laborCostPerSquareFoot.equals(product.laborCostPerSquareFoot) : product.laborCostPerSquareFoot == null;
    }

    @Override
    public int hashCode() {
        int result = productType != null ? productType.hashCode() : 0;
        result = 31 * result + (costPerSquareFoot != null ? costPerSquareFoot.hashCode() : 0);
        result = 31 * result + (laborCostPerSquareFoot != null ? laborCostPerSquareFoot.hashCode() : 0);
        return result;
    }
}
