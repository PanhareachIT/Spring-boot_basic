package com.panhareach.SpringBackend.model.request.address;

import com.panhareach.SpringBackend.model.entity.AddressEntity;
import com.panhareach.SpringBackend.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class AddressRequest implements Serializable {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Phnom Penh", maxLength = 50)
    @Size(max = 50, message = "Address cannot be bigger than 30 characters!")
    private String address;

    public AddressEntity toEntity(UserEntity user){
        AddressEntity address = new AddressEntity();
        address.setAddress(this.address);
        address.setUser(user);
        return address;
    }
}
