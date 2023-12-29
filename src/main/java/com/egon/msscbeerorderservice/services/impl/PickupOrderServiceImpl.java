package com.egon.msscbeerorderservice.services.impl;

import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.GetBeerOrderByIdService;
import com.egon.msscbeerorderservice.services.PickupOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PickupOrderServiceImpl implements PickupOrderService {

  private final BeerOrderRepository beerOrderRepository;
  private final GetBeerOrderByIdService getBeerOrderByIdService;
  private final BeerOrderMapper mapper;

  @Override
  public void execute(UUID customerId, UUID orderId) {
    var beerOrderDto = getBeerOrderByIdService.execute(customerId, orderId)
        .toBuilder()
        .orderStatus(OrderStatusEnum.PICKED_UP)
        .build();

    beerOrderRepository.save(mapper.toEntity(beerOrderDto));
  }
}
