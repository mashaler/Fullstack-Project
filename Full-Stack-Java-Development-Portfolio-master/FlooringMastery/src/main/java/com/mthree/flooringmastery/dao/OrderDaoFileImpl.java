/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author josep
 */
public class OrderDaoFileImpl implements OrderDao{
    
    FlooringMasteryAudit auditDao;
    
    //file directories
    private final String orderFileDirectory;
    private final String backupFileDirectory;
    private static final String DELIMITER = "::";
    
    //next available order number
    private int currentOrderNumber;
    
    //persistence file names
    private final String CURRENT_ORDER_NUMBER_File;
    private final String BACKUP_File;
   
    //header of order files
    private static final String FILEHEADER = "OrderNumber::"
            + "CustomerName::State::TaxRate::ProductType::"
            + "Area::CostPerSquareFoot::LaborCostPerSquareFoot::"
            + "MaterialCost::LaborCost::Tax::Total";
    
    
    public OrderDaoFileImpl(FlooringMasteryAudit auditDao){
        
        this.auditDao = auditDao;
        BACKUP_File = "DataExport.txt";
        CURRENT_ORDER_NUMBER_File = "currentordernumber.txt";
        orderFileDirectory = "Orders/";
        backupFileDirectory = "Backup/";
    }
    
    public OrderDaoFileImpl(FlooringMasteryAudit auditDao, String testDirectory, String testOrderDircetory, String backUpFileName, String currentOrderNumber){
        
        this.auditDao = auditDao;
        BACKUP_File = backUpFileName;
        CURRENT_ORDER_NUMBER_File = currentOrderNumber;
        orderFileDirectory = testOrderDircetory;
        backupFileDirectory = testDirectory;
        
    }
    
    //map will hold all orders <number, order>
    private Map<Integer, Order> orders = new HashMap<>();
    
    /**
     * Get read in all orders for a given delivery date
     * @param deliveryDate
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public List<Order> getOrders(LocalDate deliveryDate) throws DataPersistenceException {
        
        loadOrders(deliveryDate);
        List<Order> ordersList = new ArrayList<Order>(orders.values());
        
        return ordersList;
    }

    /**
     * Add next available order number to order, and write order
     * @param orderToAdd
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Order addOrder(Order orderToAdd) throws DataPersistenceException {
        
        getCurrentOrderNumber();
        orderToAdd.setOrderNumber(currentOrderNumber);
        
        try{
            loadOrders(orderToAdd.getDeliveryDate());
        }catch(DataPersistenceException e){
            
        }
        
        orders.put(orderToAdd.getOrderNumber(), orderToAdd);
        
        writeOrders(orderToAdd.getDeliveryDate());
        
        //add 1 to the current availble order number (make it different for next order)
        incrementAndWriteCurrentOrderNumber();
        
        auditDao.writeAuditEntry("Order: " + orderToAdd.getOrderNumber() +" ADDED.");
        
        return orderToAdd;
    }

    /**
     * Takes an order, and it's delivery date (to locate the file) and removes it
     * @param orderToRemove
     * @param orderDeliveryDate
     * @return
     * @throws DataPersistenceException 
     */   
    @Override
    public Order removeOrder(Order orderToRemove, LocalDate orderDeliveryDate) throws DataPersistenceException {
        
        loadOrders(orderDeliveryDate);
        
        orders.remove(orderToRemove.getOrderNumber());
        
        writeOrders(orderDeliveryDate);
        
        auditDao.writeAuditEntry("Order: " + orderToRemove.getOrderNumber() +" REMOVED.");
        return orderToRemove;
    }

