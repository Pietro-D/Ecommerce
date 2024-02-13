package ecommerce.backend.model;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name="carts")
public class Cart implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_at",nullable = false)
    private Date modifiedAt;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customerCart;

    

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = new Date();
    }

    @PrePersist
    protected void onPersist() {
        this.modifiedAt = new Date();
        this.createdAt=new Date();
    }

    

}
