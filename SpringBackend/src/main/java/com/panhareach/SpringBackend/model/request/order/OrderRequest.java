package com.panhareach.SpringBackend.model.request.order;

import com.panhareach.SpringBackend.model.entity.OrderEntity;
import com.panhareach.SpringBackend.model.request.orderDetail.OrderDetailRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data

public class OrderRequest implements Serializable {
    private String customerName;
    private Double totalPrice;
    private List<OrderDetailRequest> orderDetail;

    public OrderEntity toEntity(){
        OrderEntity order = new OrderEntity();
        order.setCustomerName(this.customerName);
        return order;
    }
}
