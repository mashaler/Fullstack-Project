/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mthree.flooringmastery.dto.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author josep
 */
public class OrderDaoFileImplTest {
    
    public OrderDaoFileImplTest() {
    }
    
    OrderDao testOrderDao;
   
    File file;
    
    //create files that test orders will be save to
    //create a new test number file that resets to 1 for each test
    @BeforeEach
    public void setUp() throws Exception {
      
        new File("TestFiles/TestOrders/Orders_12122025.txt");
        Writer out1 = new FileWriter("TestFiles/TestOrders/Orders_12122025.txt");
        out1.write("\n");
        out1.flush();
        out1.close();
        
        new File("TestFiles/TestOrders/Orders_12132025.txt");
        Writer out2 = new FileWriter("TestFiles/TestOrders/Orders_12132025.txt");
        out2.write("\n");
        out2.flush();
        out2.close();

        new File("TestFiles/TestCurrentOrderNumber.txt");
        Writer out = new FileWriter("TestFiles/TestCurrentOrderNumber.txt");
        out.write("1");
        out.flush();
        out.close();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testOrderDao = ctx.getBean("testOrderDao", OrderDao.class);
        
    }
    
   

    @Test
    public void testAddGetter() throws Exception{
        
        //creat first test Order
        Order testOrder = new Order();
        
        LocalDate deliveryDate = LocalDate.of(2025, Month.DECEMBER, 12);
        testOrder.setDeliveryDate(deliveryDate);
        testOrder.setCustomerName("MIMMM");
        testOrder.setState("ASd");
        testOrder.setTaxRate(BigDecimal.TEN);
        testOrder.setProductType("MOMOM");
        testOrder.setArea(BigDecimal.ZERO);
        testOrder.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder.setMaterialCost(BigDecimal.ZERO);
        testOrder.setLabourCost(BigDecimal.ZERO);
        testOrder.setTax(BigDecimal.TEN);
        testOrder.setTotal(BigDecimal.ONE);
        
        //creat second test Order
        Order testOrder2 = new Order();
        
        LocalDate deliveryDate2 = LocalDate.of(2025, Month.DECEMBER, 12);
        testOrder2.setDeliveryDate(deliveryDate2);
        testOrder2.setCustomerName("MIasdasd");
        testOrder2.setState("AasdSd");
        testOrder2.setTaxRate(BigDecimal.ZERO);
        testOrder2.setProductType("MOMOM");
        testOrder2.setArea(BigDecimal.ZERO);
        testOrder2.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder2.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder2.setMaterialCost(BigDecimal.ZERO);
        testOrder2.setLabourCost(BigDecimal.ZERO);
        testOrder2.setTax(BigDecimal.TEN);
        testOrder2.setTotal(BigDecimal.ONE);
        
        //Add both test orders
        testOrderDao.addOrder(testOrder);
        testOrderDao.addOrder(testOrder2);
        
        //get a list of orders
        List<Order> allOrders =  testOrderDao.getOrders(deliveryDate);
        
        //get first test order back and check all fields are the same
        Order testOrderReceived = allOrders.get(0);
        assertEquals(testOrder.getArea(),testOrderReceived.getArea());
        assertEquals(testOrder.getCostPerSquareFoot(),testOrderReceived.getCostPerSquareFoot());
        assertEquals(testOrder.getCustomerName(),testOrderReceived.getCustomerName());
        assertEquals(testOrder.getTax(),testOrderReceived.getTax());
        assertEquals(testOrder.getTaxRate(),testOrderReceived.getTaxRate());
        assertEquals(testOrder.getTax(),testOrderReceived.getTax());
        assertEquals(testOrder.getLabourCost(),testOrderReceived.getLabourCost());
        
         //get second test order back and check all fields are the same
        Order testOrder2Received = allOrders.get(1);
        assertEquals(testOrder2.getArea(),testOrder2Received.getArea());
        assertEquals(testOrder2.getCostPerSquareFoot(),testOrder2Received.getCostPerSquareFoot());
        assertEquals(testOrder2.getCustomerName(),testOrder2Received.getCustomerName());
        assertEquals(testOrder2.getTax(),testOrder2Received.getTax());
        assertEquals(testOrder2.getTaxRate(),testOrder2Received.getTaxRate());
        assertEquals(testOrder2.getTax(),testOrder2Received.getTax());
        assertEquals(testOrder2.getLabourCost(),testOrder2Received.getLabourCost());
    }
    
