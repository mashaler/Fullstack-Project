/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.controller;

import com.mthree.flooringmastery.dao.DataPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import com.mthree.flooringmastery.service.FlooringMasteryServiceLayer;
import com.mthree.flooringmastery.service.NoSuchOrderException;
import com.mthree.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josep
 */
public class FlooringMasteryController {
    
    //DI
    FlooringMasteryView view;
    FlooringMasteryServiceLayer service;
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    //to hold user choice of order to edit: employed in edit method. Initialised to inaccessible int
    int userOrderToEditSelection = -1;
    
    //presents user with main of operation, allows use to access all programme functionality
    public void run() throws DataPersistenceException, NoSuchOrderException{
        
        boolean keepGoing = true;
        int menuChoice = 0;
        
        displayWelcomeBanner();
        
        //keep user returning to main menu
        while(keepGoing){
            menuChoice = displayMenu();
            
            switch(menuChoice){
                case 1:
                    listOrdersByDate();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removedOrder();
                    break;
                case 5:
                    exportOrders();
                    break;
                case 6:
                    keepGoing = false;
                    break;
            }
        }
        displayExitBanner();
    }
    
    private void displayWelcomeBanner(){
        
        view.dispalyWelcomeBanner();
    }
    
    //display list of actions user can take
    private int displayMenu(){
        
       int userMenuSelection = view.displayMenuGetUserChoice();
       return userMenuSelection;
    }
    
    private void displayExitBanner(){
        view.displayExitBanner();
    }
    
    /**
     * Allows user to add order
     * All user input validation (i.e. valid variable types, reasonable dates and requests for services that can be provided are handled here)
     * Once validated, orders are stored to a txt file depending on there delivery date
     * @throws DataPersistenceException 
     */
    private void addOrder() throws DataPersistenceException{
        
        //products and taxes to display to user
        List<Product> allProducts = service.getAllProducts();
        List<Tax> allTaxes = service.getAllTax();
        
        Order orderToAdd = view.addOrder(allProducts, allTaxes);
        
        if(orderToAdd != null){
            
            //fill out remaining order fields
            orderToAdd = service.completeOrder(orderToAdd);
            view.displayOrder(orderToAdd);
            
            boolean userWantsToConfrim = view.promptUserToConfirmOrder();
            if(userWantsToConfrim){
                service.addOrder(orderToAdd);
                view.displaySuccessfulADDBanner();
            }
        }
    }
    
    /**
     * Lists all dates due to be delivered on a user selected date
     * User selected date is validated to ensure a date exist on chosen date
     * @throws DataPersistenceException
     * @throws NoSuchOrderException 
     */
    private void listOrdersByDate() throws DataPersistenceException, NoSuchOrderException{
       
        boolean hasErrors = false;
        
        List<Order> allOrdersByDate = new ArrayList<>();
        
        do{
            try{
                LocalDate dateToView = view.promptUserForDeliveryDate();
                
                //This conditional resonds to user hitting return without inputing a date (i.e they want to go back to main menu)
                //In this case, the following "scapegoat" date is returned
                if(dateToView.equals(LocalDate.of(1000, Month.JANUARY, 1))){
                    break;
                }
                
                allOrdersByDate = service.getOrdersByDate(dateToView);
                view.displayViewOrdersBanner(dateToView);
                view.displayOrderShortSummary(allOrdersByDate);
                view.enterToContiue();
            }catch(NoSuchOrderException | DataPersistenceException e){
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;
            }
        }while(hasErrors);
    }

