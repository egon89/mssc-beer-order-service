package com.egon.msscbeerorderservice.actions;

import com.egon.msscbeerorderservice.config.JmsConfig;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
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
public class ValidateFailedAction implements Action<OrderStatusEnum, OrderEventEnum> {

  private final JmsTemplate jmsTemplate;

  @Override
  public void execute(StateContext<OrderStatusEnum, OrderEventEnum> stateContext) {
    final var orderId = StateContextUtil.<UUID>getValueFromMessageHeader(
        stateContext, BaseBeerOrderManager.ORDER_ID_HEADER);
    try {
      jmsTemplate.convertAndSend(JmsConfig.VALIDATE_FAILED_QUEUE, orderId);
      log.debug("Order {} with failed validation sent to {} queue ", orderId, JmsConfig.VALIDATE_FAILED_QUEUE);
    } catch (Exception ex) {
      final var errorMessage = "Error to send order %s to %s queue".formatted(orderId, JmsConfig.VALIDATE_FAILED_QUEUE);
      log.error(errorMessage);
      throw new RuntimeException(errorMessage);
    }
  }
}
