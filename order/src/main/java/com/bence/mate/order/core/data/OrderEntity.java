package com.bence.mate.order.core.data;

import com.bence.mate.order.core.model.OrderStatus;
import javax.persistence.EnumType;
import java.io.Serializable;

import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class OrderEntity implements Serializable {

    @Id
    @Getter
    @Setter
    @Column(unique = true)
    public String orderId;

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private String addressId;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
