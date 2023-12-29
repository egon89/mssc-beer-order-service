package com.egon.msscbeerorderservice.repositories;

import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import com.egon.msscbeerorderservice.entities.CustomerEntity;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrderEntity, UUID> {
  Page<BeerOrderEntity> findAllByCustomer(CustomerEntity customer, Pageable pageable);

  List<BeerOrderEntity> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<BeerOrderEntity> findOneById(UUID id);
}
