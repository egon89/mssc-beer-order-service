package com.egon.brewery.dtos;

import com.egon.msscbeerorderservice.entities.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

public class CustomerPagedListDto extends PageImpl<CustomerDto> {
  public CustomerPagedListDto(List<CustomerDto> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public CustomerPagedListDto(List<CustomerDto> content) {
    super(content);
  }

  public static CustomerPagedListDto of(
      Page<CustomerEntity> customerPage,
      Function<Page<CustomerEntity>, List<CustomerDto>> customerFunction
      ) {
    List<CustomerDto> customers = customerFunction.apply(customerPage);
    Pageable customerPageable = customerPage.getPageable();
    PageRequest pageable = PageRequest.of(customerPageable.getPageNumber(), customerPageable.getPageSize());
    long totalElements = customerPage.getTotalElements();

    return new CustomerPagedListDto(customers, pageable, totalElements);
  }
}
