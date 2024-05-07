package com.panhareach.SpringBackend.controller.backend;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.infrastructure.model.response.body.BaseBodyResponse;
import com.panhareach.SpringBackend.model.entity.UserEntity;
import com.panhareach.SpringBackend.model.request.user.UserRequest;
import com.panhareach.SpringBackend.model.request.user.UserRestoreRequest;
import com.panhareach.SpringBackend.model.response.category.CategoryResponse;
import com.panhareach.SpringBackend.model.response.error.ErrorResponse;
import com.panhareach.SpringBackend.model.response.user.UserResponse;
import com.panhareach.SpringBackend.service.UserService;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Backend User Controller", description = "Controller for admin manage User")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Endpoint for admin create a user", description = "Admin can creating a user by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<BaseBodyResponse> create(@Valid @RequestBody UserRequest req) throws Exception {
        UserEntity user = this.userService.create(req);
        return BaseBodyResponse.createSuccess(UserResponse.fromEntity(user), "User Create Successfully");
    }

    @Operation(summary = "Endpoint for admin find all categories", description = "Admin can finding all categories by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<BaseBodyResponse> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "isPage", required = false, defaultValue = "true") boolean isPage,
            @RequestParam(name = "sort", required = false, defaultValue = "id:desc") String sort,
            @RequestParam(name = "isTrash", required = false, defaultValue = "false") boolean isTrash,
            @RequestParam Map<String, String> reqParam
    ) throws Exception {

        Page<BaseResponse> category = this.userService.findAll(page, limit, sort, isPage, isTrash, reqParam).map(UserResponse::fromEntity);

        return BaseBodyResponse.success(category, "Success");
    }

    @Operation(summary = "Endpoint for admin update a category", description = "Admin can updating a category by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) throws Exception {
        UserEntity user = this.userService.update(id, request);

        return BaseBodyResponse.success(UserResponse.fromEntity(user), "Updated Successfully");
    }

    @Operation(summary = "Endpoint for admin restore a category", description = "Admin can restoring a category by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/restore/{id}")
    public ResponseEntity<BaseBodyResponse> restore(@PathVariable Long id, @Valid @RequestBody UserRestoreRequest req) throws Exception {
        UserEntity user = this.userService.restore(id, req);

        return BaseBodyResponse.success(UserResponse.fromEntity(user), "Restored Successfully");
    }

    @Operation(summary = "Endpoint for admin finding a category", description = "Admin can finding a category by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> findOne(@PathVariable Long id) throws Exception {
        UserEntity user = this.userService.findOne(id);

        return BaseBodyResponse.success(UserResponse.fromEntity(user), "Found Successfully");
    }

    @Operation(summary = "Endpoint for admin delete a category", description = "Admin can deleting a category by using this endpoint", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> delete(@PathVariable Long id) throws Exception {
        UserEntity user = this.userService.delete(id);

        return BaseBodyResponse.success(UserResponse.fromEntity(user), "Deleted Successfully");
    }
}
