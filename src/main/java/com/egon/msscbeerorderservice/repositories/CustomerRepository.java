package com.egon.msscbeerorderservice.repositories;

import com.egon.msscbeerorderservice.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
  List<CustomerEntity> findAllByNameLike(String name);
}
