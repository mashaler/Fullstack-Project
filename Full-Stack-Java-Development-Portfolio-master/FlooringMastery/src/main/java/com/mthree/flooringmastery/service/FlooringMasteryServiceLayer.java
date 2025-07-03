/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.DataPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * complete set of business logic methods. see implementation file for details
 */
public interface FlooringMasteryServiceLayer {
    
    Product getProduct(String productType) throws DataPersistenceException;
    
    List<Product> getAllProducts() throws DataPersistenceException;
    
    Tax getTax(String stateAbbreviation) throws DataPersistenceException;
    
    List<Tax> getAllTax() throws DataPersistenceException;
    
    Order completeOrder(Order order) throws DataPersistenceException;
    
    Order addOrder(Order orderToAdd) throws DataPersistenceException;
    
    List<Order> getOrdersByDate(LocalDate deliveryDate) throws DataPersistenceException, NoSuchOrderException;
    
    Order removeOrder(Order orderToReomve, LocalDate deliveryDate) throws DataPersistenceException;
    
    Order editOrder(Order editedOrder, LocalDate deliveryDate) throws DataPersistenceException;
    
    void exportOrders() throws DataPersistenceException;
    
    
}
