package com.testo.Schema;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sonumalik on 17/03/17.
 */

public class Orders {
    private String timeStamp;
    private String productCode;
    private String price;

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getProductCode() {
        return productCode;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeStamp", timeStamp);
        result.put("productCode", productCode);
        result.put("price", price);
        return result;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
