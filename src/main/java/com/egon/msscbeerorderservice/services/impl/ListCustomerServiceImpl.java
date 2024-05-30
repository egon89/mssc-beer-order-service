package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.CustomerDto;
import com.egon.msscbeerorderservice.mappers.CustomerMapper;
import com.egon.msscbeerorderservice.repositories.CustomerRepository;
import com.egon.msscbeerorderservice.services.ListCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCustomerServiceImpl implements ListCustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  @Override
  public List<CustomerDto> execute() {
    return repository.findAll().stream().map(mapper::toDto).toList();
  }
}
