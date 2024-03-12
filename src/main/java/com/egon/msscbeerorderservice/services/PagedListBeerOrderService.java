package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderPagedListDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PagedListBeerOrderService {
  BeerOrderPagedListDto execute(UUID customerId, Pageable pageable);
}
