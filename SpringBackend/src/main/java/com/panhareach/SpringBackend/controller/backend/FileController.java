package com.panhareach.SpringBackend.controller.backend;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.infrastructure.model.response.body.BaseBodyResponse;
import com.panhareach.SpringBackend.model.entity.FileEntity;

import com.panhareach.SpringBackend.model.response.file.FileResponse;
import com.panhareach.SpringBackend.property.AppProperties;
import com.panhareach.SpringBackend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
//@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final AppProperties appProperties;

    @Autowired
    public FileController( FileService fileService, AppProperties appProperties) {

        this.fileService = fileService;
        this.appProperties = appProperties;
    }

//    @Operation(summary = "Endpoint for user upload a file", description = "User can uploading a file by using this endpoint", responses = {
//            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = FileResponse.class), mediaType = "application/json")),
//            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))})
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileEntity> upload(@RequestPart MultipartFile file) throws Exception {
        FileEntity entity =  this.fileService.upload(file);
        return ResponseEntity.ok(entity);
    }
    @GetMapping("/app")
    public ResponseEntity<FileEntity> app() throws Exception {
        System.out.println("ddfda");
        System.out.println("Null man bro " + this.appProperties.getApiUrl());

        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<BaseBodyResponse> findAll (
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "isTrash", defaultValue = "false") boolean isTrash,
            @RequestParam(required = false, name = "q", defaultValue = "") String q
    ){
        Page<BaseResponse> files = this.fileService.findAll(page, limit, isTrash, q).map(fileEntity -> FileResponse.toResponse(fileEntity, appProperties));
        return BaseBodyResponse.success(files, "Find All Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseBodyResponse> froceDelete(@PathVariable Long id) throws Exception {
        FileEntity file = this.fileService.froceDelete(id);
        return BaseBodyResponse.success(FileResponse.toResponse(file, this.appProperties), "Delete Successfully");
    }
}
