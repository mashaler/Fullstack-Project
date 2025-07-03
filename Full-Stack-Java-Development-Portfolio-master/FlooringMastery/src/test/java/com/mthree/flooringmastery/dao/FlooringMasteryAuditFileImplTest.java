/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author josep
 */
public class FlooringMasteryAuditFileImplTest {
    
    public FlooringMasteryAuditFileImplTest() {
    }
    
    FlooringMasteryAudit testAuditDao;
   
    File file;
    
    //create new audit txt file before test
    @BeforeEach
    public void setUp() throws IOException {
        file = new File("TestFiles/TestAudit.txt");
        new FileWriter("TestFiles/TestAudit.txt");
        testAuditDao = new FlooringMasteryAuditFileImpl("TestAudit.txt", "TestFiles/");
        
    }
  

    @Test
    public void testWriteEnty()throws Exception {
        //message to be writted
        String messageToAudit = "HI";
        
        //write message
        testAuditDao.writeAuditEntry(messageToAudit);
        
        //make sure that file has been written to
        assertNotEquals(file.length(),0);
    }
    
}
