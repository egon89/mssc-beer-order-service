package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;

public interface UpdateAllocateQuantityService {
  void execute(BeerOrderDto dto, BeerOrderEntity entity);
}
