/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.DataPersistenceException;
import com.mthree.flooringmastery.dao.TaxDao;
import com.mthree.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author josep
 */
public class TaxDaoStubImpl implements TaxDao{

    @Override
    public Tax getTax(String productType) throws DataPersistenceException {
        Tax tax = new Tax();
        
        tax.setStateAbbreviation("TX");
        tax.setStateName("Texas");
        tax.setTaxRate(new BigDecimal("4.45"));
        
        return tax;
    }

    @Override
    public List<Tax> getAllTaxes() throws DataPersistenceException {
        
        return null;
    }
    
}
