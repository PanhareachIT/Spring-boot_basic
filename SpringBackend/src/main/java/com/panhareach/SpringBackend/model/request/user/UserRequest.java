package com.panhareach.SpringBackend.model.request.user;

import com.panhareach.SpringBackend.model.entity.UserEntity;
import com.panhareach.SpringBackend.model.request.address.AddressRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Panhareach", maxLength = 50)
    @Size(max = 50, message = "Username cannot be bigger than 30 characters!")
    private String username;


    private AddressRequest address;

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setUsername(this.username);
        user.setAddressEntity(address.toEntity(user));
        return user;
    }
}
