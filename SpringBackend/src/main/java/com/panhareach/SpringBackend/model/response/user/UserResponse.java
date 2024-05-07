package com.panhareach.SpringBackend.model.response.user;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.model.entity.UserEntity;
import com.panhareach.SpringBackend.model.request.address.AddressRequest;
import com.panhareach.SpringBackend.model.response.address.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse extends BaseResponse {
    private String username;
    private AddressResponse addressResponse;

    public static UserResponse fromEntity(UserEntity user){
        AddressResponse address;
        if(user.getAddressEntity() == null){
            address = null;
        }else {
            address = new AddressResponse(user.getAddressEntity().getId(), user.getAddressEntity().getAddress());
        }
        return new UserResponse(user.getUsername(), address);
    }
}
