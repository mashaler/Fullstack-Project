/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.ui;

import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author josep
 */
public class FlooringMasteryView {
    
    UserIO io;

    public FlooringMasteryView(UserIO io){
        
        this.io = io;
    }
    
    private LocalDateTime dateToday = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy ... HH:mm");
    private String formattedDate = dateToday.format(formatter);
    
    public void dispalyWelcomeBanner(){
        io.print("****************************************\n"+
                "                WELCOME\n"+
                "****************************************");
    }
    
    public int displayMenuGetUserChoice(){
        io.print("HOW MAY I SERVE YOU TODAY?            "+formattedDate);
        
        io.print("1) Display Orders");
        io.print("2) Add an Order");
        io.print("3) Edit an Order");
        io.print("4) Remove an Order");
        io.print("5) Export All Data");
        io.print("6) Quit");
        
        int userChoice = io.readInt("PLEASE SELECT FROM THE OPTIONS ABOVE.", 1, 6);
        
        return userChoice;
    }
    
    public void displayExitBanner(){
        io.print(("Thank you. Enjoy your day!").toUpperCase());
    }
    
    /**
     * Pulls together all user prompts and returns a filled out order object based user input
     * numerous scapegoat variables are employed so the user can quickly jump back to main menu
     * @param allProductOptions
     * @param allTaxes
     * @return 
     */
    public Order addOrder(List<Product> allProductOptions, List<Tax> allTaxes){
        
        Order orderToAdd = new Order();
        
        orderToAdd.setCreationDate(dateToday);
        
        orderToAdd.setCustomerName(promptUserForName());
        
        //If user wants back to menu (they entered nothing)
        if(orderToAdd.getCustomerName().isBlank()){
            orderToAdd = null;
            return orderToAdd;
        }
        
        orderToAdd.setState(promptUserForState(allTaxes));
        
        //If user wants back to menu (they entered nothing)
        if(orderToAdd.getState().isBlank()){
            orderToAdd = null;
            return orderToAdd;
        }
        
        orderToAdd.setProductType(promptUserForProductType(allProductOptions));
        
        //If user wants back to menu (they entered nothing)
        if(orderToAdd.getProductType().isBlank()){
            orderToAdd = null;
            return orderToAdd;
        }
        
        orderToAdd.setArea(promtUserForArea());
        
        //If user wants back to menu (they entered nothing). This is a scapegoat big decimal
        if(orderToAdd.getArea().equals(BigDecimal.ZERO)){
           orderToAdd = null;
           return orderToAdd;
        }
         
        orderToAdd.setDeliveryDate(promptUserForDeliveryDate());
        
        //If user wants back to menu (they entered nothing). This is a scapegoat date
        if(orderToAdd.getDeliveryDate().equals(LocalDate.of(1000, Month.JANUARY, 1))){
            orderToAdd = null;
            return orderToAdd;
        }
        
        return orderToAdd;
    }
    
    /**
     * prompts user for company name. regex used to make sure input is of correct format
     * @return 
     */
    public String promptUserForName(){
        
        String userName = "";
        
        String regex = "([\\w\\s\\.,-])*";
        boolean goodInput = false;
        
        while(!goodInput){
            userName = io.readString("PLEASE INSERT COMPANY NAME:\nNB:Unless instructed otherwise, you may return to the main menu by hitting return.");
            if(userName.matches(regex)){
                goodInput = true;
            }else{
                io.print("PLEASE LIMIT YOU SELECTION TO NUMBERS, LETTERS AND THE FOLLOWING PUCTUTION: , . -");
            }
        }
       return userName;
    }
    
