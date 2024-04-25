package com.egon.msscbeerorderservice.helper;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.brewery.dtos.BeerOrderLineDto;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BeerOrderHelper {
  public static BeerOrderDto createNewBeerOrderDto(UUID id, String upc) {
    final var orderLine = BeerOrderLineDto.builder()
        .beerId(UUID.randomUUID())
        .upc(upc)
        .price(new BigDecimal(new Random().nextLong(10)))
        .build();
    return  BeerOrderDto.builder()
        .id(id)
        .orderStatus(OrderStatusEnum.NEW)
        .beerOrderLines(List.of(orderLine))
        .build();
  }
}
