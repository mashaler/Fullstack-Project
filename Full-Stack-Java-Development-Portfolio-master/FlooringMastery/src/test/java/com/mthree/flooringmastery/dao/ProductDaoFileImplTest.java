/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Product;
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
public class ProductDaoFileImplTest {
    
    ProductDao testProductDao;
    
    public ProductDaoFileImplTest() {
    }
    
    File file;
    String fileDirectory = "TestFiles/";
    
    //create test product file with sample date
    @BeforeEach
    public void setUp() throws Exception {
        String testFileName = "test_product_file.txt";
        file = new File(fileDirectory+testFileName);
        Writer out = new FileWriter(fileDirectory+testFileName);
        
 
        out.write("\nRug,2.25,2.10\n" +
                   "Stuff,1.75,2.10\n" +
                    "Soap,3.50,4.15");
        
        out.flush();
        out.close();
        
        testProductDao = new ProductDaoFileImpl(testFileName, fileDirectory);
        
    }
    
    @Test
    public void testGetProduc() throws Exception{
        //Create instance of first test product
        Product firstProduct = new Product();
        firstProduct.setProductType("Rug");
        firstProduct.setLabourCostPerSquareFoot(new BigDecimal("2.10"));
        firstProduct.setCostPerSquareFoot(new BigDecimal("2.25"));
        
       //Create instance of second test product
        Product secondProduct = new Product();
        secondProduct.setProductType("Stuff");
        secondProduct.setLabourCostPerSquareFoot(new BigDecimal("2.10"));
        secondProduct.setCostPerSquareFoot(new BigDecimal("1.75"));
        
        
        //get products back from file
        Product getProduct1 = testProductDao.getProduct("Rug");
        Product getProduct2 = testProductDao.getProduct("Stuff");
        
        //check that we got the write products back
        assertEquals(firstProduct, getProduct1);
        assertEquals(secondProduct, getProduct2);
        
    }
    
    @Test
    public void testGetAllProducts() throws Exception{
        //Create instance of first test product
        Product firstProduct = new Product();
        firstProduct.setProductType("Rug");
        firstProduct.setLabourCostPerSquareFoot(new BigDecimal("2.10"));
        firstProduct.setCostPerSquareFoot(new BigDecimal("2.25"));
        
       //Create instance of second test product
        Product secondProduct = new Product();
        secondProduct.setProductType("Stuff");
        secondProduct.setLabourCostPerSquareFoot(new BigDecimal("2.10"));
        secondProduct.setCostPerSquareFoot(new BigDecimal("1.75"));
        
        
        //get products back from file
        List<Product> allProducts = testProductDao.getAllProducts();
        
        //check that products were successfully returned
        assertTrue(allProducts.contains(firstProduct));
        assertTrue(allProducts.contains(secondProduct));
    }
    
}
