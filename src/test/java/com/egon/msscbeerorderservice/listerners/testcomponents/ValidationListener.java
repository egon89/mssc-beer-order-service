package com.egon.msscbeerorderservice.listerners.testcomponents;

import com.egon.brewery.dtos.events.AllocateBeerOrderRequest;
import com.egon.brewery.dtos.events.AllocateBeerOrderResult;
import com.egon.brewery.dtos.events.ValidateBeerOrderRequest;
import com.egon.brewery.dtos.events.ValidateBeerOrderResultDto;
import com.egon.msscbeerorderservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationListener {
  private final JmsTemplate jmsTemplate;

  @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
  public void listen(Message<?> msg){
    final var request = (ValidateBeerOrderRequest) msg.getPayload();
    log.debug("Receiving the beer order {} from {} queue",
        request.getBeerOrderDto().getId(), JmsConfig.VALIDATE_ORDER_QUEUE);
    final var result = ValidateBeerOrderResultDto.builder()
        .isValid(Boolean.TRUE)
        .id(request.getBeerOrderDto().getId())
        .build();
    jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, result);
    log.debug("Beer order {} (isValid: {}) sent to {} queue",
        request.getBeerOrderDto().getId(), result.isValid(), JmsConfig.VALIDATE_ORDER_RESULT_QUEUE);
  }

  @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
  public void listenAllocateOrder(Message<?> msg) {
    final var request = (AllocateBeerOrderRequest) msg.getPayload();
    log.debug("Receiving the beer order {} from {} queue",
        request.getBeerOrderDto().getId(), JmsConfig.ALLOCATE_ORDER_QUEUE);
    final var result = AllocateBeerOrderResult.builder()
        .beerOrderDto(request.getBeerOrderDto())
        .build();
    jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, result);
    log.debug("Beer order {} (isAllocationError: {}, isPendingInventory: {}) sent to {} queue",
        request.getBeerOrderDto().getId(),
        result.isAllocationError(),
        result.isPendingInventory(),
        JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE);
  }
}
