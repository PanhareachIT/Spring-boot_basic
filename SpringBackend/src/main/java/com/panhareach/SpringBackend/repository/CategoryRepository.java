package com.panhareach.SpringBackend.repository;

import com.panhareach.SpringBackend.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {
    Boolean existsByNameAndDeletedAtIsNull(String name);
//    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(ID id);
    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(Long id);
    Optional<CategoryEntity> findByIdAndDeletedAtIsNotNull(Long id);




}
