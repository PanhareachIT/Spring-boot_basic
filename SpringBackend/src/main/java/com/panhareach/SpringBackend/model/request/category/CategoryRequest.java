package com.panhareach.SpringBackend.model.request.category;

import com.panhareach.SpringBackend.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Computer", maxLength = 30)
    @NotNull(message = "Name is required!")
    @NotEmpty(message = "Name cannot be empty!")
    @Size(max = 30, message = "Name cannot be bigger than 30 characters!")
    private String name;
    @Size(max = 100, message = "Description cannot be bigger than 100 characters!")
    @Schema(example = "Computer Category", maxLength = 100, nullable = true)
    private String description;

    public CategoryEntity toEntity(){
        CategoryEntity entity = new CategoryEntity();
        entity.setName(this.getName());
        entity.setDescription(this.getDescription());
        return entity;
    }
}
