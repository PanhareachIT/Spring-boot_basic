package com.panhareach.SpringBackend.model.request.orderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderDetailRequest implements Serializable {
    private Integer quantity;
    private Double price;

    private String productName;
}
