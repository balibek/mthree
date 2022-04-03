/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.balibek.vendingmachine.service;

import com.apple.eawt.Application;
import com.balibek.vendingmachine.dao.*;
import com.balibek.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.balibek.vendingmachine.dto.Item;
import com.balibek.vendingmachine.dto.Money;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer testServ;

    public VendingMachineServiceLayerImplTest() {
//        VendingMachineDao dao = new VendingMachineDaoStubImpl();
//        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
//
//        testServ = new VendingMachineServiceLayerImpl(dao, auditDao);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testServ = ctx.getBean("serviceLayer", VendingMachineServiceLayerImpl.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllItems() throws Exception {
        Item testItem = new Item("5");
        testItem.setTitle("Candy");
        testItem.setCountOfItem(7);
        testItem.setCost("2.1");

        assertEquals("Should only one item.", 1, testServ.getAllItems().size());
        assertTrue("The one item should be Pepsi", testServ.getAllItems().contains(testItem));
    }

    @Test
    public void testValidBalance() {
        Money cbalance = new Money();
        cbalance.setAmoutOfMoney(new BigDecimal(3));

        try {
            testServ.addMoney(cbalance.getAmoutOfMoney());
        } catch (VendingMachineDataValidationException | VendingMachinePersistenceException ex) {
            fail("This was the Valid TEST!!!");
        }
    }

    @Test
    public void testGetItem() throws Exception {
        Item testItem = new Item("5");
        testItem.setTitle("Candy");
        testItem.setCountOfItem(7);
        testItem.setCost("2.1");

        Item candy = testServ.getItem("5");
        assertNotNull(candy);
        assertEquals(testItem, candy);

        Item notInDao = testServ.getItem("33");
        assertNull(notInDao);
    }

    @Test
    public void testGetBalance() {
        Money cbalance = new Money();
        cbalance.setAmoutOfMoney(new BigDecimal(3));

        try {
            testServ.getBalance();
        } catch (VendingMachinePersistenceException ex) {
            fail("This was the Valid TEST!!!");
        }
    }

    @Test
    public void testCheckandDecreaseBalance() throws Exception {
        Item testItem = new Item("5");
        testItem.setTitle("Candy");
        testItem.setCountOfItem(7);
        testItem.setCost("2.1");
        
        Item testItem2 = new Item("6");
        testItem2.setTitle("Nuts");
        testItem2.setCountOfItem(2);
        testItem2.setCost("4.1");

        Money cbalance = new Money();
        cbalance.setAmoutOfMoney(new BigDecimal(3));
        
        BigDecimal cost = new BigDecimal(testItem.getCost());
        BigDecimal balanceAfterBuying = cbalance.getAmoutOfMoney().subtract(cost);
        
        Exception exception = assertThrows(InsufficientFundsException.class, () -> { 
            testServ.checkBalance(testItem2);
        });
        
        String expectedMessage = "ERROR: Not enough money to buy. Please add money.";
        String actualMessage = exception.getMessage();
        
        assertTrue("Balance enough to buy stuff", testServ.checkBalance(testItem));
        assertTrue(actualMessage.contains(expectedMessage));
        assertNotNull("Balance 0", testServ.decreaseBalance(testItem));
        assertTrue("Balance decrease", testServ.getBalance().compareTo(balanceAfterBuying)==0);
    }
    
    @Test
    public void testCheckandDecreaseCountOfItem() throws Exception {
        Item testItem = new Item("5");
        testItem.setTitle("Candy");
        testItem.setCountOfItem(0);
        testItem.setCost("2.1");
        
        Item testItem2 = new Item("6");
        testItem2.setTitle("Nuts");
        testItem2.setCountOfItem(4);
        testItem2.setCost("4.1");
        
        Exception exception = assertThrows(NoItemInventoryException.class, () -> { 
            testServ.checkCountOfItem(testItem);
        });
        
        String expectedMessage = "ERROR: Not enough item to buy.";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue("Count of item decrease", testServ.decreaseItemCount(testItem2));
        
    }

}
