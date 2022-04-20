package com.ebd.flooringmaster.dao;

import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasterDaoFileImplTest {

    private final DateTimeFormatter ORDER_DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");
    private final String[] ORDER_HEADERS = { "OrderNumber","CustomerName","State","TaxRate","ProductType","Area","CostPerSquareFoot","LaborCostPerSquareFoot","MaterialCost","LaborCost","Tax","Total" };
    private static final String DELIMITER = ";";
    private final String testingDirectory = "TestingDirectory/";
    private final String testTaxFile = testingDirectory + "Data/Taxes.txt";
    private final String testProductFile = testingDirectory + "Data/Products.txt";
    private final String testOrdersDir = testingDirectory + "Orders/";
    private final String BACKUP_DIR = testingDirectory + "Backup/";
    private final String testBackUpFile = BACKUP_DIR + "DataExport.txt";
    private FlooringMasterDao dao;

    public FlooringMasterDaoFileImplTest() {
    }

    @BeforeEach
    void setUp() throws Exception {

        Files.createDirectories(Path.of(testOrdersDir));
        Files.createDirectories(Path.of(BACKUP_DIR));

        // delete backup file
        Files.deleteIfExists(Path.of(testBackUpFile));

        // delete all orders
        var orderFiles = Files.list(Path.of(testOrdersDir)).toList();
        for (var file : orderFiles) {
            Files.deleteIfExists(file);
        }

        this.dao = new FlooringMasterDaoFileImpl(testTaxFile, testProductFile, testOrdersDir, testBackUpFile);
    }

    @AfterEach
    void tearDown() throws Exception {
        // delete backup file
        Files.deleteIfExists(Path.of(testBackUpFile));

        // delete all orders
        var orderFiles = Files.list(Path.of(testOrdersDir)).toList();
        for (var file : orderFiles) {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testGetOrder() throws Exception {
        // Setup
        LocalDate orderDate = LocalDate.of(2013, 6, 1);
        Order orderToBeAdded = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );

        // Try getting an order with zero files.
        assertNull(dao.getOrder(LocalDate.of(2100, 1, 1), 999),
                "Testing get order with no files.");

        // Try adding and retrieving the same order
        dao.addOrder(orderDate, orderToBeAdded);
        Order retrievedOrder = dao.getOrder(orderDate, 1);
        assertEquals(orderToBeAdded, retrievedOrder, "Testing get order of the same order");

        // Test existing file but invalid order number
        assertNull(dao.getOrder(orderDate, 999), "Testing invalid order num");
    }

    @Test
    public void testGetOrdersOfDate() throws Exception {
        // Setup
        LocalDate orderDate = LocalDate.of(2013, 6, 1);
        Order orderToBeAdded = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );
        Order secondOrderToBeAdded = new Order(
                2,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );

        // Try getting orders with no matching date.
        assertEquals(Collections.emptyList(), dao.getOrdersOfDate(orderDate),
                "Testing get orders of non existent date.");

        // Create and add a new order file with two orders and retrieve all orders from it
        dao.addOrder(orderDate, orderToBeAdded);
        dao.addOrder(orderDate, secondOrderToBeAdded);
        var ordersFilled = dao.getOrdersOfDate(orderDate);
        assertEquals(List.of(orderToBeAdded, secondOrderToBeAdded), ordersFilled,
                "Testing get orders of a filled order file.");
    }

    @Test
    public void testAddOrder() throws Exception {
        // Setup
        LocalDate orderDate = LocalDate.of(2013, 6, 1);
        Order orderToBeAdded = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );
        Order secondOrderToBeAdded = new Order(
                2,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );

        // Add one order and check if it was added...
        dao.addOrder(orderDate, orderToBeAdded);
        Order retrieved = dao.getOrder(orderDate, 1);
        assertEquals(orderToBeAdded, retrieved, "Checking add order via get order");

        // ...check orders list...
        var retrievedOrders = dao.getOrdersOfDate(orderDate);
        assertEquals(List.of(orderToBeAdded), retrievedOrders, "Checking add order via list");

        // ...check that the file was created with the correct date format...
        var orderFilePath = Path.of(
                testOrdersDir + "Orders_" + orderDate.format(ORDER_DATE_FORMAT) + ".txt"
        );
        assertTrue(Files.exists(orderFilePath), "Checking to see if file exists after add order.");

        // ...check that the order header is created
        assertEquals(String.join(DELIMITER, ORDER_HEADERS), Files.readAllLines(orderFilePath).get(0), "Checking order header got created correctly.");

        // ...check that only one entry was added.
        assertEquals(2, Files.readAllLines(orderFilePath).size(), "Checking length of file after 1st add.");

        // Add second order to the same file and check if it was added...
        dao.addOrder(orderDate, secondOrderToBeAdded);
        var secondRetrieved = dao.getOrder(orderDate, 2);
        assertEquals(secondOrderToBeAdded, secondRetrieved, "Checking add order via get order");

        // ...check that both orders are added
        var secondRetrievedOrders = dao.getOrdersOfDate(orderDate);
        assertEquals(List.of(orderToBeAdded, secondOrderToBeAdded), secondRetrievedOrders, "Checking add order via list");

        // ...check to see that no new files were created after second add
        assertEquals(1, Files.list(Path.of(testOrdersDir)).count(), "Testing no new files created after add order.");

        // ...check length of file is correct
        assertEquals(3, Files.readAllLines(orderFilePath).size(), "Checking length of file after 2nd add.");
    }

    @Test
    public void testEditOrder() throws Exception {
        // Setup
        var orderDate = LocalDate.of(2013, 6, 1);
        var orderFilePath = Path.of(
                testOrdersDir + "Orders_" + orderDate.format(ORDER_DATE_FORMAT) + ".txt"
        );
        Order orderToBeAdded = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );
        dao.addOrder(orderDate, orderToBeAdded);

        var orderToBeEdited = dao.getOrder(orderDate, orderToBeAdded.getOrderNumber());

        orderToBeEdited.setArea(new BigDecimal(900));
        orderToBeEdited.setCustomerName("EDITED");
        orderToBeEdited.setTax(
                new Tax("WA", "Washington", new BigDecimal("9.25"))

                );
        orderToBeEdited.setProduct(
                new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"))
        );

        // Edit order
        dao.editOrder(orderDate, orderToBeEdited);
        var editedOrder = dao.getOrder(orderDate, orderToBeEdited.getOrderNumber());

        // Check that the order numbers are the same
        assertEquals(orderToBeAdded.getOrderNumber(), editedOrder.getOrderNumber(),
                "Making sure order numbers are the same after editing.");
        assertNotEquals(orderToBeAdded, editedOrder,
                "Making sure edited and previous order are different");

        // Check same number of orders still remain
        assertEquals(1, dao.getOrdersOfDate(orderDate).size());
    }

    @Test
    public void testRemoveOrder() throws Exception {
        // Setup
        var orderDate = LocalDate.of(2013, 6, 1);
        var orderFilePath = Path.of(
                testOrdersDir + "Orders_" + orderDate.format(ORDER_DATE_FORMAT) + ".txt"
        );
        Order orderToBeAdded = new Order(
                1,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );
        Order secondOrderToBeAdded = new Order(
                2,
                "Ada Lovelace",
                new BigDecimal("249.00"),
                new Tax("CA", "Calfornia", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"))
        );
        dao.addOrder(orderDate, orderToBeAdded);
        dao.addOrder(orderDate, secondOrderToBeAdded);
        // Pre check
        assertEquals(
                List.of(orderToBeAdded, secondOrderToBeAdded),
                dao.getOrdersOfDate(orderDate),
                "Making sure added orders were added.");

        // Remove one order
        Order removedOrder = dao.removeOrder(orderDate, orderToBeAdded.getOrderNumber());
        // ...check it got removed
        assertEquals(orderToBeAdded, removedOrder, "Testing removing a singular order.");
        // ...check the list after removal
        assertEquals(
                List.of(secondOrderToBeAdded),
                dao.getOrdersOfDate(orderDate),
                "Second order should be removed.");

        // Remove the last order in the file
        Order secondRemovedOrder = dao.removeOrder(orderDate, secondOrderToBeAdded.getOrderNumber());
        assertEquals(secondOrderToBeAdded, secondRemovedOrder, "Testing removing another order");

        // File should be deleted now
        assertTrue(Files.notExists(orderFilePath), "Making sure order size of zero is deleted.");
    }

    @Test
    public void testGetProducts() throws Exception {
        var numProducts = dao.getProducts().size();

        var numProductEntriesInFile = Files.lines(Path.of(testTaxFile)).skip(1).count();

        assertEquals(numProductEntriesInFile, numProducts, "Testing if products exists and has correct number of entries");
    }

    @Test
    public void testGetTaxes() throws Exception {
        var numTaxes = dao.getTaxes().size();

        var numTaxEntriesInFile = Files.lines(Path.of(testTaxFile)).skip(1).count();

        assertEquals(numTaxEntriesInFile, numTaxes, "Testing if taxes exists and has correct number of entries");

    }

    @Test
    public void testExportData() throws Exception {
        DateTimeFormatter BACKUP_DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String backupHeader = String.join(DELIMITER, ORDER_HEADERS) + DELIMITER + "OrderDate";

        // export
        dao.exportData();

        // check backup header
        var lines = Files.readAllLines(Path.of(testBackUpFile));
        assertEquals(backupHeader, lines.get(0), "Testing backup header is correct");

        // check date is correct format
        for (var line : lines.subList(1, lines.size())) {
            var tokens = line.split(DELIMITER);
            var date = tokens[tokens.length - 1];

            assertDoesNotThrow(() -> LocalDate.parse(date, BACKUP_DATE_FORMAT));
        }
    }


}