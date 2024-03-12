package com.egon.msscbeerorderservice.mappers;

import com.egon.brewery.dtos.BeerOrderLineDto;
import com.egon.brewery.dtos.request.BeerOrderRequestDto;
import com.egon.msscbeerorderservice.entities.BeerOrderLineEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerOrderLineDecoratorMapper.class)
public interface BeerOrderLineMapper {
  BeerOrderLineDto toDto(BeerOrderLineEntity entity);

  BeerOrderLineDto toDto(BeerOrderRequestDto.BeerOrderLineRequestDto requestDto);

  BeerOrderLineEntity toEntity(BeerOrderLineDto dto);
}
