/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 * actions user can take involving orders 
 * @author josep
 */
public interface OrderDao {
    
    List<Order> getOrders(LocalDate deliveryDate) throws DataPersistenceException;
    
    Order addOrder(Order orderToAdd) throws DataPersistenceException;
    
    Order removeOrder(Order orderToRemove, LocalDate orderDeliveryDate) throws DataPersistenceException;
    
    Order editOrder(Order orderToEdit, LocalDate orderDeliveryDate) throws DataPersistenceException;
    
    void exportAllData() throws DataPersistenceException;
    
}
