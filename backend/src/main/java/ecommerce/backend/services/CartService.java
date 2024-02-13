package ecommerce.backend.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.exceptions.CartNotExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.exceptions.ProductNotAvailableException;
import ecommerce.backend.exceptions.ProductNotInCartException;
import ecommerce.backend.exceptions.QuantityNotAvailableException;
import ecommerce.backend.model.Cart;
import ecommerce.backend.model.CartElement;
import ecommerce.backend.model.Customer;
import ecommerce.backend.model.Product;
import ecommerce.backend.repositories.CartElementRepository;
import ecommerce.backend.repositories.CartRepository;
import ecommerce.backend.repositories.CustomerRepository;
import ecommerce.backend.repositories.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired 
    private CartElementRepository cartElementRep;

    @Autowired
    private ProductRepository productRep;

    @Autowired
    private CustomerRepository customerRep;


    @Transactional(readOnly = true)
    public List<CartElement>  getProducts(String email){

        Integer idCart=cartRepo.findIdByCustomer(email)
                                .orElseThrow( ()-> new CartNotExistsException());
        
        return cartElementRep.findAllByCart(idCart);
    }

    @Transactional
    public void addProductToCart(String email, Integer prodId, Integer quantity){

        Cart cart=cartRepo.findCartByCustomer(email).orElse(null);
        Product prod=productRep.findById(prodId)
                                .orElseThrow( () -> new ProductNotAvailableException());

        //add a new cart if necessary
        if(cart==null){
            Customer customer=customerRep.findByEmail(email)
                                            .orElseThrow( () -> new CustomerNotExistsException());                      
            cart=new Cart();
            cart.setCustomerCart(customer);
            cartRepo.save(cart);
            customer.setCart(cart);
        }
        //check if the product is available
        if(prod.getQuantity()<quantity) throw new QuantityNotAvailableException();

        CartElement cartElement= cartElementRep.findByCartAndProduct(cart.getId(), prodId).orElse(null);

        //product not in the cart
        if(cartElement==null){
            cartElement=new CartElement(quantity,cart,prod);
            cartElementRep.save(cartElement);

        }
        else{ //product in the cart
            if(cartElement.getQuantity()+quantity>prod.getQuantity()) throw new QuantityNotAvailableException();
            cartElement.setQuantity(quantity+cartElement.getQuantity());
        }
    }

    @Transactional
    public void removeProduct(String email, Integer idProd){

        Cart cart= cartRepo.findCartByCustomer(email)
                            .orElseThrow( () -> new CartNotExistsException());
        
        CartElement toRemove=cartElementRep.findByCartAndProduct(cart.getId(), idProd).orElseThrow(
            ()-> new ProductNotInCartException());

        cartElementRep.delete(toRemove);
        
    }




    
}
