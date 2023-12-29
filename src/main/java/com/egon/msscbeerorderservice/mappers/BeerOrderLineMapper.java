package com.egon.msscbeerorderservice.mappers;

import com.egon.msscbeerorderservice.dtos.BeerOrderLineDto;
import com.egon.msscbeerorderservice.dtos.request.BeerOrderRequestDto;
import com.egon.msscbeerorderservice.entities.BeerOrderLineEntity;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerOrderLineMapper {
  BeerOrderLineDto toDto(BeerOrderLineEntity entity);

  BeerOrderLineDto toDto(BeerOrderRequestDto.BeerOrderLineRequestDto requestDto);

  BeerOrderLineEntity toEntity(BeerOrderLineDto dto);
}
