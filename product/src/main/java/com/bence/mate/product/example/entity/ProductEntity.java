package com.bence.mate.product.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class ProductEntity implements Serializable {

    @Id
    @Getter
    @Setter
    @Column(unique = true)
    private String productId;

    @Getter
    @Setter
    @Column(unique=true)
    private String title;

    @Getter
    @Setter
    private BigDecimal price;

    @Getter
    @Setter
    private Integer quantity;
}
