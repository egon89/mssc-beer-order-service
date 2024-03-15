package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.brewery.dtos.request.ValidateBeerOrderRequest;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.NewOrderBeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NewOrderBeerServiceImplTest {

  @Autowired
  NewOrderBeerService newOrderBeerService;

  @Autowired
  BeerOrderRepository repository;

  // remove the jmsTemplate mock to run an integration test
  @MockBean
  JmsTemplate jmsTemplate;

  @Test
  void shouldCreateANewBeerOrder() {
    final var dto = BeerOrderDto.builder().build();
    final var savedBeerOrder = newOrderBeerService.execute(dto);
    assertThat(savedBeerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.NEW);

    final var beerOrder = repository.findById(savedBeerOrder.getId()).orElseThrow();
    assertThat(beerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.VALIDATION_PENDING);
    verify(jmsTemplate).convertAndSend(anyString(), any(ValidateBeerOrderRequest.class));
  }
}