    /**
     * prompt user to enter state. Let user view complete list of allowed states if they enter MM
     * @param allTaxes
     * @return 
     */
    public String promptUserForState(List<Tax> allTaxes){
        List<String> stateAbbriations = allTaxes.stream()
                .map((s)-> s.getStateAbbreviation())
                .collect(Collectors.toList());
                
        boolean wantToSeeList = true;
        String userState = "";
        do{
            
            userState = io.readString("PLEASE ENTER YOUR STATE ABBREVIATION (e.g. Alabama - Al)."
                + "\nTO VIEW A COMPLETE LIST OF SERVICED STATES, PLEASE ENTER MM.", 2);
            if(userState.equals("MM")){
                io.print("THE FOLLOWING STATES ARE CURRENTLY SERVICED. PLEASE SELECT FROM THE FOLLOWING:");
                for(Tax t: allTaxes){
                    io.print(t.getStateName()+" - "+t.getStateAbbreviation());
                }
            }else if(userState.isBlank()){
                return userState;
                
            }else{
                
                if(stateAbbriations.contains(userState)){
                    wantToSeeList=false;
                }else{
                    io.print("THAT STATE IS NOT CURRENTLY SERVICED.");
                }
            }
        
        }while(wantToSeeList);
       
        return userState;
    }
    
    /**
     * display list of all products. prompt user to pick one
     * @param allProductTypes
     * @return 
     */
    public String promptUserForProductType(List<Product> allProductTypes){
        io.print("PLEASE SELECT THE MATERIAL YOU WISH:");
        int optionNumber = 1;
        //print each item in inventory
        for(Product type: allProductTypes){
            io.print(optionNumber+"). "+type.getProductType());
            optionNumber +=1;
        }
        
        io.print((allProductTypes.size()+1)+")."+ " BACK TO MAIN MENU");
        
        //make sure that user is given the option to select every menu item
        int userChoice = io.readInt("***PLEASE SELECT FROM OPTIONS ABOVE (1-"+(allProductTypes.size()+1)+")***", 1, allProductTypes.size()+1);
        
        if(userChoice != allProductTypes.size()+1){
            return allProductTypes.get((userChoice-1)).getProductType();
        }else{
            return "";
        }
        
    }
    
    public BigDecimal promtUserForArea(){
        return io.readBigDecimal("PLEASE INPUT REQUIRED FLOOR SIZE IN SQR FEET TO NO MORE THAN TWO DECIMAL PLACES (minimum area is 100.00)", new BigDecimal("100"), 2);
    }
    
    public LocalDate promptUserForDeliveryDate(){
        LocalDate maximumFutureDate = LocalDate.of(2050,Month.JANUARY,1);
        return io.readDate("PLEASE SELECT A DELIVERY DATE (dd/mm/yyyy).",dateToday.toLocalDate() , maximumFutureDate);
    }
    
