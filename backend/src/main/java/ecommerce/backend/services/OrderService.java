package ecommerce.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.dto.PurchaseDTO;
import ecommerce.backend.exceptions.AddressNotExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.exceptions.ProductNotAvailableException;
import ecommerce.backend.exceptions.ProductNotInCartException;
import ecommerce.backend.exceptions.QuantityNotAvailableException;
import ecommerce.backend.model.Address;
import ecommerce.backend.model.CartElement;
import ecommerce.backend.model.Customer;
import ecommerce.backend.model.Order;
import ecommerce.backend.model.OrderElement;
import ecommerce.backend.model.Product;
import ecommerce.backend.repositories.AddressRepository;
import ecommerce.backend.repositories.CartElementRepository;
import ecommerce.backend.repositories.CustomerRepository;
import ecommerce.backend.repositories.OrderElementRepository;
import ecommerce.backend.repositories.OrderRepository;
import ecommerce.backend.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRep;

    @Autowired
    private ProductRepository productRep;

    @Autowired
    private CustomerRepository customerRep;

    @Autowired
    private AddressRepository addressRep;

    @Autowired
    private OrderElementRepository orderElementRep;

    @Autowired
    private CartElementRepository cartElementRep;


   @Transactional(readOnly = true)
   public List<Order> getHistory(String email,Integer pageNumber, Integer pageSize, String sort){
        Pageable p= PageRequest.of(pageNumber,pageSize,Sort.by(sort));
         Page<Order> page=orderRep.findAllByCustomer(email, p);
         return page.getContent();
   }

   @Transactional
   public void placeOrder(String email, PurchaseDTO p){
        List<Integer> ids= p.getIdsCartElements();
        List<CartElement> cartElements=new ArrayList<>();
        for(Integer id: ids)
            cartElements.add(cartElementRep.findById(id).orElseThrow(()->new ProductNotInCartException()));
            

        Customer customer=customerRep.findByEmail(email).orElseThrow(()-> new CustomerNotExistsException());
        Address addr= addressRep.findById(p.getIdAddr()).orElseThrow(()-> new AddressNotExistsException());
        
        //check if the products are available
        float total=0;
        for(CartElement elem : cartElements){
            Integer qtaToBuy= elem.getQuantity();
            Product prod=productRep.findById(elem.getProduct().getId())
                                    .orElseThrow( ()->new ProductNotAvailableException());
            
            if(qtaToBuy>prod.getQuantity()) throw new QuantityNotAvailableException();
            
            prod.setQuantity(prod.getQuantity()-qtaToBuy);

            Float priceDiscounted=prod.getDiscountPrice();
            total += qtaToBuy * ((priceDiscounted !=null) ? priceDiscounted : prod.getPrice());
        }

        //Create a new order
        Order newOrder=new Order(total,customer,addr);
        customer.addOrder(newOrder);

        //add cart products to order
        for(CartElement elem:cartElements){
            OrderElement newElement=new OrderElement(elem.getQuantity(), newOrder, elem.getProduct());
            orderElementRep.save(newElement);
            newOrder.add(newElement);

            //remove from cart
            entityManager.merge(elem);
            entityManager.remove(elem);
            
        }
        newOrder=orderRep.save(newOrder);
   }
}
