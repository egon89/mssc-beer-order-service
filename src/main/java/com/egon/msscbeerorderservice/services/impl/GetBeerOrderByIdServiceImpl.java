package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.repositories.CustomerRepository;
import com.egon.msscbeerorderservice.services.GetBeerOrderByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBeerOrderByIdServiceImpl implements GetBeerOrderByIdService {

  private final BeerOrderRepository beerOrderRepository;
  private final CustomerRepository customerRepository;
  private final BeerOrderMapper mapper;

  @Override
  public BeerOrderDto execute(UUID customerId, UUID orderId) {
    customerRepository.findById(customerId).orElseThrow(RuntimeException::new);
    var beerOrder = beerOrderRepository.findOneById(orderId).orElseThrow(RuntimeException::new);
    if (!beerOrder.getCustomer().getId().equals(customerId)) {
      throw new RuntimeException("Customer is not the owner of the order");
    }

    return mapper.toDto(beerOrder);
  }
}
