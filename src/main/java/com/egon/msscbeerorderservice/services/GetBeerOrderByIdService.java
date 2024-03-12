package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;

import java.util.UUID;

public interface GetBeerOrderByIdService {
  BeerOrderDto execute(UUID customerId, UUID orderId);
}
