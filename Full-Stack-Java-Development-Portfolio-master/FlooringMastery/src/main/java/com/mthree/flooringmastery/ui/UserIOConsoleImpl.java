/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author josep
 */

//@Component
public class UserIOConsoleImpl implements UserIO{
    
    final private Scanner console = new Scanner(System.in);

    private BigDecimal scapeGoatBD = BigDecimal.ZERO;
    private LocalDate scapeGoatDate = LocalDate.of(1000, Month.JANUARY, 1);
    
    /**
     *
     * A very simple method that takes in a message to display on the console 
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     *
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }
    
    @Override
    public String readString(String prompt, int requiredLength){
        boolean goodInput = false;
        
        String userInput = "";
        
        while(!goodInput){
            userInput = readString(prompt);
            if(userInput.length() == requiredLength){
                goodInput = true;
            }else if(userInput.isBlank()){
                return userInput;
                
            }else{
                System.out.println("YOU SELECTION SHOULD HAVE BE TWO CHARACTERS LONG. SEE THE EXAMPLE PROVIDED.");
            }
        }
        return userInput;
    }
    
    

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
//                
//                if(stringValue.isBlank()){
//                    return null;
//                }
                
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the min/max range
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a long
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as long
     */
    @Override
    public long readLong(String msgPrompt) {
        
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the min/max range
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the min/max range
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an double value as an answer to the message prompt within the min/max range
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal minimumValue, int maxScale) {
        
        boolean goodInput = false;
        
        BigDecimal userInput = BigDecimal.ZERO;
        
        while(!goodInput){
            boolean ableToParse = false;
            
            while(!ableToParse){
                try{
                    String userStringInput = readString(prompt);
                    
                    if(userStringInput.isBlank()){
                        return scapeGoatBD;
                    }
                    
                    userInput = new BigDecimal(userStringInput);
                    ableToParse =true;
                }catch(NumberFormatException e){
                    System.out.println("PLEASE ENTER A NUMBER");
                }
            }
            
            if(userInput.compareTo(minimumValue)<=0){
                System.out.println("PLEASE ENTER A VALUE GREATER THAN "+ minimumValue);
            }else if(userInput.scale()> maxScale){
                System.out.println("PLEASE USE THE REQUESTED INPUT FORMAT");
            }else{
                goodInput =true;
            }
        }
        return userInput;
    }

    @Override
    public LocalDate readDate(String prompt) {
        boolean goodInput = false;
        
       LocalDate userInputDate = null;
       // LocalDateTime userInputDate = LocalDateTime.now();
        String userInputString = "";
        
        while(!goodInput){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            userInputString = readString(prompt);
            
            if(userInputString.isBlank()){
                return scapeGoatDate;
            }
            
            try{
                userInputDate = LocalDate.parse(userInputString, formatter);
                goodInput = true;
            }catch(DateTimeException ex){
                System.out.println("THAT IS NOT A VALID DATE");
            }
        }
        
        return userInputDate;
    }

    @Override
    public LocalDate readDate(String prompt,  LocalDate minimum, LocalDate maximum){
        boolean goodInput = false;
        
        LocalDate userInputDate = null;
        while(!goodInput){
            userInputDate = readDate(prompt);
            
            if(userInputDate.equals(scapeGoatDate)){
                return scapeGoatDate;
            }
            
            if(userInputDate.isAfter(maximum)){
                System.out.println("TSG Corp can make no guarentee that delivery requested for so far into the future will be fulfilled."
                        + "Please conisder a more reasonable date");
            }else if(userInputDate.isBefore(minimum)){
                System.out.println("TSG Corp can neither confirm, nor deny, that orders requested for the past have \n"
                        + ", or have not, already been fulfilled. If the order has been fulfilled,\nthen placing the order would redundent."
                        + "And, if the order was not fulfilled,\nwe at TSG Corp whole-heartedly apologise. ");
            }else{
                goodInput = true;
            }
        }
        
        return userInputDate;
    }
}
