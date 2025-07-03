/*
 * The purpose of this project is to allow user to add, edit, remove, display and store orders for a flooring
 * sales firm. Orders are saved in a different file depending on their delivery date. All data in the system
 * can be exported at once, for backing up purposes.
 */
package com.mthree.flooringmastery;

import com.mthree.flooringmastery.controller.FlooringMasteryController;
import com.mthree.flooringmastery.dao.DataPersistenceException;
import com.mthree.flooringmastery.service.NoSuchOrderException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author joseph
 */
public class App {
    public static void main(String[] args) throws DataPersistenceException, NoSuchOrderException {
   
        //Spring DI
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        
        
        //main method of programme
        controller.run();
        
    }
}
