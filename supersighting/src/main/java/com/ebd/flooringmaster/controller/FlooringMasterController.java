package com.ebd.flooringmaster.controller;

import com.ebd.flooringmaster.dao.FlooringMasteryPersistenceException;
import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import com.ebd.flooringmaster.service.FlooringMasterServiceLayer;
import com.ebd.flooringmaster.service.NoSuchOrderException;
import com.ebd.flooringmaster.ui.FlooringMasterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasterController {

    private final FlooringMasterView view;
    private final FlooringMasterServiceLayer service;

    @Autowired
    public FlooringMasterController(FlooringMasterView view, FlooringMasterServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {

            while (keepGoing) {

                menuSelection = view.printMenuAndGetSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void unknownCommand() {
        view.displayUnknownBanner();
    }

    private void displayOrders() throws FlooringMasteryPersistenceException {
        var orderDate = view.getOrderDateChoice();

        try {
            var orders = service.getOrdersOfDate(orderDate);
            view.printOrdersList(orders);
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws FlooringMasteryPersistenceException {
        view.displayCreateOrderBanner();

        LocalDate selectDate;
        String customerName = "";
        Tax selectTax = null;
        Product selectProduct = null;
        BigDecimal area = new BigDecimal(0);
        do {
            selectDate = view.getOrderDateChoice();

            if (!service.isNewOrderDateValid(selectDate)) {
                view.displayOrderDateError();
            }

        } while (!service.isNewOrderDateValid(selectDate));
        //name validation loop
        do {
            customerName = view.getCustomerName();
            if (!service.isCustomerNameValid(customerName)) {
                view.displayInvalidNameError();
            }
        } while (!service.isCustomerNameValid(customerName));
        //tax list prompt
        List<Tax> tax = service.getTaxes();

        //tax validation loop
        do {
            view.printTaxList(tax);
            String stateAbr = view.getStateAbr();
            selectTax = service.getTax(stateAbr);
            if (selectTax == null) {
                view.displayInvalidTaxError();
            }
        } while (selectTax == null);
        //product prompt
        List<Product> products = service.getProducts();
        do {
            view.printProductList(products);
            String productType = view.getProductType();
            selectProduct = service.getProduct(productType);
            if (selectProduct == null) {
                view.displayInvalidProductError();
            }
        } while (selectProduct == null);
        do {
            try {
                area = view.getArea();
                if (!service.isAreaValid(area)) {
                    view.displayInvalidAreaError();
                }
            } catch (NumberFormatException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!service.isAreaValid(area));

        Order newOrder = new Order(service.generateNextOrderNum(selectDate), customerName, area, selectTax, selectProduct);
        view.displayOrderSummary(newOrder);

        if (view.promptDoesUserWantToContinue("Add")) {
            service.addOrder(selectDate, newOrder);
        }
    }

    private void editOrder() throws FlooringMasteryPersistenceException {

        LocalDate orderDate = view.getOrderDateChoice();
        int orderNum = view.getOrderNumChoice();

        try {
            Order orderToEdit = service.getOrder(orderDate, orderNum);

            // new name
            String newCustomerName;
            do {
                view.printPreviousValue(orderToEdit.getCustomerName());
                newCustomerName = view.getCustomerName();
                if (newCustomerName.isEmpty()) {
                    newCustomerName = orderToEdit.getCustomerName();
                }

                if (!service.isCustomerNameValid(newCustomerName)) {
                    view.displayInvalidNameError();
                }
            } while (!service.isCustomerNameValid(newCustomerName));

            // new state
            Tax newTax;
            do {
                view.printPreviousValue(orderToEdit.getTax().getStateAbbreviation());
                view.printTaxList(service.getTaxes());
                String stateAbbreviation = view.getStateAbr();

                newTax = stateAbbreviation.isEmpty()
                        ? orderToEdit.getTax() : service.getTax(stateAbbreviation);

                if (newTax == null) {
                    view.displayInvalidTaxError();
                }
            } while (newTax == null);

            // new product type
            Product newProduct;
            do {
                view.printPreviousValue(orderToEdit.getProduct().getProductType());
                view.printProductList(service.getProducts());
                String productType = view.getProductType();

                newProduct = productType.isEmpty()
                        ? orderToEdit.getProduct() : service.getProduct(productType);

                if (newProduct == null) {
                    view.displayInvalidProductError();
                }
            } while (newProduct == null);

            // new area
            BigDecimal newArea = null;
            boolean isAreaValid = false;
            do {
                view.printPreviousValue(orderToEdit.getArea().toString());
                String areaString = view.getInput("Enter new area: ");

                try {
                    newArea = areaString.isEmpty()
                            ? orderToEdit.getArea() : new BigDecimal(areaString);

                    isAreaValid = service.isAreaValid(newArea);

                    if (!isAreaValid) {
                        view.displayInvalidAreaError();
                    }
                } catch (NumberFormatException e) {
                    view.displayErrorMessage(e.getMessage());
                }
            } while (!isAreaValid);

            orderToEdit.setCustomerName(newCustomerName);
            orderToEdit.setTax(newTax);
            orderToEdit.setProduct(newProduct);
            orderToEdit.setArea(newArea);

            view.displayOrderSummary(orderToEdit);

            if (view.promptDoesUserWantToContinue("Edit")) {
                service.editOrder(orderDate, orderToEdit);
            }
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() throws FlooringMasteryPersistenceException {
        LocalDate orderDate = view.getOrderDateChoice();
        int orderNum = view.getOrderNumChoice();

        try {
            Order orderToRemove = service.getOrder(orderDate, orderNum);

            view.displayOrderSummary(orderToRemove);

            if (view.promptDoesUserWantToContinue("Remove")) {
                service.removeOrder(orderDate, orderToRemove.getOrderNumber());
            }
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exportData() throws FlooringMasteryPersistenceException {
        view.displayExportBanner();
        service.exportData();
        view.displaySuccessBanner();
    }

    private void exitMessage() {
        view.displayGoodByeBanner();
    }

}
