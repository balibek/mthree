package com.ebd.flooringmaster.dao;

import com.ebd.flooringmaster.dto.Order;
import com.ebd.flooringmaster.dto.Product;
import com.ebd.flooringmaster.dto.Tax;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FlooringMasterDaoFileImpl implements FlooringMasterDao {

    private static final String DELIMITER = ";";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");
    private final String[] ORDER_HEADERS = { "OrderNumber","CustomerName","State","TaxRate","ProductType","Area","CostPerSquareFoot","LaborCostPerSquareFoot","MaterialCost","LaborCost","Tax","Total" };
    private final String TAX_FILE;
    private final String PRODUCT_FILE;
    private final String ORDERS_DIR;
    private final String BACKUP_FILE;

    public FlooringMasterDaoFileImpl() {
        this.TAX_FILE = "Data/Taxes.txt";
        this.PRODUCT_FILE = "Data/Products.txt";
        this.ORDERS_DIR = "Orders/";
        this.BACKUP_FILE = "Backup/DataExport.txt";
    }

    public FlooringMasterDaoFileImpl(String TAX_FILE, String PRODUCT_FILE, String ORDERS_DIR, String backup_file) {
        this.TAX_FILE = TAX_FILE;
        this.PRODUCT_FILE = PRODUCT_FILE;
        this.ORDERS_DIR = ORDERS_DIR;
        this.BACKUP_FILE = backup_file;
    }

    /**
     * @param orderDate
     * @param orderNum
     * @return the order or NULL if there is no order
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException {
        return getOrderMap(orderDate).get(orderNum);
    }

    /**
     * @param orderDate
     * @return list of orders or empty list if no order file exists for date
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public List<Order> getOrdersOfDate(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        return new ArrayList<>(getOrderMap(orderDate).values());
    }

    private Map<Integer, Order> getOrderMap(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        Map<Integer, Order> orderMap;
        Path orderFilePath = getOrderFilePath(orderDate);

        if (Files.notExists(orderFilePath)) {
            return new TreeMap<>();
        }

        var products = getProductMap();
        var taxes = getTaxMap();
        try (var lines = Files.lines(orderFilePath)) {
            orderMap = lines
                    .skip(1)
                    .map(line -> stringToOrder(line, products, taxes))
                    .collect(Collectors.toMap(
                            Order::getOrderNumber, // key
                            Function.identity(), // value (identity just returns what's passed in)
                            (k1, k2) -> k1, // if two keys are equal (should never be the case)
                            TreeMap::new)); // type of map (we want to have consistent order when writing to file)
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not read order file.");
        }

        return orderMap;
    }

    /**
     * Adds order, overwrites file.
     *
     * @param orderDate
     * @param order to add
     * @return order that was added
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public Order addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        try {
            Map<Integer, Order> orderMap = getOrderMap(orderDate);
            Order shouldBeNull = orderMap.put(order.getOrderNumber(), order);

            // if not null it means there was an order of the same number
            if (shouldBeNull != null) {
                return null;
            }

            Path orderFilePath = getOrderFilePath(orderDate);
            writeOrderFile(orderFilePath, orderMap);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not write to order file.");
        }

        return order;
    }

    /**
     * @param orderDate
     * @param updatedOrder
     * @return the old order or NULL if order DNE
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public Order editOrder(LocalDate orderDate, Order updatedOrder) throws FlooringMasteryPersistenceException {
        var orderMap = getOrderMap(orderDate);
        Order oldOrder = orderMap.replace(updatedOrder.getOrderNumber(), updatedOrder);

        if (oldOrder == null) {
            return null;
        }

        try {
            Path orderFilePath = getOrderFilePath(orderDate);
            writeOrderFile(orderFilePath, orderMap);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Cannot edit order.");
        }

        return oldOrder;
    }

    /**
     * Removes the order, and if there are no orders left, deletes the file.
     *
     * @param orderDate
     * @param orderNum
     * @return order that was removed or null if order doesn't exist
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public Order removeOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistenceException {
        var orderMap = getOrderMap(orderDate);
        Order removedOrder = orderMap.remove(orderNum);

        if (removedOrder == null) {
            return null;
        }

        try {
            Path orderFilePath = getOrderFilePath(orderDate);

            if (orderMap.isEmpty()) {
                Files.delete(orderFilePath);
            } else {
                writeOrderFile(orderFilePath, orderMap);
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not remove order.");
        }

        return removedOrder;
    }

    @Override
    public List<Tax> getTaxes() throws FlooringMasteryPersistenceException {
        return new ArrayList<>(getTaxMap().values());
    }

    @Override
    public void exportData() throws FlooringMasteryPersistenceException {
        try {
            final Path backUpFilePath = Path.of(BACKUP_FILE);
            final var backUpDateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            // reset backup file and add extra date field
            writeOrderFile(backUpFilePath, Collections.emptyMap());
            Files.writeString(backUpFilePath, DELIMITER + "OrderDate", StandardOpenOption.APPEND);

            // list of all order file name paths, ex) "Orders/Orders_06012013.txt"
            var orderFilePaths = Files.list(Path.of(ORDERS_DIR)).toList();

            // for each order file in Orders
            for (var orderFilePath : orderFilePaths) {
                // first extract date from filename and format it to the backup date format
                String orderDateStr = orderFilePath
                        .getFileName()
                        .toString()
                        .replaceFirst("Orders_", "")
                        .replaceFirst(".txt", "");
                LocalDate orderDate = LocalDate.parse(orderDateStr, DATE_FORMAT);
                String backupOrderDateStr = orderDate.format(backUpDateFormat);

                var orderFileLines = Files.readAllLines(orderFilePath);
                orderFileLines = orderFileLines.subList(1, orderFileLines.size());

                for (var line : orderFileLines) {
                    String updatedLine = String.format("\n%s%s%s", line, DELIMITER, backupOrderDateStr);
                    Files.writeString(backUpFilePath, updatedLine, StandardOpenOption.APPEND);
                }
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not export data.");
        }
    }

    @Override
    public List<Product> getProducts() throws FlooringMasteryPersistenceException {
        return new ArrayList<>(getProductMap().values());
    }

    private void appendOrderToFile(Path orderFilePath, Order order) throws IOException {
        String orderString = orderToString(order);
        Files.writeString(orderFilePath, "\n" + orderString, StandardOpenOption.APPEND);
    }

    private String orderToString(Order order) {
        return String.join(DELIMITER,
                Integer.toString(order.getOrderNumber()),
                order.getCustomerName(),
                order.getTax().getStateAbbreviation(),
                order.getTax().getTaxRate().toString(),
                order.getProduct().getProductType(),
                order.getArea().toString(),
                order.getProduct().getCostPerSquareFoot().toString(),
                order.getProduct().getLaborCostPerSquareFoot().toString(),
                order.getMaterialCost().toString(),
                order.getLaborCost().toString(),
                order.getTotalTax().toString(),
                order.getTotal().toString()
        );
    }

    /**
     * Overwrites or creates order file if DNE with orders from map.values()
     *
     * @param orderFilePath
     * @param orderMap
     * @throws IOException
     */
    private void writeOrderFile(Path orderFilePath, Map<Integer, Order> orderMap) throws IOException {
        Files.writeString(orderFilePath, String.join(DELIMITER, ORDER_HEADERS));

        for (Order order : orderMap.values()) {
            appendOrderToFile(orderFilePath, order);
        }
    }

    private Path getOrderFilePath(LocalDate orderDate) {
        String orderDateString = orderDate.format(DATE_FORMAT);
        return Path.of(ORDERS_DIR + "Orders_" + orderDateString + ".txt");
    }

    private Order stringToOrder(String line, Map<String, Product> productMap, Map<String, Tax> taxMap) {
        String[] tokens = line.split(DELIMITER);

        int orderNum = Integer.parseInt(tokens[0]);
        String customerName = tokens[1];
        Tax tax = taxMap.get(tokens[2]);
        Product product = productMap.get(tokens[4]);
        BigDecimal area = new BigDecimal(tokens[5]);

        return new Order(orderNum, customerName, area, tax, product);
    }

    private Product stringToProduct(String line) {
        String[] tokens = line.split(DELIMITER);

        return new Product(
                tokens[0],
                new BigDecimal(tokens[1]),
                new BigDecimal(tokens[2])
        );
    }

    private Tax stringToTax(String line) {
        String[] tokens = line.split(DELIMITER);

        return new Tax(
                tokens[0],
                tokens[1],
                new BigDecimal(tokens[2])
        );
    }

    private Map<String, Product> getProductMap() throws FlooringMasteryPersistenceException {
        Map<String, Product> productsMap;
        try (var lines = Files.lines(Path.of(PRODUCT_FILE))) {
            productsMap = lines
                    .skip(1)
                    .map(this::stringToProduct)
                    .collect(Collectors.toMap(
                            Product::getProductType, // key
                            Function.identity() // value
                    ));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not load products.");
        }

        return productsMap;
    }

    private Map<String, Tax> getTaxMap() throws FlooringMasteryPersistenceException {
        Map<String, Tax> taxMap;
        try (var lines = Files.lines(Path.of(TAX_FILE))) {
            taxMap = lines
                    .skip(1)
                    .map(this::stringToTax)
                    .collect(Collectors.toMap(
                            Tax::getStateAbbreviation, // key
                            Function.identity() // value
                    ));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not load taxes.");
        }

        return taxMap;
    }

    private Product findProduct(String productType) throws FlooringMasteryPersistenceException {
        // should never fail
        Map<String, Product> productMap = getProductMap();
        return productMap.get(productType);
    }

    private Tax findTax(String stateAbbreviation) throws FlooringMasteryPersistenceException {
        // should never fail
        Map<String, Tax> taxMap = getTaxMap(); 
        return taxMap.get(stateAbbreviation);
    }

    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        return findProduct(productType);
    }

    @Override
    public Tax getTax(String stateAbbreviation) throws FlooringMasteryPersistenceException {
        return findTax(stateAbbreviation);
    }
}
