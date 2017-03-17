package com.testo.Schema;

/**
 * Created by sonumalik on 17/03/17.
 */

public class Orders {
    private String timeStamp;
    private String productCode;

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
}
