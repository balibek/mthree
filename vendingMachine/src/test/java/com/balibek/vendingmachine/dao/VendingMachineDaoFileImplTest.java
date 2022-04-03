/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.balibek.vendingmachine.dao;

import com.balibek.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bali_bek
 */
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        String testFile = "testvendingmachine.txt";
        
        testDao = new VendingMachineDaoFileImpl(testFile);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCheckBalance() throws Exception {
        testDao.loadInventory();
        testDao.addMoney(new BigDecimal(3.4));
        Item item = testDao.getItem("4");
        Item item2 = testDao.getItem("3");
        
        assertTrue("You can buy this Item", testDao.checkBalance(item));
        assertFalse("You can't buy this item", testDao.checkBalance(item2));
    }
    
    @Test
    public void testChountOfItem() throws Exception {
        testDao.loadInventory();
        Item item = testDao.getItem("122");
        Item item2 = testDao.getItem("4");
        
        assertFalse("Item finished", testDao.checkCountOfItem(item));
        assertTrue("Item enough", testDao.checkCountOfItem(item2));
    }
    
    @Test
    public void testDecreaseItem() throws Exception {
        testDao.loadInventory();
        Item item = testDao.getItem("4");
        Item item2 = testDao.getItem("122");
        
        assertTrue("Count Of item decrease", testDao.checkCountOfItem(item));
        assertFalse("Error Item finished", testDao.checkCountOfItem(item2));
        
    }
    
    @Test
    public void testListOfItem() throws Exception {
        testDao.loadInventory();
        List<Item> ItemsList = testDao.getAllItems();
        
        assertNotNull("List no Null", ItemsList);
        assertTrue(ItemsList.contains(testDao.getItem("4")));
        assertFalse(ItemsList.contains(testDao.getItem("2")));
        assertEquals("There are 4 items in the list", 4, ItemsList.size());
    }
    
    @Test
    public void testDecreaseBalance() throws Exception {
        testDao.loadInventory();
        BigDecimal currentBalance = testDao.addMoney(new BigDecimal(3.4));
        Item item = testDao.getItem("4");
        BigDecimal cost = new BigDecimal(item.getCost());
        BigDecimal balanceAfterBuying = currentBalance.subtract(cost);
        
        Item item2 = testDao.getItem("3");
        BigDecimal cost2 = new BigDecimal(item2.getCost());
        BigDecimal balanceAfterBuying2 = currentBalance.subtract(cost2);
        
        assertNotNull("Balance 0", testDao.decreaseBalance(item));
        assertTrue("Balance decrease", testDao.getBalance().compareTo(balanceAfterBuying)==0);
        assertFalse("Balance can't decrease", testDao.getBalance().compareTo(balanceAfterBuying2)==-1);
    }
    
    @Test
    public void testReturnChange() throws Exception {
        testDao.loadInventory();
        testDao.addMoney(new BigDecimal(3.4));
        Item item = testDao.getItem("4");
        testDao.decreaseBalance(item);
        testDao.decreaseItemCount(item);
        
        assertNotNull("Change not null", testDao.returnChange());
       // assertEquals("You can buy this Item", testDao.returnChange());
    }
}
