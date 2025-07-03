/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

/**
 *
 * @author josep
 */
public class DataPersistenceException extends Exception {

    /**
     * Creates a new instance of <code>DataPersistenceException</code> without
     * detail message.
     */
    public DataPersistenceException(String msg) {
        
        super(msg);
    }

    /**
     * Constructs an instance of <code>DataPersistenceException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DataPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
