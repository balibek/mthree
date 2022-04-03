package com.ebd.flooringmaster.ui;

import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlooringMasterView {
    private final UserIO io;

    @Autowired
    public FlooringMasterView(UserIO io) {
        this.io = io;
    }

    public void displayErrorMessage(String message) {
        io.print("=== ERROR ===");
        io.print(message);
        io.readString("Please hit enter to continue.");
    }

    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("Choose from Above", 1, 6);
    }

    public void displayUnknownBanner() {
        io.print("=====|| UNKNOWN COMMAND||=====");
    }
    
    public void displayGoodByeBanner() {
        io.print("=====|| GOOD BYE ||=====");
    }

    public LocalDate getOrderDateChoice() {
        return io.readLocalDate("Please enter the Order Date in mm/dd/yyyy format");
    }

    public void displayBannerOrdersForDate() {
         io.print("=====|| DISPLAY ORDERS FOR SELECTING DATE ||=====");
    }


     public void printOrdersList(List<Order> orders) {
        for (Order currentOrders : orders) {
            String orderInfo
                    = String.format("#%s : %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                            currentOrders.getOrderNumber(),
                            currentOrders.getCustomerName(),
                            currentOrders.getTax().getStateAbbreviation(),
                            currentOrders.getTax().getTaxRate(),
                            currentOrders.getProduct().getProductType(),
                            currentOrders.getArea(),
                            currentOrders.getProduct().getCostPerSquareFoot(),
                            currentOrders.getProduct().getLaborCostPerSquareFoot(),
                            currentOrders.getMaterialCost(),
                            currentOrders.getLaborCost(),
                            currentOrders.getTotalTax(),
                            currentOrders.getTotal());
            io.print(orderInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayCreateOrderBanner() {
        io.print("=====|| ADD ORDER ||=====");
    }

    public String getCustomerName() {
        return io.readString("Please enter customer name");
    }

    public void printTaxList(List<Tax> tax) {
        for (Tax currentTax : tax) {
            String taxInfo
                    = String.format("%s, %s, %s",
                            currentTax.getStateAbbreviation(),
                            currentTax.getStateName(),
                            currentTax.getTaxRate());

            io.print(taxInfo);
        }
    }

    public String getStateAbr() {
        return io.readString("Please enter State Abbreviation");
    }

    public void printProductList(List<Product> products) {
        for (Product currentProducts : products) {
            String productsInfo
                    = String.format("%s, %s, %s",
                            currentProducts.getProductType(),
                            currentProducts.getCostPerSquareFoot(),
                            currentProducts.getLaborCostPerSquareFoot());

            io.print(productsInfo);
        }
    }

    public String getProductType() {
        return io.readString("Please enter product type");
    }

    public void displayOrderDateError() {
        io.print("Order Date must be in the future");
    }

    public BigDecimal getArea() {
        return io.readBigDecimal("Please enter area");
    }

    public void displayInvalidDataError() {
        io.print("Invalid input");
    }
    
    //error functions to streamline bugfixing for addorder
    public void displayInvalidAreaError() {
        io.print("Invalid area input (must be greater than or equal to 100)");
    }
    public void displayInvalidNameError() {
        io.print("Invalid customer name input (allowed to contain [a-z][0-9] as well as periods and comma characters).");
    }
    public void displayInvalidProductError() {
        io.print("Invalid product type input.");
    }
    public void displayInvalidTaxError() {
        io.print("Invalid state abbreviation input.");
    }

    public int getOrderNumChoice() {
        return io.readInt("Enter an order number:");
    }

    public void displayOrderSummary(Order order) {
        String orderInfo
                = String.format("#%s : %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getTax().getStateAbbreviation(),
                order.getTax().getTaxRate(),
                order.getProduct().getProductType(),
                order.getArea(),
                order.getProduct().getCostPerSquareFoot(),
                order.getProduct().getLaborCostPerSquareFoot(),
                order.getMaterialCost(),
                order.getLaborCost(),
                order.getTotalTax(),
                order.getTotal());
        io.print(orderInfo);
    }

    public boolean promptDoesUserWantToContinue(String action) {
        String answer;
        do {
            answer = io.readString(String.format("Continue with %s? (y/n)", action));
        } while (!"y".equalsIgnoreCase(answer) && !"n".equalsIgnoreCase(answer));

        return "y".equalsIgnoreCase(answer);

    }

    public void printPreviousValue(String value) {
        io.print("Previous value: " + value);
    }

    public String getInput(String s) {
        return io.readString(s);
    }

    public void displayExportBanner() {
        io.print("=====|| EXPORTING ALL ORDERS TO BACKUP ||=====");
    }

    public void displaySuccessBanner() {
        io.print("=====|| OPERATION SUCCESSFUL ||=====");
        io.readString("Please hit enter to continue.");
    }
}
