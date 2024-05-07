package com.panhareach.SpringBackend.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AppProperties {
//    @Value("${ms.app.api-url}")
    private String apiUrl;

    public String getApiUrl() {
        return apiUrl;
    }

}
