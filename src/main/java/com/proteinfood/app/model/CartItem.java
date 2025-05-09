package com.proteinfood.app.model;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItem {
    private String id;
    private String foodPackageId;
    private String foodPackageName;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    
    public CartItem() {
        this.id = UUID.randomUUID().toString();
    }
    
    public CartItem(String foodPackageId, String foodPackageName, int quantity, 
                    BigDecimal pricePerUnit) {
        this.id = UUID.randomUUID().toString();
        this.foodPackageId = foodPackageId;
        this.foodPackageName = foodPackageName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = pricePerUnit.multiply(new BigDecimal(quantity));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodPackageId() {
        return foodPackageId;
    }

    public void setFoodPackageId(String foodPackageId) {
        this.foodPackageId = foodPackageId;
    }

    public String getFoodPackageName() {
        return foodPackageName;
    }

    public void setFoodPackageName(String foodPackageName) {
        this.foodPackageName = foodPackageName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (this.pricePerUnit != null) {
            this.totalPrice = this.pricePerUnit.multiply(new BigDecimal(quantity));
        }
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        if (this.quantity > 0) {
            this.totalPrice = pricePerUnit.multiply(new BigDecimal(this.quantity));
        }
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
