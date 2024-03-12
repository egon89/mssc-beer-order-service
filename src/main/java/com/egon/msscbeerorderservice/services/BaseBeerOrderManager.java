package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class BaseBeerOrderManager {
  protected final StateMachineFactory<OrderStatusEnum, OrderEventEnum> stateMachineFactory;
  protected final BeerOrderMapper mapper;
  protected final BeerOrderRepository repository;

  protected void sendEvent(BeerOrderDto beerOrderDto, OrderEventEnum orderEventEnum) {
    var smf = build(beerOrderDto);
    final var msg = MessageBuilder.withPayload(orderEventEnum).build();
    smf.sendEvent(Mono.just(msg)).blockFirst();
  }

  protected StateMachine<OrderStatusEnum, OrderEventEnum> build(BeerOrderDto dto) {
    var stateMachine = stateMachineFactory.getStateMachine(dto.getId());
    stateMachine.stopReactively().block();
    stateMachine.getStateMachineAccessor()
        .doWithAllRegions(sma -> {
          sma.resetStateMachineReactively(
              new DefaultStateMachineContext<>(dto.getOrderStatus(), null, null, null))
              .block();
        });
    stateMachine.startReactively().block();
    return stateMachine;
  }
}
