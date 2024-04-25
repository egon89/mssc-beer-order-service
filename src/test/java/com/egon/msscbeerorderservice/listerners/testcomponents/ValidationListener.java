package com.egon.msscbeerorderservice.listerners.testcomponents;

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
  public void list(Message<?> msg){

    final var request = (ValidateBeerOrderRequest) msg.getPayload();

    System.out.println("########### I RAN ########");

    jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE,
        ValidateBeerOrderResultDto.builder()
            .isValid(true)
            .id(request.getBeerOrderDto().getId())
            .build());
  }
}
