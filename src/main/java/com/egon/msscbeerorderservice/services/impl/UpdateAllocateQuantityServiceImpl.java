package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.brewery.dtos.BeerOrderLineDto;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.UpdateAllocateQuantityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateAllocateQuantityServiceImpl implements UpdateAllocateQuantityService {

  private final BeerOrderRepository repository;

  @Override
  public void execute(BeerOrderDto dto, BeerOrderEntity entity) {
    final var allocatedOrder = repository.findOneById(dto.getId()).orElseThrow();
    allocatedOrder.getBeerOrderLines().forEach(beerOrderLineEntity -> {
      final var quantityAllocated = dto.getBeerOrderLines().stream()
          .filter(beerOrderLineDto -> beerOrderLineDto.getId().equals(beerOrderLineEntity.getId()))
          .map(BeerOrderLineDto::getQuantityAllocated)
          .findFirst()
          .orElse(0);
      beerOrderLineEntity.setQuantityAllocated(quantityAllocated);
    });
    repository.saveAndFlush(entity);
  }
}
