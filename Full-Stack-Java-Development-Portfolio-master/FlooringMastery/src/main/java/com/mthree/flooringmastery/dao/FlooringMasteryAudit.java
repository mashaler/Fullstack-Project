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
public interface FlooringMasteryAudit {
    
    /**
     * Write an audit note whenever orders are added removed backed-up or edited
     * @param entry note to written to audit log
     * @throws DataPersistenceException 
     */
    public void writeAuditEntry(String entry) throws DataPersistenceException;
    
}
