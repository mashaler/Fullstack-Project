/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author josep
 */
public class ProductDaoFileImpl implements ProductDao{
    
    //File name that will store products
    public final String PRODUCT_FILE;
    //delimiter used in reading and write file
    public static final String DELIMITER = ",";
    
    public final String fileDirectory;
    
    //will hold in all read in products
    List<Product> products = new ArrayList<>();
    
    public ProductDaoFileImpl() {
        PRODUCT_FILE = "Products.txt";
        fileDirectory = "Data/";
    }
    
    public ProductDaoFileImpl(String productFileName, String fileDirectory){
        PRODUCT_FILE = productFileName;
        this.fileDirectory = fileDirectory;
    }
    
    /**
     * read in all products from txt file, and return the one whose type is passed in as a parameter
     * @param productType
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public Product getProduct(String productType) throws DataPersistenceException {
        loadProducts();
        
        Product userSelectedProduct = products.stream().
                filter((d)-> d.getProductType().equals(productType)).
                findFirst().orElse(null);
        
        return userSelectedProduct;
    }
    
    /**
     * read in all products, returns list of all products
     * @return
     * @throws DataPersistenceException 
     */
    @Override
    public List<Product> getAllProducts() throws DataPersistenceException {
        loadProducts();
        
        return products;
    }
    
    
    /**
     * load in all products
     * @throws DataPersistenceException 
     */
    private void loadProducts() throws DataPersistenceException{
        Scanner scanner;
        
        //remove all oldo
        products.clear();
        try{
            scanner = new Scanner(
                    new BufferedReader(
                    new FileReader(fileDirectory+PRODUCT_FILE)));
        }catch(FileNotFoundException e){
            throw new DataPersistenceException("COULD NOT LOAD PRODUCTS INTO MEMMORY.", e);
        }
        
        String currentLine;
        String[] productTokens;
        
        //Dont read in headerline
        scanner.nextLine();
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            productTokens = currentLine.split(DELIMITER);
            
            if(productTokens.length == 3){
                Product currentProduct = new Product();
                
                currentProduct.setProductType(productTokens[0]);
                currentProduct.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
                currentProduct.setLabourCostPerSquareFoot(new BigDecimal(productTokens[2]));
                
                products.add(currentProduct);
            }
        }
    }

  
    
    
    
}
