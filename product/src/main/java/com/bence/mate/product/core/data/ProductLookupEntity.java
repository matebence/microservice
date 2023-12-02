package com.bence.mate.product.core.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productlookup")
// We don't need here all properties from the Products, we just need a Primary ones which
// identifies its uniqueness in the data table
public class ProductLookupEntity implements Serializable {

    @Id
    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    @Column(unique = true)
    private String title;
}
