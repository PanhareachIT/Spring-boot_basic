package com.panhareach.SpringBackend.model.entity;


import com.panhareach.SpringBackend.infrastructure.model.entity.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity(name = "Category")
@Table(name = "categories")

public class CategoryEntity extends BaseSoftDeleteEntity<Long> {
    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 100)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryEntity that = (CategoryEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

