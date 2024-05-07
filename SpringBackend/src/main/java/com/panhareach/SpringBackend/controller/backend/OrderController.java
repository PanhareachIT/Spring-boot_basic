package com.panhareach.SpringBackend.controller.backend;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.infrastructure.model.response.body.BaseBodyResponse;
import com.panhareach.SpringBackend.model.entity.OrderEntity;
import com.panhareach.SpringBackend.model.request.order.OrderRequest;

import com.panhareach.SpringBackend.model.response.order.OrderResponse;
import com.panhareach.SpringBackend.model.response.error.ErrorResponse;

import com.panhareach.SpringBackend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Backend Category Order", description = "Controller for admin manage Order")
public class OrderController {

    private final OrderService orderServer;

    @Operation(summary = "Endpoint for admin create a order", description = "Admin can creating a order by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<BaseBodyResponse> create(@Valid @RequestBody OrderRequest req) throws Exception {
        OrderEntity order = this.orderServer.create(req);
        return BaseBodyResponse.createSuccess(OrderResponse.fromEntity(order), "Created Successfully");

    }



    @Operation(summary = "Endpoint for admin find all Orders", description = "Admin can finding all Orders by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")),
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
        Page<BaseResponse> order = this.orderServer.findAll(page, limit, sort, isPage, reqParam).map(OrderResponse::fromEntity);
        return BaseBodyResponse.success(order, "Success");
    }

    @Operation(summary = "Endpoint for admin update a order", description = "Admin can updating a order by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> update(@PathVariable Long id, @Valid @RequestBody OrderRequest request) throws Exception {
        OrderEntity order = this.orderServer.update(id, request);

        return BaseBodyResponse.success(OrderResponse.fromEntity(order), "Updated Successfully");
    }

    @Operation(summary = "Endpoint for admin finding a order", description = "Admin can finding a order by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> findOne(@PathVariable Long id) throws Exception {
        OrderEntity order = this.orderServer.findOne(id);

        return BaseBodyResponse.success(OrderResponse.fromEntity(order), "Found Successfully");
    }

    @Operation(summary = "Endpoint for admin delete a order", description = "Admin can deleting a order by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> delete(@PathVariable Long id) throws Exception {
        OrderEntity order = this.orderServer.deleteByid(id);

        return BaseBodyResponse.success(OrderResponse.fromEntity(order), "Deleted Successfully");
    }
}
