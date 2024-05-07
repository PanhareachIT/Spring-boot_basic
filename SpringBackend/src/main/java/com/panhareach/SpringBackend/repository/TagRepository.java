package com.panhareach.SpringBackend.repository;

import com.panhareach.SpringBackend.model.entity.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagsEntity, Long> {
    Boolean existsByName (String name);
}
