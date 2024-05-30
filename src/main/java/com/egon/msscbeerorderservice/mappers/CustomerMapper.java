package com.egon.msscbeerorderservice.mappers;

import com.egon.brewery.dtos.CustomerDto;
import com.egon.msscbeerorderservice.entities.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
  CustomerDto toDto(CustomerEntity entity);

  CustomerEntity toEntity(CustomerDto dto);
}
