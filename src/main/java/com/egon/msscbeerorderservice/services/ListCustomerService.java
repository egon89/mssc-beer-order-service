package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.CustomerDto;

import java.util.List;

public interface ListCustomerService {
  List<CustomerDto> execute();
}
