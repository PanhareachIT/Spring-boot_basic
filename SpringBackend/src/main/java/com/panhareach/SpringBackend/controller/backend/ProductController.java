package com.panhareach.SpringBackend.controller.backend;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.infrastructure.model.response.body.BaseBodyResponse;
import com.panhareach.SpringBackend.model.entity.ProductEntity;
import com.panhareach.SpringBackend.model.request.product.ProductRequest;
import com.panhareach.SpringBackend.model.response.error.ErrorResponse;
import com.panhareach.SpringBackend.model.response.product.ProductResponse;
import com.panhareach.SpringBackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Endpoint for admin create a product", description = "Admin can creating a product by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(schema = @Schema(implementation = ProductResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<BaseBodyResponse> create(@Valid @RequestBody ProductRequest req) throws Exception {
        ProductEntity product = this.productService.create(req);
        return BaseBodyResponse.createSuccess(ProductResponse.fromEntity(product), "Created Successfully");

    }



    @Operation(summary = "Endpoint for admin find all Orders", description = "Admin can finding all Orders by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<BaseBodyResponse> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "isPage", required = false, defaultValue = "true") boolean isPage,
            @RequestParam(name = "sort", required = false, defaultValue = "id:desc") String sort,
            @RequestParam Map<String, String> reqParam
    ) throws Exception {
        Page<BaseResponse> product = this.productService.findAll(page, limit, sort, isPage, reqParam).map(ProductResponse::fromEntity);
        return BaseBodyResponse.success(product, "Success");
    }

    @Operation(summary = "Endpoint for admin update a product", description = "Admin can updating a product by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) throws Exception {
        ProductEntity product = this.productService.update(id, request);

        return BaseBodyResponse.success(ProductResponse.fromEntity(product), "Updated Successfully");
    }

    @Operation(summary = "Endpoint for admin finding a product", description = "Admin can finding a product by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> findOne(@PathVariable Long id) throws Exception {
        ProductEntity product = this.productService.findOne(id);

        return BaseBodyResponse.success(ProductResponse.fromEntity(product), "Found Successfully");
    }

    @Operation(summary = "Endpoint for admin delete a product", description = "Admin can deleting a product by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProductResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> delete(@PathVariable Long id) throws Exception {
        ProductEntity product = this.productService.deleteById(id);

        return BaseBodyResponse.success(ProductResponse.fromEntity(product), "Deleted Successfully");
    }


}
