package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.repositories.CustomerRepository;
import com.egon.msscbeerorderservice.services.PlaceOrderBeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceOrderBeerOrderServiceImpl implements PlaceOrderBeerOrderService {

  private final BeerOrderRepository beerOrderRepository;
  private final CustomerRepository customerRepository;
  private final BeerOrderMapper mapper;

  @Transactional
  @Override
  public BeerOrderDto execute(UUID customerId, BeerOrderDto beerOrderDto) {
    var customer = customerRepository.findById(customerId).orElseThrow(RuntimeException::new);
    var beerOrder = mapper.toEntity(beerOrderDto)
        .toBuilder()
        .id(null)
        .customer(customer)
        .orderStatus(OrderStatusEnum.NEW)
        .build();
    beerOrder.getBeerOrderLines().forEach(beerOrderLine -> beerOrderLine.setBeerOrder(beerOrder));
    var savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

    return mapper.toDto(savedBeerOrder);
  }
}
