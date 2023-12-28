package com.egon.msscbeerorderservice.entities;

import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_beer_order")
public class BeerOrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36, updatable = false, nullable = false)
  private UUID id;

  private String customerReference;

  @ManyToOne
  private CustomerEntity customer;

  @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  private Set<BeerOrderLineEntity> beerOrderLines;

  @Enumerated(EnumType.STRING)
  private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;

  private String orderStatusCallbackUrl;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdAt;

  @UpdateTimestamp
  private Timestamp updatedAt;

  @Version
  private Long version;
}
