/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

/**
 *
 * @author josep
 */
public class NoSuchOrderException extends Exception {

    /**
     * Creates a new instance of <code>NoSuchOrderException</code> without
     * detail message.
     */
    public NoSuchOrderException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs an instance of <code>NoSuchOrderException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoSuchOrderException(String msg) {
        super(msg);
    }
}
