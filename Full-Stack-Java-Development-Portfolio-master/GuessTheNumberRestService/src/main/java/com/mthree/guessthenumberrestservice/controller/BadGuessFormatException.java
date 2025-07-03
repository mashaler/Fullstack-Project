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
public class BadGuessFormatException extends Exception {

    /**
     * Creates a new instance of <code>BadGuessFormatException</code> without
     * detail message.
     */
    public BadGuessFormatException() {
    }

    /**
     * Constructs an instance of <code>BadGuessFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BadGuessFormatException(String msg) {
        super(msg);
    }
}
