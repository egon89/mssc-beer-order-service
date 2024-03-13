package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.NewOrderBeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NewOrderBeerServiceImplTest {

  @Autowired
  NewOrderBeerService newOrderBeerService;

  @Autowired
  BeerOrderRepository repository;

  @Test
  void shouldCreateANewBeerOrder() {
    final var dto = BeerOrderDto.builder().build();
    final var savedBeerOrder = newOrderBeerService.execute(dto);
    assertThat(savedBeerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.NEW);

    final var beerOrder = repository.findById(savedBeerOrder.getId()).orElseThrow();
    assertThat(beerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.NEW);
  }
}