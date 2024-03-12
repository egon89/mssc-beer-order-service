package com.egon.msscbeerorderservice.interceptors;

import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderStatusEnum, OrderEventEnum> {

  private final BeerOrderRepository repository;

  @Override
  public void preStateChange(
      State<OrderStatusEnum, OrderEventEnum> state,
      Message<OrderEventEnum> message,
      Transition<OrderStatusEnum, OrderEventEnum> transition,
      StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine,
      StateMachine<OrderStatusEnum, OrderEventEnum> rootStateMachine) {
    final var id = Optional.ofNullable(stateMachine)
        .map(StateMachine::getUuid)
        .orElseThrow(IllegalArgumentException::new);
    final var newStatus = Optional.ofNullable(state).map(State::getId).orElseThrow(IllegalArgumentException::new);
    final var event = Optional.ofNullable(message).map(Message::getPayload).orElseThrow(IllegalArgumentException::new);
    final var beerOrder = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found!"));
    final var previousStatus = beerOrder.getOrderStatus();
    beerOrder.setOrderStatus(newStatus);
    final var savedBeerOrder = repository.save(beerOrder);
    log.debug("Beer Order {} changed from {} to {} in {} event",
        savedBeerOrder.getId(), previousStatus, savedBeerOrder.getOrderStatus(), event);
  }
}
