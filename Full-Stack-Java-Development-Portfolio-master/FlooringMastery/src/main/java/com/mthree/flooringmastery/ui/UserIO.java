/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author josep
 */
public interface UserIO {
    //print message
    void print(String msg);
    //print message return user string
    String readString(String prompt);
    String readString(String prompt, int requiredLength);
    
    //print message and return user input double
    double readDouble(String prompt);
    //dito but with min and max accepted values
    double readDouble(String prompt, double min, double max);
    
    //following methods work as above
    float readFloat(String prompt);
    float readFloat(String promp, float min, float max);
    
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    
    long readLong(String prompt);
    long readLong(String prompt, long min, long max);
    
    BigDecimal readBigDecimal(String prompt, BigDecimal minimumValue, int scale);
    
    LocalDate readDate(String prompt);
    LocalDate readDate(String prompt, LocalDate minimum, LocalDate maximum);
}
