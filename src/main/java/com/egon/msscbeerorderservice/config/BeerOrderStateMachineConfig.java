package com.egon.msscbeerorderservice.config;

import com.egon.msscbeerorderservice.actions.ValidateOrderRequestAction;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@EnableStateMachineFactory
@RequiredArgsConstructor
@Configuration
public class BeerOrderStateMachineConfig
    extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {

  private final ValidateOrderRequestAction validateOrderRequestAction;

  @Override
  public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states) throws Exception {
    states.withStates()
        .initial(OrderStatusEnum.NEW)
        .states(EnumSet.allOf(OrderStatusEnum.class))
        .end(OrderStatusEnum.PICKED_UP)
        .end(OrderStatusEnum.DELIVERED)
        .end(OrderStatusEnum.DELIVERY_EXCEPTION)
        .end(OrderStatusEnum.VALIDATION_EXCEPTION)
        .end(OrderStatusEnum.ALLOCATION_EXCEPTION)
    ;
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions) throws Exception {
    transitions.withExternal()
        .source(OrderStatusEnum.NEW).target(OrderStatusEnum.VALIDATION_PENDING)
          .event(OrderEventEnum.VALIDATE_ORDER)
          .action(validateOrderRequestAction)
        .and().withExternal()
        .source(OrderStatusEnum.NEW).target(OrderStatusEnum.VALIDATED)
          .event(OrderEventEnum.VALIDATION_PASSED)
        .and().withExternal()
        .source(OrderStatusEnum.NEW).target(OrderStatusEnum.VALIDATION_EXCEPTION)
          .event(OrderEventEnum.VALIDATION_FAILED);
  }
}
