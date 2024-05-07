package com.panhareach.SpringBackend.repository;

import com.panhareach.SpringBackend.model.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {

    FileEntity findByIdAndDeletedAtIsNotNull(Long aLong);
    Optional<FileEntity> findByIdAndDeletedAtIsNull(Long aLong);


    Page<FileEntity> findAllByOriginalNameContainsAndDeletedAtIsNull(String name, Pageable pageable);

    Page<FileEntity> findAllByOriginalNameContainsAndDeletedAtIsNotNull(String name, Pageable pageable);
}
