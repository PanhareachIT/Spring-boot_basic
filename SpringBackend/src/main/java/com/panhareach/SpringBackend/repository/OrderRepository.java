package com.panhareach.SpringBackend.repository;

import com.panhareach.SpringBackend.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {

//    Optional<OrderEntity> findByIdAndDeletedAtIsNull(Long id);
}