    /**
     * Allows user to remove a date
     * User inputs a delivery date, and is presented with all orders to be delivered on that date
     * User can then select which order to remove from that day
     * @throws DataPersistenceException
     * @throws NoSuchOrderException 
     */
    public void removedOrder() throws DataPersistenceException, NoSuchOrderException{
        
        boolean hasErrors = false;
        List<Order> allOrdersByDate = new ArrayList<>();
        
        do{
            try{
            LocalDate dateToView = view.promptUserForDeliveryDate();
            
            //This conditional resonds to user hitting return without inputing a date (i.e they want to go back to main menu)
            //In this case, the following "scapegoat" date is returned
            if(dateToView.equals(LocalDate.of(1000, Month.JANUARY, 1))){
                  break;
            }
            
            allOrdersByDate = service.getOrdersByDate(dateToView);
            userOrderToEditSelection = view.promptUserForOrderNumbers(allOrdersByDate);
            
            //from list of all orders, get the one the user wants to remove
            Order orderToBeRemoved = allOrdersByDate.stream()
                    .filter((o) -> o.getOrderNumber() == userOrderToEditSelection)
                    .findFirst()
                    .orElse(null);
            
            view.diplayUserRemoveChoice(orderToBeRemoved);
            
            boolean userWantsToConfrim = view.promptUserToConfirmOrder();
            
            if(userWantsToConfrim){
                service.removeOrder(orderToBeRemoved, dateToView);
                view.displaySuccessfulREMOCEBanner();
            }else{
                break;
            }
            }catch(DataPersistenceException | NoSuchOrderException e){
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;
            }
            
        }while(hasErrors);
        
    }
    
    /**
     * Allows user to edit order
     * User inputs a delivery date, and is presented with all orders to be delivered on that date
     * User can then select which order to edit from that day
     * User can select one or more field to edit
     * edited order is saved only if user has made genuine edit
     * @throws DataPersistenceException 
     */
    public void editOrder()throws DataPersistenceException{
        
        int userEditChoice = 0;
        List<Product> allProducts = service.getAllProducts();
        List<Tax> allTaxes = service.getAllTax();
        List<Order> allOrdersByDate = new ArrayList<>();
        
        boolean hasErrors = false;
        
        do{
            try{
                LocalDate dateToView = view.promptUserForDeliveryDate();
                
                //This conditional resonds to user hitting return without inputing a date (i.e they want to go back to main menu)
                //In this case, the following "scapegoat" date is returned
                if(dateToView.equals(LocalDate.of(1000, Month.JANUARY, 1))){
                      break;
                }
                
                allOrdersByDate = service.getOrdersByDate(dateToView);
                userOrderToEditSelection = view.promptUserForOrderNumbers(allOrdersByDate);

                //get order to be edited
                Order orderToBeEdited = allOrdersByDate.stream()
                    .filter((o) -> o.getOrderNumber() == userOrderToEditSelection)
                    .findFirst()
                    .orElse(null);
                
                //make a copy of order before edit to ensure that user actually edited order
                Order orderBeforeEdit = new Order(orderToBeEdited);
                boolean keepEditing = true;
                
                while(keepEditing){
                    userEditChoice = view.displayEditingMenuGetUserChoice();
                    
                    //user wants to edit
                    if(userEditChoice != 5){
                        orderToBeEdited = view.editOrder(orderToBeEdited, userEditChoice, allProducts, allTaxes);
                        
                        //if has actually made a change update order
                        if(!orderBeforeEdit.equals(orderToBeEdited)){
                            orderToBeEdited.setDeliveryDate(dateToView);
                            orderToBeEdited = service.completeOrder(orderToBeEdited);
                        }
                    }else{
                        keepEditing = false;
                    }
                }
                
                //user has made a change
                if(!orderBeforeEdit.equals(orderToBeEdited)){
                    
                    view.displayEditBanner();
                    view.displayOrder(orderToBeEdited);
                   
                    boolean userWantsToConfrim = view.promptUserToConfirmOrder();

                    if(userWantsToConfrim){
                        service.editOrder(orderToBeEdited, orderToBeEdited.getDeliveryDate());
                        view.displaySuccessfulEditBanner();
                    }else{
                        view.displayOrderWasNotEditBanner();
                    }
                    
                //user has made no change overall
                }else{
                    view.displayOrderWasNotEditBanner();
                }
            }catch(DataPersistenceException | NoSuchOrderException e){
                  view.displayErrorMessage(e.getMessage());
                  hasErrors = true;
            }
        }while(hasErrors);
       
    }
    
    /**
     * exports all orders in system to a single file
     * orders a saved to a new line ending in their delivery date (MMddyyyy)
     * @throws DataPersistenceException 
     */
    public void exportOrders() throws DataPersistenceException{
        service.exportOrders();
        view.displaySuccessfulAEXOPTBanner();
    }
}
