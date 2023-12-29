package com.egon.msscbeerorderservice.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Component
public class DateMapper {

  public Timestamp toTimestamp(OffsetDateTime offsetDateTime) {
    if (Objects.isNull(offsetDateTime)) return null;

    return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
  }

  public OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
    if (Objects.isNull(timestamp)) return null;

    return OffsetDateTime.of(timestamp.toLocalDateTime(), ZoneOffset.UTC);
  }
}
