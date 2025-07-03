/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author josep
 */
public interface TaxDao {
    
    Tax getTax(String productType) throws DataPersistenceException;
    
    List<Tax> getAllTaxes() throws DataPersistenceException;
}