    public void displayOrder(Order completedOrder){
        
        io.print(("\nSUMMARY OF ORDER:").toUpperCase());
        try{
            io.print("Delivery Date: " + completedOrder.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }catch(NullPointerException e){
           
        }
        
        io.print("Customer Name: " + completedOrder.getCustomerName());
        io.print("State: "+ completedOrder.getState());
        io.print("Tax Rate: " + completedOrder.getTaxRate() + "%");
        io.print("Product Type: " + completedOrder.getProductType());
        io.print("Area: " + completedOrder.getArea());
        io.print("Cost Per Square Foot: £" + completedOrder.getCostPerSquareFoot() +" per sqr foot");
        io.print("Labour Cost Per Square Foot: £" + completedOrder.getLabourCostPerSqaureFoot() +" per sqr foot");
        io.print("========== Total Costs ==========");
        io.print("Material Cost: £" + completedOrder.getMaterialCost());
        io.print("Labour Cost: £" + completedOrder.getLabourCost());
        io.print("Tax: £" + completedOrder.getTax());
        io.print("\n");
        io.print("Total: £"+ completedOrder.getTotal()+"\n");
    }
    
    public boolean promptUserToConfirmOrder(){
        
        io.print("DO YOU WISH TO CONFRIM?");
        
        boolean goodInput = false;
        
        while(!goodInput){
            
            String userChoice =  io.readString("Please choose: Y/N");
            
            if(userChoice.equalsIgnoreCase("y")){
                return true;
            }else if(userChoice.equalsIgnoreCase("n")){
                return false;
            }else{
                io.print("INAPPROPTIATE INPUT");
            }
        }
        
        return false;
    }
    
    public void displayViewOrdersBanner(LocalDate dateToView){
        
        io.print("THE FOLLOWING ORDERS ARE TO BE DELIVERED ON ... "+ dateToView.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
    }
    
    public void displayOrderShortSummary(List<Order> orderToDisplay){
        
        io.print("\nOrder Number: Name/Product/Area/State :: Total Cost");
        for(Order o : orderToDisplay){
            io.print(o.getOrderNumber()+" : "+
            o.getCustomerName()+"/"+
            o.getProductType()+ "/"+
            o.getArea()+ "/"+
            o.getState()+" :: £"+
            o.getTotal());
            
        }
    
    }
    
    public void displayErrorMessage(String errorMessage){
        io.print(errorMessage);
    }
    
    public void enterToContiue(){
        io.readString("PLEASE HIT ENTER TO CONTINUE.");
    }
    
    public void diplayUserRemoveChoice(Order orderToRemove){
        io.print("\nTHE FOLLOWING ORDER WILL BE REMOVED:");
        //io.print(orderToRemove);
        displayOrder(orderToRemove);
    }
    
    public int promptUserForOrderNumbers(List<Order> ordersByDate){
        
        displayOrderShortSummary(ordersByDate);
        
        int numberOfOrderToBeReomved = io.readInt("PLEASE CHOSE AN ORDER NUMBER, AND HIT ENETER.",
                ordersByDate.get(0).getOrderNumber(),
                ordersByDate.get(ordersByDate.size()-1).getOrderNumber());
        
        return numberOfOrderToBeReomved;
    }
    
    /**
     * prompt user for a new value of the field which the user has decided to edit
     * only update if the user enter a new value, otherwise return to editing menu
     * @param orderToBeEdited
     * @param userEditChoice
     * @param allProductTypes
     * @param allTaxes
     * @return 
     */
    public Order editOrder(Order orderToBeEdited, int userEditChoice,List<Product> allProductTypes, List<Tax> allTaxes){
        
        switch(userEditChoice){
            case 1:
                String updatedName = promptUserForName();
                if(!updatedName.isBlank()){
                    orderToBeEdited.setCustomerName(updatedName);
                }
                break;
            case 2:
                String updatedState = promptUserForState(allTaxes);
                if(!updatedState.isBlank()){
                    orderToBeEdited.setState(updatedState);
                }
                break;
            case 3:
                String updateProductType = promptUserForProductType(allProductTypes);
                if(!updateProductType.isBlank()){
                    orderToBeEdited.setProductType(updateProductType);
                }
                break;
            case 4:
                BigDecimal updateArea = promtUserForArea();
                if(!updateArea.equals(BigDecimal.ZERO)){
                    orderToBeEdited.setArea(updateArea);
                }
                break;
                
        }
        
        return orderToBeEdited;
        
    }
    
    public int displayEditingMenuGetUserChoice(){
        io.print("WHAT WOULD YOU LIKE TO EDIT:");
        io.print("1) Customer name");
        io.print("2) State");
        io.print("3) Product Type");
        io.print("4) Area");
        io.print("5) Back");
        
        return io.readInt("PLEASE SELECT FROM THE ABOVE OPTIONS.", 1, 5);
    }
    
    public void displayEditBanner(){
        io.print("*********** HERE IS YOU NEW UPDATED ORDER ***********\n");
    }
    
    public void displaySuccessfulEditBanner(){
        io.print("*********** ORDER SUCCESSFULLY EDITED ***********\n");
    }
    
    public void displaySuccessfulADDBanner(){
        io.print("*********** ORDER SUCCESSFULLY ADDED ***********\n");
    }
    public void displaySuccessfulREMOCEBanner(){
        io.print("*********** ORDER SUCCESSFULLY REMOVED ***********\n");
    }
    public void displaySuccessfulAEXOPTBanner(){
        io.print("*********** ORDERS SUCCESSFULLY BACKED-UP ***********\n");
    }
    
    public void displayOrderWasNotEditBanner(){
        io.print("*********** ORDER WAS NOT EDITED ***********\n");
    }
    
    
}
