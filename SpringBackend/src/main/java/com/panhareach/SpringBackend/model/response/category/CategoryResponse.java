package com.panhareach.SpringBackend.model.response.category;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse extends BaseResponse {
    @Schema(example = "168")
    private Long id;
    @Schema(example = "Best Category")
    private String name;
    @Schema(example = "Best Category Ever")
    private String description;

    public static CategoryResponse fromEntity(CategoryEntity category){
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }
}
