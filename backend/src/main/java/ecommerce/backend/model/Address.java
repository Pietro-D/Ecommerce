package ecommerce.backend.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address_line",nullable = false)
    private String addressLine;

    @Column(name = "postal_code",nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_at")
    private Date modifiedAt;

    @ManyToOne
    @JoinColumn(name="customer_id",referencedColumnName = "email")
    private Customer customerAddress;

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = new Date();
    }

    @PrePersist
    protected void onPersist(){
        this.modifiedAt = new Date();
        this.createdAt=new Date();

    }

    public Address(){}

    public Address(String addressLine, String postalCode, String country, String city){
        this.addressLine=addressLine;
        this.postalCode=postalCode;
        this.country=country;
        this.city=city;
    }



}
