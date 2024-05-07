package com.panhareach.SpringBackend.model.entity;

import com.panhareach.SpringBackend.infrastructure.model.entity.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Table
@Entity(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends BaseSoftDeleteEntity<Long> {
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false)
    private Long size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FileEntity that = (FileEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
