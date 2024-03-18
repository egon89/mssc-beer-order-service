package com.egon.msscbeerorderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_beer_order_line")
public class BeerOrderLineEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36, updatable = false, nullable = false)
  private UUID id;

  @ManyToOne
  private BeerOrderEntity beerOrder;

  private UUID beerId;

  private String upc;

  private Integer orderQuantity = 0;

  private Integer quantityAllocated = 0;

  private BigDecimal price;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdAt;

  @UpdateTimestamp
  private Timestamp updatedAt;

  @Version
  private Long version;
}
