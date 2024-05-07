package com.panhareach.SpringBackend.model.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryRestoreRequest implements Serializable {
    @Size(max = 100, message = "Description cannot be bigger than 100 characters!")
    @Schema(example = "Computer Category", maxLength = 100, nullable = true)
    private String name;
}
