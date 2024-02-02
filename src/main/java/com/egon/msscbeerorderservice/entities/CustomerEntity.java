package com.egon.msscbeerorderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
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
@Table(name = "tb_customer")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JdbcTypeCode(java.sql.Types.VARCHAR)
  @Column(length = 36, updatable = false, nullable = false)
  private UUID id;

  private String name;

  @JdbcTypeCode(java.sql.Types.VARCHAR)
  @Column(length = 36)
  private UUID apiKey;

  @OneToMany(mappedBy = "customer")
  private Set<BeerOrderEntity> beerOrders;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdAt;

  @UpdateTimestamp
  private Timestamp updatedAt;

  @Version
  private Long version;
}
