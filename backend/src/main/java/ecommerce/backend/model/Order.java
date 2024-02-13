package ecommerce.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Float total;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",nullable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "email")
    private Customer customerOrder;

    @ManyToOne
    @JoinColumn(name="address_id")
    private Address customerAddress;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade = CascadeType.REMOVE)
    private List<OrderElement> ordersElement;

    @PrePersist
    public void onPersist(){
        this.createdAt=new Date();
    }

    public Order(){}

    public Order(Float total, Customer customer, Address address){
        this.total=total;
        this.customerOrder=customer;
        this.customerAddress=address;
        this.ordersElement=new ArrayList<>();
    }

    public void add(OrderElement ord){
        this.ordersElement.add(ord);
    }

   
}
