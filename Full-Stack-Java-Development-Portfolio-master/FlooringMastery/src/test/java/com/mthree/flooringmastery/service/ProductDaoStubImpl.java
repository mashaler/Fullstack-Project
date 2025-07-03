/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.DataPersistenceException;
import com.mthree.flooringmastery.dao.ProductDao;
import com.mthree.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author josep
 */
public class ProductDaoStubImpl implements ProductDao{

    @Override
    public Product getProduct(String productType) throws DataPersistenceException {
        
        Product product = new Product();
        
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("2.25"));
        product.setLabourCostPerSquareFoot(new BigDecimal("2.10"));
        
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws DataPersistenceException {
        return null;
    }
    
}
