/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author josep
 */
public interface ProductDao {
    
    Product getProduct(String productType) throws DataPersistenceException;
    
    List<Product> getAllProducts() throws DataPersistenceException;
}
