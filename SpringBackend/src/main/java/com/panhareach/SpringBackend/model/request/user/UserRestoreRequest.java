package com.panhareach.SpringBackend.model.request.user;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRestoreRequest implements Serializable {

    private String username;
}
