package com.panhareach.SpringBackend.infrastructure.model.response.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class BaseBodyResponse implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageResponse page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusResponse status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean success;

    public static ResponseEntity<BaseBodyResponse> success(Page<BaseResponse> response, String message) {
        List<BaseResponse> data = response.getContent();
        PageResponse page;
        if (response.getPageable().isUnpaged()) page = null;
        else
            page = new PageResponse(response.getNumber() + 1, response.getSize(), response.getTotalPages(), response.getTotalElements());
        StatusResponse status = new StatusResponse(message, (short) 200);

        return ResponseEntity.ok(new BaseBodyResponse(data, page, status, null));
    }

    public static ResponseEntity<BaseBodyResponse> success(BaseResponse response, String message) {
        StatusResponse status = new StatusResponse(message, (short) 200);

        return ResponseEntity.ok(new BaseBodyResponse(response, null, status, null));
    }

    public static ResponseEntity<BaseBodyResponse> createSuccess(BaseResponse response, String message) {
        StatusResponse status = new StatusResponse(message, (short) 201);

        return ResponseEntity.ok(new BaseBodyResponse(response, null, status, null));
    }

    public static ResponseEntity<BaseBodyResponse> failed(String message, HttpStatusCode httpStatus){
        BaseBodyResponse data = new BaseBodyResponse();
        StatusResponse statusResponse = new StatusResponse( message, (short) httpStatus.value());

        data.setStatus(statusResponse);
        data.setSuccess(false);
        return ResponseEntity.status(httpStatus).body(data);

    }
    public static ResponseEntity<BaseBodyResponse> internalFailed(String message, HttpStatusCode httpStatus) {
        BaseBodyResponse data = new BaseBodyResponse();
        StatusResponse statusResponse = new StatusResponse( message, (short) httpStatus.value());

        data.setStatus(statusResponse);
        data.setSuccess(false);
        return ResponseEntity.status(httpStatus).body(data);
    }

    public Object getData() {
        return data;
    }

    public PageResponse getPage() {
        return page;
    }

    public StatusResponse getStatus() {
        return status;
    }
}
