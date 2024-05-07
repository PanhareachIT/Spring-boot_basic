package com.panhareach.SpringBackend.model.response.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSizeResponse {
    private Long originalValue;
    private Long formatValue;
    private String formatType;
    private String normalized;

}
