package com.panhareach.SpringBackend.model.response.tag;

import com.panhareach.SpringBackend.infrastructure.model.response.BaseResponse;
import com.panhareach.SpringBackend.model.entity.TagsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagResponse extends BaseResponse {
    private Long id;
    private String name;
    public static TagResponse fromEntity(TagsEntity entity){
        return new TagResponse(entity.getId(), entity.getName());
    }
}
