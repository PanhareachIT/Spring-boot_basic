package com.panhareach.SpringBackend.model.response.order;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.model.entity.OrderDetailEntity;
import com.panhareach.SpringBackend.model.entity.OrderEntity;
import com.panhareach.SpringBackend.model.response.orderDetail.OrderDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@AllArgsConstructor
public class OrderResponse extends BaseResponse {
    private Long id;
    private String customerName;
    private Double TotalPrice;
    private List<OrderDetailResponse> orderDetail;

    public OrderResponse(Long id, String customerName, Double totalPrice, List<OrderDetailResponse> orderDetail) {
        this.id = id;
        this.customerName = customerName;
        TotalPrice = totalPrice;
        this.orderDetail = orderDetail;
    }

    public static OrderResponse fromEntity(OrderEntity entity){

        if (entity == null) return null;

        return new OrderResponse(
                entity.getId(),
                entity.getCustomerName(),
                entity.getTotalPrice(),
                entity.getOrderDetails().stream().map(OrderDetailResponse::fromEntity).toList()
        );
//        List<OrderDetailResponse> orderDetails = new ArrayList<>();
//        if(entity.getOrderDetails() == null){
//            orderDetails = null;
//        }else {
//            for (OrderDetailEntity data: entity.getOrderDetails()){
//                orderDetails.add(OrderDetailResponse.fromEntity(data));
//            }
//        }
//
//        return new OrderResponse(entity.getId(), entity.getCustomerName(), entity.getTotalPrice(), orderDetails);
    }
}
