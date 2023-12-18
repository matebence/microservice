package com.bence.mate.payment.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class PaymentEntity implements Serializable {

    @Id
    @Getter
    @Setter
    private String paymentId;

    @Getter
    @Setter
    @Column
    public String orderId;
}
