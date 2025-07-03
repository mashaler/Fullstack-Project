/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Tax;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author josep
 */
public class TaxDaoFileImplTest {
     TaxDao testTaxDao;
    
    public TaxDaoFileImplTest() {
    }
    
    File file;
    String fileDirectory = "TestFiles/";
    
    /**
     * test file of sample data
     * @throws Exception 
     */
    @BeforeEach
    public void setUp() throws Exception {
        String testFileName = "test_tax_file.txt";
        file = new File(fileDirectory+testFileName);
        Writer out = new FileWriter(fileDirectory+testFileName);
        
 
        out.write("\nMU,Texas,4.45\n" +
                "NA,Washington,9.25\n" +
                    "TT,Kentucky,6.00");
        
        out.flush();
        out.close();
        
        testTaxDao = new TaxDaoFileImpl(testFileName, fileDirectory);
        
    }
   
    
     @Test
    public void testGetProduc() throws Exception{
        //Create instance of first test product
        Tax firstTax = new Tax();
        firstTax.setStateAbbreviation("MU");
        firstTax.setStateName("Texas");
        firstTax.setTaxRate(new BigDecimal("4.45"));
        
        //Create instance of first test product
        Tax secondTax = new Tax();
        secondTax.setStateAbbreviation("NA");
        secondTax.setStateName("Washington");
        secondTax.setTaxRate(new BigDecimal("9.25"));
        
        
        //get products back from file
        Tax getTax1 = testTaxDao.getTax("MU");
        Tax getTax2 = testTaxDao.getTax("NA");
        
        //check that we got the write products back
        assertEquals(firstTax, getTax1);
        assertEquals(secondTax, getTax2);
        
    }
    
     @Test
    public void testGetAllTaxes() throws Exception{
        
        //Create instance of first test tax
        Tax firstTax = new Tax();
        firstTax.setStateAbbreviation("MU");
        firstTax.setStateName("Texas");
        firstTax.setTaxRate(new BigDecimal("4.45"));
        
        //Create instance of second test tax
        Tax secondTax = new Tax();
        secondTax.setStateAbbreviation("NA");
        secondTax.setStateName("Washington");
        secondTax.setTaxRate(new BigDecimal("9.25"));
        
        
        List<Tax> allTaxes = testTaxDao.getAllTaxes();
        //check that we got the right taxes back
        assertTrue(allTaxes.contains(firstTax));
        assertTrue(allTaxes.contains(secondTax));
        
    }
    
}
