package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;

public interface AllocationPendingInventoryService {
  void execute(BeerOrderDto beerOrderDto);
}
