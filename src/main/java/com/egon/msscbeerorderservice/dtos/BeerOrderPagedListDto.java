package com.egon.msscbeerorderservice.dtos;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerOrderPagedListDto extends PageImpl<BeerOrderDto> {
  public BeerOrderPagedListDto(List<BeerOrderDto> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public BeerOrderPagedListDto(List<BeerOrderDto> content) {
    super(content);
  }
}
