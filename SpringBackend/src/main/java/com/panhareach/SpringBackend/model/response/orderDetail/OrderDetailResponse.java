package com.panhareach.SpringBackend.model.response.orderDetail;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.model.entity.OrderDetailEntity;
import com.panhareach.SpringBackend.model.entity.OrderEntity;
import com.panhareach.SpringBackend.model.request.orderDetail.OrderDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class OrderDetailResponse extends BaseResponse {
    private Long id;
    private String productName;
    private Integer Quantity;
    private Double price;

    public static OrderDetailResponse fromEntity(OrderDetailEntity entity){
        return new OrderDetailResponse(entity.getId(), entity.getProductName(), entity.getQty(), entity.getPrice());
    }
}