    /**
     * Takes edited order and replaces it in the text file.
     * The delivery date is also passed in as a parameter to locate the correct file
     * @param orderToEdit
     * @param orderDeliverDate
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Order editOrder(Order orderToEdit, LocalDate orderDeliverDate) throws DataPersistenceException {
        loadOrders(orderDeliverDate);
        
        orders.replace(orderToEdit.getOrderNumber(), orderToEdit);
        
        writeOrders(orderDeliverDate);
        
        auditDao.writeAuditEntry("Order: " + orderToEdit.getOrderNumber() +" EDITED.");
        
        return orderToEdit;
    }
    
    
    
    /**
     * unmarshalls order
     * @param orderAsText
     * @return 
     */
    private Order unmarshallOrder(String orderAsText){

        // ______________________________________
        // |         |             |     |       |            |  
        // |ORDER NUM|CUSTOMER NAME|STATE|TAXRATE|PRODUCT TPYE| AREA| COST PER SQR FOOT | LABOUR COST PER SQE FOOT | MATERIAL COST | LABOUR COST | TAX| TOTAL
        // |         |             |     |       |            |  
        // --------------------------------------
        //  [0]         [1]          [2]    [3]       [4]       [5]             [6]                     [7]                  [8]         [9]       [10]   [11]      
        String[] orderTokens = orderAsText.split(DELIMITER);

        // Given the pattern above, the student Id is in index 0 of the array.
        String orderNumber = orderTokens[0];

        Order orderFromFile = new Order(Integer.parseInt(orderNumber));

        //set order name
        orderFromFile.setCustomerName(orderTokens[1]);

        //set state
        orderFromFile.setState(orderTokens[2]);

        // set taxrate
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        
        //set prodcut type
        orderFromFile.setProductType(orderTokens[4]);
        
        //area
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        
        //material cost per sq foot
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        
        //labour cost per sq foot
        orderFromFile.setLabourCostPerSqaureFoot(new BigDecimal(orderTokens[7]));
        
        //material cost
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
       
        //labour cost
        orderFromFile.setLabourCost(new BigDecimal(orderTokens[9]));
        
        //tax
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));

        //total
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));
        
        return orderFromFile;
    }
    
    /**
     * loads orders map with all the orders of an associated delivery day
     * @param dateToLoad
     * @throws DataPersistenceException 
     */
    private void loadOrders(LocalDate dateToLoad) throws DataPersistenceException {
        
        orders.clear();
        
        Scanner scanner;
        DateTimeFormatter dateFileFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String userDateFileFormatter = dateToLoad.format(dateFileFormatter);
        
        File userRequestedFile = new File(orderFileDirectory+"Orders_"+userDateFileFormatter+".txt");
                
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(userRequestedFile)));
            
        } catch (FileNotFoundException e){
            throw new DataPersistenceException( "No orders exits on that date. Please select anotner, or hit return to go back to main menu.", e);
        }
        
        // currentLine holds the most recent line read from the file
        String currentLine;
        Order currentOrder;
        scanner.nextLine();
        
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a product
            currentOrder = unmarshallOrder(currentLine);

            // We are going to use the product id as the map key for our product object.
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        // close scanner
        scanner.close();
    }
    
    /**
     * marshall orders
     * @param anOrder
     * @return 
     */
    private String marshallOrder(Order anOrder){

        // numer
        String orderAsText = anOrder.getOrderNumber()+ DELIMITER;

        // name
        orderAsText += anOrder.getCustomerName()+ DELIMITER;

        // state
        orderAsText += anOrder.getState()+ DELIMITER;

        // Tax rate
        orderAsText += anOrder.getTaxRate()+ DELIMITER;
        
        // product typee
        orderAsText += anOrder.getProductType()+ DELIMITER;

        //area
        orderAsText += anOrder.getArea()+ DELIMITER;
        
        // cost per sqr foot
        orderAsText += anOrder.getCostPerSquareFoot()+ DELIMITER;

        // labour cost per sqr foot
        orderAsText += anOrder.getLabourCostPerSqaureFoot()+ DELIMITER;
        
        // material cost
        orderAsText += anOrder.getMaterialCost()+ DELIMITER;

        // labour cost
        orderAsText += anOrder.getLabourCost()+ DELIMITER;
        
        // tax
        orderAsText += anOrder.getTax()+ DELIMITER;
        
        // total
        orderAsText += anOrder.getTotal();

        return orderAsText;
    }
    
    
    
    /**
     * write out orders to a different txt file depending on it delivery date
     * @param deliveryDate
     * @throws DataPersistenceException 
     */
    private void writeOrders(LocalDate deliveryDate) throws  DataPersistenceException{
        
        PrintWriter out;

        
        DateTimeFormatter dateFileFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String userDateFileFormatter = deliveryDate.format(dateFileFormatter);
        
        String fileName = orderFileDirectory+"Orders_"+userDateFileFormatter+".txt";
        File deliveyDateFile = new File(fileName);
        
        try {
            out = new PrintWriter(
                    new FileWriter(deliveyDateFile));
        } catch (IOException e) {
            throw new DataPersistenceException(
                    "Could not save data.", e);
        }

        String orderAsText;
        out.println(FILEHEADER);
        List<Order> orderList = new ArrayList(orders.values());
        for (Order currentOrder : orderList) {
            // turn a product into a String
            orderAsText = marshallOrder(currentOrder);
            // write the produce object to the file
            out.println(orderAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }
    
    /**
     * read in the next available order number 
     * @throws DataPersistenceException 
     */
    private void getCurrentOrderNumber() throws DataPersistenceException{
        Scanner scanner;
        
        try{
            scanner = new Scanner(
            new BufferedReader(
            new FileReader(CURRENT_ORDER_NUMBER_File)));
        }catch (FileNotFoundException e){
            throw new DataPersistenceException("Could Not Load Next Availabe Order Number");
        }
        
        int numberReadIn = Integer.parseInt(scanner.next());
        
        this.currentOrderNumber = numberReadIn;
        
        scanner.close();
    }
    
    /**
     * increment the next available order number and write it out
     * @throws DataPersistenceException 
     */
    private void incrementAndWriteCurrentOrderNumber() throws DataPersistenceException{
        PrintWriter out;
        
        try{
            out = new PrintWriter(
            new FileWriter(CURRENT_ORDER_NUMBER_File));
        }catch(IOException e){
            throw new DataPersistenceException("Could not save new order number.", e);
        }
        
        currentOrderNumber += 1;
        
        out.println(currentOrderNumber);
        out.flush();
        out.close();
    }

    /**
     * write out all order (of all dates) to an backup txt file
     * @throws DataPersistenceException 
     */
    @Override
    public void exportAllData() throws DataPersistenceException {
        
        File dir = new File(orderFileDirectory);
        
        File [] dirtoryListing = dir.listFiles();
        String currentDate = "";
        
        PrintWriter out;

        String fileName = backupFileDirectory+BACKUP_File;

        File backupFile = new File(fileName);

        try {

            out = new PrintWriter(
                    new FileWriter(backupFile));
        } catch (IOException e) {
            throw new DataPersistenceException(
                    "Could not save data.", e);
        }
        
        String orderAsText;
        out.println(FILEHEADER);
            
        if(dirtoryListing!=null){
            for (File orderFile : dirtoryListing){
                
                String currentFileName = orderFile.getName();
                String stringDateFromFileName = currentFileName.substring(7, 15);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                LocalDate dateFromFileName = LocalDate.parse(stringDateFromFileName,formatter);
                
                loadOrders(dateFromFileName);
                
                List<Order> orderList = new ArrayList(orders.values());
                for (Order currentOrder : orderList) {
                // turn a product into a String
                orderAsText = marshallOrder(currentOrder);
                // write the produce object to the file
                out.println(orderAsText+"::"+stringDateFromFileName);
                // force PrintWriter to write line to the file
                out.flush();
            }
                
            }
            
            // Clean up
            out.close();
             
            auditDao.writeAuditEntry("ORDERS BACKED-UP");

        }
    }
    
}
