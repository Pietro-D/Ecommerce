package ecommerce.backend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="customers")
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_at")
    private Date modifiedAt;

    @OneToMany(mappedBy = "customerAddress",cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToOne(mappedBy = "customerCart",cascade =CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customerOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;


    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = new Date();
    }

    @PrePersist
    protected void onPersist() {
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public Customer(String name, String surname, String phone, String email, String password){
        this.name=name;
        this.surname=surname;
        this.phone=phone;
        this.email=email;
        this.password=password;
    }

    public Customer(){}

    public void addAddress(Address address){

        this.getAddresses().add(address);

    }

    public void addOrder(Order order){
        this.getOrders().add(order);
    }



}
