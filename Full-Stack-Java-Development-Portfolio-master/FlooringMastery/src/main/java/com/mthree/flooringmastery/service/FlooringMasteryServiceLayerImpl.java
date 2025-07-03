/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.DataPersistenceException;
import com.mthree.flooringmastery.dao.OrderDao;
import com.mthree.flooringmastery.dao.ProductDao;
import com.mthree.flooringmastery.dao.TaxDao;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author josep
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{
    
    ProductDao productDao;
    TaxDao taxDao;
    OrderDao orderDao;

    public FlooringMasteryServiceLayerImpl(ProductDao productDao, TaxDao taxDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.orderDao = orderDao;
    }
    
    /**
     * get product object from product type 
     * @param productType
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Product getProduct(String productType) throws DataPersistenceException {
        return productDao.getProduct(productType);
    }

    /**
     * get all products, return as list
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public List<Product> getAllProducts() throws DataPersistenceException {
        return productDao.getAllProducts();
    }

    /**
     * get tax object from state name
     * @param stateAbbreviation
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Tax getTax(String stateAbbreviation) throws DataPersistenceException {
        return taxDao.getTax(stateAbbreviation);
    }

    /**
     * get list of all tax objects
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public List<Tax> getAllTax() throws DataPersistenceException {
        return taxDao.getAllTaxes();
    }

    /**
     * complete order. Calculate and set all costs from the user selection of state, material and area
     * return completed order
     * @param order
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Order completeOrder(Order order) throws DataPersistenceException {
        
        Product userChosenProduct = productDao.getProduct(order.getProductType());
        Tax userChosenTax = taxDao.getTax(order.getState());
        
        order.setCostPerSquareFoot(userChosenProduct.getCostPerSquareFoot());
        order.setLabourCostPerSqaureFoot(userChosenProduct.getLabourCostPerSquareFoot());
        order.setTaxRate(userChosenTax.getTaxRate());
        
        calculateOrderMaterialCost(order);
        calculateOrderLabourCost(order);
        calculateOrderTaxCost(order);
        
        calculateOrderTotalCost(order);
        
        return order;
    }

   /**
    * calculate and set material cost
    * @param order
    * @throws DataPersistenceException 
    */
    public void calculateOrderMaterialCost(Order order) throws DataPersistenceException {
        BigDecimal materialCost = order.getArea().multiply(order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        
        order.setMaterialCost(materialCost);
    }

    /**
     * 
     * calculate and set labour cost
     * @param order
     * @throws DataPersistenceException 
     */
    public void calculateOrderLabourCost(Order order) throws DataPersistenceException {
        BigDecimal labourCost = order.getArea().multiply(order.getLabourCostPerSqaureFoot()).setScale(2, RoundingMode.HALF_UP);
        
        order.setLabourCost(labourCost);
    }

    /**
     * calculate and set tax cost
     * @param order
     * @throws DataPersistenceException 
     */
    public void calculateOrderTaxCost(Order order) throws DataPersistenceException {
        
        BigDecimal materialCostPlusLabourCost = order.getLabourCost().add(order.getMaterialCost());
        
        BigDecimal taxPercentage = order.getTaxRate().divide(new BigDecimal("100"));//.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal taxCost = taxPercentage.multiply(materialCostPlusLabourCost).setScale(2, RoundingMode.HALF_UP);
        order.setTax(taxCost);
        
    }

    /**
     * calculate and set total cost
     * @param order
     * @throws DataPersistenceException 
     */
    public void calculateOrderTotalCost(Order order) throws DataPersistenceException {
        BigDecimal totalCost  = order.getMaterialCost().add(order.getLabourCost()).add(order.getTax());
        
        order.setTotal(totalCost);
    }

    /**
     * add order to txt file
     * @param orderToAdd
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Order addOrder(Order orderToAdd) throws DataPersistenceException {
        return orderDao.addOrder(orderToAdd);
    }

    /**
     * return list of all orders due on a given delivery date
     * @param deliveryDate
     * @return
     * @throws DataPersistenceException
     * @throws NoSuchOrderException 
     */
    @Override
    public List<Order> getOrdersByDate(LocalDate deliveryDate) throws DataPersistenceException, NoSuchOrderException {
        List<Order> ordersList = orderDao.getOrders(deliveryDate);
        
        if(ordersList.isEmpty()){
            throw new NoSuchOrderException("No orders are due to be delivered on " +deliveryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }else{
            return ordersList;
        }
        
    }

    /**
     * removes order passed in
     * @param orderToReomve
     * @param deliveryDate
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Order removeOrder(Order orderToReomve, LocalDate deliveryDate) throws DataPersistenceException {
        return orderDao.removeOrder(orderToReomve, deliveryDate);
    }

    /**
     * re-write edited order to txt file
     * @param editedOrder
     * @param deliveryDate
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Order editOrder(Order editedOrder, LocalDate deliveryDate) throws DataPersistenceException {
        return orderDao.editOrder(editedOrder, deliveryDate);
    }

    /**
     * export all orders to back-up txt file
     * @throws DataPersistenceException 
     */
    @Override
    public void exportOrders() throws DataPersistenceException {
        orderDao.exportAllData();
    }
    
}
