package com.egon.msscbeerorderservice.repositories;

import com.egon.msscbeerorderservice.entities.BeerOrderLineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLineEntity, UUID> {
}
