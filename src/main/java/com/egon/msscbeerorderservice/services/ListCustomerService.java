package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.CustomerPagedListDto;
import org.springframework.data.domain.Pageable;

public interface ListCustomerService {
  CustomerPagedListDto execute(Pageable pageable);
}
