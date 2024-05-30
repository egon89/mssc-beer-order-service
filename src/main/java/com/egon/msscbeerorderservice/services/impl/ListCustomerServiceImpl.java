package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.CustomerDto;
import com.egon.brewery.dtos.CustomerPagedListDto;
import com.egon.msscbeerorderservice.entities.CustomerEntity;
import com.egon.msscbeerorderservice.mappers.CustomerMapper;
import com.egon.msscbeerorderservice.repositories.CustomerRepository;
import com.egon.msscbeerorderservice.services.ListCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ListCustomerServiceImpl implements ListCustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  @Override
  public CustomerPagedListDto execute(Pageable pageable) {
    Page<CustomerEntity> customerPage = repository.findAll(pageable);
    Function<Page<CustomerEntity>, List<CustomerDto>> customerPageFunction =
        customerEntityPage -> customerEntityPage.stream().map(mapper::toDto).toList();

    return CustomerPagedListDto.of(customerPage, customerPageFunction);
  }
}
