
package com.balibek.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


public class Money {
    
    private BigDecimal amoutOfMoney;
    
   
    public Money() {
        this.amoutOfMoney = BigDecimal.ZERO;
    }

    public BigDecimal getAmoutOfMoney() {
        return amoutOfMoney;
    }

    public void setAmoutOfMoney(BigDecimal amoutOfMoney) {
        this.amoutOfMoney = amoutOfMoney.setScale(2, RoundingMode.HALF_UP);
    }
    
    public void addAmoutOfMoney(BigDecimal amoutOfMoney) {
        this.amoutOfMoney = this.amoutOfMoney.add(amoutOfMoney.setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.amoutOfMoney);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Money other = (Money) obj;
        return Objects.equals(this.amoutOfMoney, other.amoutOfMoney);
    }

    @Override
    public String toString() {
        return "Money{" + "amoutOfMoney=" + amoutOfMoney + '}';
    }
    

}
