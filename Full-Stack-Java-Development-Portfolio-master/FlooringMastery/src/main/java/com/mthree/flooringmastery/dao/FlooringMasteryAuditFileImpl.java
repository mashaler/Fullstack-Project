/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * audit log keeps track of orders being added removed backed-up and edited
 * @author josep
 */
public class FlooringMasteryAuditFileImpl implements FlooringMasteryAudit{
     
    //define auditing file
    private final String AUDIT_FILE;
    private final String auditFileDirectory;

    public FlooringMasteryAuditFileImpl() {
        AUDIT_FILE = "audit.txt";
        this.auditFileDirectory = "AuditLog/";
    }
    
    public FlooringMasteryAuditFileImpl(String auditFileName, String auditFileDirectory) {
        AUDIT_FILE =auditFileName;
        this.auditFileDirectory = auditFileDirectory;
    }
    
    @Override
    public void writeAuditEntry(String entry) throws DataPersistenceException{
        
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(auditFileDirectory+AUDIT_FILE, true));
         
        //error loading file
        }catch (IOException e){
            throw new DataPersistenceException("COULD NOT PERSIST AUDIT INFORMATION.", e);
        }
        
        //time stamp audit note
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        
        out.flush();
    }
}
