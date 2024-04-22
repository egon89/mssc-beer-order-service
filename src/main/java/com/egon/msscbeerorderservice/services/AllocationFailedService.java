package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;

public interface AllocationFailedService {
  void execute(BeerOrderDto beerOrderDto);
}
