package com.ebd.flooringmaster.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tax {
    private final String stateAbbreviation;
    private final String stateName;
    private final BigDecimal taxRate;

    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tax tax = (Tax) o;

        if (stateAbbreviation != null ? !stateAbbreviation.equals(tax.stateAbbreviation) : tax.stateAbbreviation != null)
            return false;
        if (stateName != null ? !stateName.equals(tax.stateName) : tax.stateName != null) return false;
        return taxRate != null ? taxRate.equals(tax.taxRate) : tax.taxRate == null;
    }

    @Override
    public int hashCode() {
        int result = stateAbbreviation != null ? stateAbbreviation.hashCode() : 0;
        result = 31 * result + (stateName != null ? stateName.hashCode() : 0);
        result = 31 * result + (taxRate != null ? taxRate.hashCode() : 0);
        return result;
    }
}
