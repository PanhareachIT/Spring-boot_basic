package com.panhareach.SpringBackend.model.request.product;

import com.panhareach.SpringBackend.model.entity.ProductEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductRequest implements Serializable {
    private String name;
    private Double price;
    private String description;
    private List<Long> tagId;
    public ProductEntity toEntity() {
        ProductEntity product = new ProductEntity();
        product.setName(this.name);
        product.setPrice(this.price);
        product.setDesciption(this.description);
        return product;
    }
}
