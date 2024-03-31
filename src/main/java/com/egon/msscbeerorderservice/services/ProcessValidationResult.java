package com.egon.msscbeerorderservice.services;

import java.util.UUID;

public interface ProcessValidationResult {
  void execute(UUID beerOrderId, boolean isValid);
}
