/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author josep
 */
public class Order{

    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSqaureFoot;
    private BigDecimal materialCost;
    private BigDecimal labourCost;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDateTime creationDate;
    private LocalDate deliveryDate;
    private LocalDateTime recentEditDate;

    @Override
    public String toString() {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costPerSquareFoot=" + costPerSquareFoot + ", labourCostPerSqaureFoot=" + labourCostPerSqaureFoot + ", materialCost=" + materialCost + ", labourCost=" + labourCost + ", tax=" + tax + ", total=" + total + ", creationDate=" + creationDate + ", deliveryDate=" + deliveryDate + ", recentEditDate=" + recentEditDate + '}';
    }

    
    public  Order(){
        
    }
    
    public Order(int orderNumber){
        this.orderNumber = orderNumber;
    }
    
    public Order(Order orderToClone){
        this.orderNumber = orderToClone.getOrderNumber();
        this.customerName = orderToClone.getCustomerName();
        this.state = orderToClone.getState();
        this.taxRate = orderToClone.getTaxRate();
        this.productType = orderToClone.getProductType();
        this.area = orderToClone.getArea();
        this.costPerSquareFoot = orderToClone.getCostPerSquareFoot();
        this.labourCostPerSqaureFoot = orderToClone.getLabourCostPerSqaureFoot();
        this.materialCost = orderToClone.getMaterialCost();
        this.labourCost = orderToClone.getLabourCost();
        this.tax = orderToClone.getTax();
        this.total = orderToClone.getTotal();
        this.creationDate = orderToClone.getCreationDate();
        this.deliveryDate = orderToClone.getDeliveryDate();
        this.recentEditDate = orderToClone.getRecentEditDate();
        
        
        
        
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.orderNumber;
        hash = 71 * hash + Objects.hashCode(this.customerName);
        hash = 71 * hash + Objects.hashCode(this.state);
        hash = 71 * hash + Objects.hashCode(this.taxRate);
        hash = 71 * hash + Objects.hashCode(this.productType);
        hash = 71 * hash + Objects.hashCode(this.area);
        hash = 71 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 71 * hash + Objects.hashCode(this.labourCostPerSqaureFoot);
        hash = 71 * hash + Objects.hashCode(this.materialCost);
        hash = 71 * hash + Objects.hashCode(this.labourCost);
        hash = 71 * hash + Objects.hashCode(this.tax);
        hash = 71 * hash + Objects.hashCode(this.total);
        hash = 71 * hash + Objects.hashCode(this.creationDate);
        hash = 71 * hash + Objects.hashCode(this.deliveryDate);
        hash = 71 * hash + Objects.hashCode(this.recentEditDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.labourCostPerSqaureFoot, other.labourCostPerSqaureFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.labourCost, other.labourCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.deliveryDate, other.deliveryDate)) {
            return false;
        }
        if (!Objects.equals(this.recentEditDate, other.recentEditDate)) {
            return false;
        }
        return true;
    }
    
    
    
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSqaureFoot() {
        return labourCostPerSqaureFoot;
    }

    public void setLabourCostPerSqaureFoot(BigDecimal labourCostPerSqaureFoot) {
        this.labourCostPerSqaureFoot = labourCostPerSqaureFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(BigDecimal labourCost) {
        this.labourCost = labourCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getRecentEditDate() {
        return recentEditDate;
    }

    public void setRecentEditDate(LocalDateTime recentEditDate) {
        this.recentEditDate = recentEditDate;
    }
    
    
    
}
