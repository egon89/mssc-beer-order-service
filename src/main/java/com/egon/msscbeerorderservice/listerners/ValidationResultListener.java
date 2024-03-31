package com.egon.msscbeerorderservice.listerners;

import com.egon.brewery.dtos.events.ValidateBeerOrderResultDto;
import com.egon.msscbeerorderservice.config.JmsConfig;
import com.egon.msscbeerorderservice.services.ProcessValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

  private final ProcessValidationResult processValidationResult;

  @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESULT_QUEUE)
  public void listen(ValidateBeerOrderResultDto result) {
    final var beerOrderId = result.getId();
    log.debug("Validation result for order id %s".formatted(beerOrderId));
    processValidationResult.execute(beerOrderId, result.isValid());
  }
}
