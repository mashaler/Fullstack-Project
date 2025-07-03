/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.controller;

/**
 *
 * @author josep
 */
public class GameNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>GameNotFoundException</code> without
     * detail message.
     */
    public GameNotFoundException(String msg, Throwable cause) {
        
        super(msg, cause);
    }

    /**
     * Constructs an instance of <code>GameNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GameNotFoundException(String msg) {
        super(msg);
    }
}