    @Test
    public void testAddGetRemoveOrders() throws DataPersistenceException{
        
        //creat test Order
        Order testOrder = new Order();
        
        LocalDate deliveryDate = LocalDate.of(2025, Month.DECEMBER, 13);
        testOrder.setDeliveryDate(deliveryDate);
        testOrder.setCustomerName("MIMMM");
        testOrder.setState("ASd");
        testOrder.setTaxRate(BigDecimal.TEN);
        testOrder.setProductType("MOMOM");
        testOrder.setArea(BigDecimal.ZERO);
        testOrder.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder.setMaterialCost(BigDecimal.ZERO);
        testOrder.setLabourCost(BigDecimal.ZERO);
        testOrder.setTax(BigDecimal.TEN);
        testOrder.setTotal(BigDecimal.ONE);
        
        //add test order
        testOrderDao.addOrder(testOrder);
        
        //get test order back into list
        List<Order> allOrders =  testOrderDao.getOrders(deliveryDate);
         
        //check that this is only one item in list
        assertEquals(1, allOrders.size());
        
        //remove order from list
        testOrderDao.removeOrder(testOrder, deliveryDate);
        allOrders =  testOrderDao.getOrders(deliveryDate);
        
        //check that list is now empty
        assertEquals(0, allOrders.size());
    }
    
    @Test
    public void testAddExport() throws DataPersistenceException{
        //create test export file
        File file = new File("TestFiles/ExportData.txt");
        
        //creat test Order
        Order testOrder = new Order();
        
        LocalDate deliveryDate = LocalDate.of(2025, Month.DECEMBER, 12);
        testOrder.setDeliveryDate(deliveryDate);
        testOrder.setCustomerName("MIMMM");
        testOrder.setState("ASd");
        testOrder.setTaxRate(BigDecimal.TEN);
        testOrder.setProductType("MOMOM");
        testOrder.setArea(BigDecimal.ZERO);
        testOrder.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder.setMaterialCost(BigDecimal.ZERO);
        testOrder.setLabourCost(BigDecimal.ZERO);
        testOrder.setTax(BigDecimal.TEN);
        testOrder.setTotal(BigDecimal.ONE);
        
        //create second test Order
        Order testOrder2 = new Order();
        
        LocalDate deliveryDate2 = LocalDate.of(2025, Month.DECEMBER, 13);
        testOrder2.setDeliveryDate(deliveryDate2);
        testOrder2.setCustomerName("MIasdasd");
        testOrder2.setState("AasdSd");
        testOrder2.setTaxRate(BigDecimal.ZERO);
        testOrder2.setProductType("MOMOM");
        testOrder2.setArea(BigDecimal.ZERO);
        testOrder2.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder2.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder2.setMaterialCost(BigDecimal.ZERO);
        testOrder2.setLabourCost(BigDecimal.ZERO);
        testOrder2.setTax(BigDecimal.TEN);
        testOrder2.setTotal(BigDecimal.ONE);
        
        //add test orders
        testOrderDao.addOrder(testOrder);
        testOrderDao.addOrder(testOrder2);
        
        //export test orders
        testOrderDao.exportAllData();
        
        //check that export file is not longer empty
        assertNotEquals(file.length(), 0);
    }
    
    @Test
    public void testAddEditOrder() throws DataPersistenceException{
        //create new test order
        Order testOrder = new Order();
        
        LocalDate deliveryDate = LocalDate.of(2025, Month.DECEMBER, 12);
        testOrder.setDeliveryDate(deliveryDate);
        testOrder.setCustomerName("MIMMM");
        testOrder.setState("ASd");
        testOrder.setTaxRate(BigDecimal.TEN);
        testOrder.setProductType("MOMOM");
        testOrder.setArea(BigDecimal.ZERO);
        testOrder.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder.setMaterialCost(BigDecimal.ZERO);
        testOrder.setLabourCost(BigDecimal.ZERO);
        testOrder.setTax(BigDecimal.TEN);
        testOrder.setTotal(BigDecimal.ONE);
        
        //creat second test Order
        Order testOrder2 = new Order();
        
        LocalDate deliveryDate2 = LocalDate.of(2025, Month.DECEMBER, 12);
        testOrder2.setDeliveryDate(deliveryDate2);
        testOrder2.setOrderNumber(1);
        testOrder2.setCustomerName("MIasdasd");
        testOrder2.setState("AasdSd");
        testOrder2.setTaxRate(BigDecimal.ZERO);
        testOrder2.setProductType("MOMOM");
        testOrder2.setArea(BigDecimal.ZERO);
        testOrder2.setCostPerSquareFoot(BigDecimal.TEN);
        testOrder2.setLabourCostPerSqaureFoot(BigDecimal.ZERO);
        testOrder2.setMaterialCost(BigDecimal.ZERO);
        testOrder2.setLabourCost(BigDecimal.ZERO);
        testOrder2.setTax(BigDecimal.TEN);
        testOrder2.setTotal(BigDecimal.ONE);
        
        //add test order
        testOrderDao.addOrder(testOrder);
        //edit first order to give it the fields of test order two
        testOrderDao.editOrder(testOrder2, deliveryDate);
        
        //get order back as list
        List<Order> allorders = testOrderDao.getOrders(deliveryDate);
        
        //check that only one order exist
        assertEquals(1, allorders.size());
        
        //get edited order
        Order orderGotBack = allorders.get(0);
        
        //check that first order has been update to the second order
        assertEquals(testOrder2.getCustomerName(), orderGotBack.getCustomerName());
        
    }
}
