package com.egon.msscbeerorderservice.actions;

import com.egon.brewery.dtos.events.ValidateBeerOrderRequest;
import com.egon.msscbeerorderservice.config.JmsConfig;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.BaseBeerOrderManager;
import com.egon.msscbeerorderservice.utils.StateContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidateOrderRequestAction implements Action<OrderStatusEnum, OrderEventEnum> {

  private final JmsTemplate jmsTemplate;

  private final BeerOrderRepository repository;

  private final BeerOrderMapper mapper;

  @Override
  public void execute(StateContext<OrderStatusEnum, OrderEventEnum> stateContext) {
    final var orderId = StateContextUtil.<UUID>getValueFromMessageHeader(
        stateContext, BaseBeerOrderManager.ORDER_ID_HEADER);
    log.debug("Validate order request action for order {}", orderId);
    final var beerOrder = repository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Beer order %s not found".formatted(orderId.toString())));
    final var message = ValidateBeerOrderRequest.builder()
        .beerOrderDto(mapper.toDto(beerOrder))
        .build();
    try {
      jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, message);
      log.debug("Order request {} sent to {} queue ", orderId, JmsConfig.VALIDATE_ORDER_QUEUE);
    } catch (Exception ex) {
      final var errorMessage = "Error to send order %s to %s queue".formatted(orderId, JmsConfig.VALIDATE_ORDER_QUEUE);
      log.error(errorMessage);
      throw new RuntimeException(errorMessage);
    }
  }
}
