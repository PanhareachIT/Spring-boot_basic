package com.panhareach.SpringBackend.model.response.address;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponse extends BaseResponse {
    private Long id;
    private String address;

//    public static AddressResponse fromEntity(Add)
}
