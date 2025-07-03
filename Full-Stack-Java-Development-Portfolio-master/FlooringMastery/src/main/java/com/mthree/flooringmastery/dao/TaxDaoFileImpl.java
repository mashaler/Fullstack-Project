/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The methods in this class are identical to those in the ProductDaoFileImpl class, except the file read in is different
 * as we are now returning tax objects. See ProductDaoFileImpl for details
 */
public class TaxDaoFileImpl implements TaxDao{
  
    //File name that will store tax info
    public final String TAX_FILE;
    //delimiter used in reading and write file
    public static final String DELIMITER = ",";

    public final String fileDirectory;
    
    List<Tax> taxes = new ArrayList<>();
    
   
    
    public TaxDaoFileImpl() {
        TAX_FILE = "Taxes.txt";
        fileDirectory= "Data/";
    }
    
    public TaxDaoFileImpl(String taxFileName, String fileDirectory){
        TAX_FILE = taxFileName;
        this.fileDirectory = fileDirectory;
    }
    
    
    @Override
    public Tax getTax(String stateAbbreviation) throws DataPersistenceException {
        loadTaxes();
        
        Tax userSelectedTax = taxes.stream().
                filter((d)-> d.getStateAbbreviation().equals(stateAbbreviation)).
                findFirst().orElse(null);
        
        return userSelectedTax;
    }
    
    
    @Override
    public List<Tax> getAllTaxes() throws DataPersistenceException {
        loadTaxes();
        
        return taxes;
    }
    
    
    
    private void loadTaxes() throws DataPersistenceException{
        Scanner scanner;
        
        //remove all oldo
        taxes.clear();
        try{
            scanner = new Scanner(
                    new BufferedReader(
                    new FileReader(fileDirectory+TAX_FILE)));
        }catch(FileNotFoundException e){
            throw new DataPersistenceException("COULD NOT LOAD PRODUCTS INTO MEMMORY.", e);
        }
        
        String currentLine;
        String[] taxTokens;
        
        //Dont read in headerline
        scanner.nextLine();
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            taxTokens = currentLine.split(DELIMITER);
            
            if(taxTokens.length == 3){
                Tax currentTax = new Tax();
                
                currentTax.setStateAbbreviation(taxTokens[0]);
                currentTax.setStateName(taxTokens[1]);
                currentTax.setTaxRate(new BigDecimal(taxTokens[2]));
                
                taxes.add(currentTax);
            }
        }
    }

  
    
    
    
}
