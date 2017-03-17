package com.testo.Schema;

/**
 * Created by sonumalik on 17/03/17.
 */

public class Products {
    private String productCode;
    private String productName;
    private String productManufacturer;
    private String category;
    private String price;
    private int availableProducts;

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAvailableProducts(int availableProducts) {
        this.availableProducts = availableProducts;
    }
}
