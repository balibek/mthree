package com.ebd.flooringmaster.service;

import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasterServiceLayerImplTest {
    
    private FlooringMasterServiceLayer testServ;

    public FlooringMasterServiceLayerImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testServ = ctx.getBean("serviceLayer", FlooringMasterServiceLayer.class);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    
    @Test
    public void testGetOrdersOfDate() throws Exception {
        //Order tesTOrder = new Order(45,"John", new BigDecimal(300), testServ.getTax("Tile"), testServ.getProduct("CA"));
        LocalDate orderDate = LocalDate.of(2013, 6, 2);
        Order testOrder = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );
        
        assertThrows(NoSuchOrderException.class, () -> testServ.getOrdersOfDate(orderDate), "Testing get orders of non existent date.");
        
        testServ.addOrder(orderDate, testOrder);
        
    }

    @Test
    void testGenerateNextOrderNum() throws Exception {
        // non existing file
        LocalDate nonExistentOrderDate = LocalDate.of(2999, 6, 2);
        int orderNumForNoOrders = testServ.generateNextOrderNum(nonExistentOrderDate);
        assertEquals(1, orderNumForNoOrders, "Testing order num for no orders.");

        // already existing file
        LocalDate existingOrderDate = LocalDate.of(2013, 6, 1);
        int orderNumExistingOrders = testServ.generateNextOrderNum(existingOrderDate);
        assertEquals(2, orderNumExistingOrders, "Testing order num for one existing order");
    }



    @Test
    void testInputValidation() {
        BigDecimal areaLessThan100 = new BigDecimal("99.99");
        var validArea = new BigDecimal("100");

        var invalidCustomerName = "Acme; Inc,. | 123";
        var validCustomerName = "Acme, Inc. 123123";

        var pastOrderDate = LocalDate.of(2021, 1, 1);
        var futureOrderDate = LocalDate.of(2030, 1, 1);

        assertFalse(testServ.isAreaValid(areaLessThan100), "Testing validation of area less than 100");
        assertTrue(testServ.isAreaValid(validArea), "Testing validation of area = 100");

        assertFalse(testServ.isCustomerNameValid(invalidCustomerName), "Testing validation of invalid customer name");
        assertFalse(testServ.isCustomerNameValid(""), "Testing validation of blank customer name");
        assertFalse(testServ.isCustomerNameValid("      "), "Testing validation of blank customer name");
        assertTrue(testServ.isCustomerNameValid(validCustomerName), "Testing validation of valid customer name");

        assertFalse(testServ.isNewOrderDateValid(pastOrderDate), "Testing validation of past order date");
        assertTrue(testServ.isNewOrderDateValid(futureOrderDate), "Testing validation of future order date");

    }

    @Test
    void testOrderCalculations() throws Exception {
//        1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06

        BigDecimal taxRate = new BigDecimal(25);
        var area = new BigDecimal(249);
        var costPerSquareFoot = new BigDecimal("3.5");
        var laborCostPerSquareFoot = new BigDecimal("4.15");


        Order testOrder = new Order(
                1,
                "Ada Lovelace",
                area,
                new Tax("CA", "Calfornia", taxRate),
                new Product("Tile", costPerSquareFoot, laborCostPerSquareFoot)
        );


        //    MaterialCost = (Area * CostPerSquareFoot)
        //    LaborCost = (Area * LaborCostPerSquareFoot)
        //    Tax = (MaterialCost + LaborCost) * (TaxRate/100)
        //    Total = (MaterialCost + LaborCost + Tax)

        assertEquals(new BigDecimal("871.50"), testOrder.getMaterialCost(), "Testing material cost");
        assertEquals(new BigDecimal("1033.35"), testOrder.getLaborCost(), "Testing labor cost");
        assertEquals(new BigDecimal("476.21"), testOrder.getTotalTax(), "Testing tax");
        assertEquals(new BigDecimal("2381.06"), testOrder.getTotal(), "Testing total");
    }
}