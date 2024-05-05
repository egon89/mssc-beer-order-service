package com.egon.msscbeerorderservice.actions;

import com.egon.brewery.dtos.events.AllocateBeerOrderRequest;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.BaseBeerOrderManager;
import jakarta.jms.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.support.DefaultStateContext;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AllocateOrderActionTest {

  @Autowired
  AllocateOrderAction action;

  @MockBean
  BeerOrderRepository repository;

  @Autowired
  BeerOrderMapper mapper;

  // change to @Autowired for integration test
  @MockBean
  JmsTemplate jmsTemplate;

  @MockBean
  ConnectionFactory connectionFactory;

  @Test
  void shouldSendBeerOrderToAllocateOrderQueue() {
    final var header = new MessageHeaders(Map.of(BaseBeerOrderManager.ORDER_ID_HEADER, UUID.randomUUID()));
    final var message = new GenericMessage<OrderEventEnum>(OrderEventEnum.ALLOCATE_ORDER, header);
    final var stateContext = new DefaultStateContext<OrderStatusEnum, OrderEventEnum>(null, message, header, null, null, null, null, null, null, null, null);
    final var beerOrderEntity = BeerOrderEntity.builder()
        .id(UUID.randomUUID())
        .updatedAt(Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()))
        .createdAt(Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()))
        .build();
    when(repository.findById(any())).thenReturn(Optional.of(beerOrderEntity));

    action.execute(stateContext);

    verify(jmsTemplate).convertAndSend(anyString(), any(AllocateBeerOrderRequest.class));
  }
}