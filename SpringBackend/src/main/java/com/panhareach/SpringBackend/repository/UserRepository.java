package com.panhareach.SpringBackend.repository;

import com.panhareach.SpringBackend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByIdAndDeletedAtIsNull(Long id);
    Optional<UserEntity> findByIdAndDeletedAtIsNotNull(Long id);
    Boolean existsByUsernameAndDeletedAtIsNull(String username);
}
