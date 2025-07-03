/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.DataPersistenceException;
import org.junit.jupiter.api.Test;
import com.mthree.flooringmastery.dto.Order;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author josep
 */
public class FlooringMasteryServiceLayerImplTest {
    
    public FlooringMasteryServiceLayerImplTest() {
    }
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    FlooringMasteryServiceLayer testService = ctx.getBean("testService", FlooringMasteryServiceLayer.class);
    
    @Test
    public void testCalculateOrder()throws DataPersistenceException {
        
        //create new partially filledout order
        Order testOrder = new Order();
        
        testOrder.setArea(BigDecimal.ONE);
        testOrder.setProductType("Wood");
        testOrder.setState("TX");
        
        //completed order
        Order completedTestOrder = testService.completeOrder(testOrder);
        
        //check that order was filled out correctly
        assertEquals(completedTestOrder.getArea(),BigDecimal.ONE);
        assertEquals(completedTestOrder.getMaterialCost(),new BigDecimal("2.25"));
        assertEquals(completedTestOrder.getLabourCost(),new BigDecimal("2.10"));
        assertEquals(completedTestOrder.getTax(),new BigDecimal("0.19"));
        
    }
    
    //THE REMAINED OF THE METHODS ARE JUST PASS-THOUGH METHODS FROM THE DAO
    //THESE HAVE BEEN TESTED SEPERATELY
    
    
}
