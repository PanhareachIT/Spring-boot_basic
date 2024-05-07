package com.panhareach.SpringBackend.model.response.product;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.model.entity.ProductEntity;
import com.panhareach.SpringBackend.model.response.tag.TagResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private List<TagResponse> tagResponses;

    public static ProductResponse fromEntity(ProductEntity entity){
        if(entity!=null){
            return new ProductResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getPrice(),
                    entity.getDesciption(),
                    entity.getTags().stream().map(TagResponse::fromEntity).toList()
            );
        }else {
            return null;
        }


        }

}
