package ecommerce.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.backend.configurations.UserDetailsImpl;
import ecommerce.backend.dto.AddProdRequest;
import ecommerce.backend.dto.MessageResponse;
import ecommerce.backend.dto.ProductDTO;
import ecommerce.backend.exceptions.CartNotExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.exceptions.ProductNotAvailableException;
import ecommerce.backend.exceptions.ProductNotInCartException;
import ecommerce.backend.exceptions.QuantityNotAvailableException;
import ecommerce.backend.model.CartElement;
import ecommerce.backend.services.CartService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<?> getProducts(@AuthenticationPrincipal UserDetailsImpl user){

        
        try{
            List<CartElement> result=cartService.getProducts(user.getUsername());
            List<ProductDTO> response= result.stream().map(this::convertToDTO).collect(Collectors.toList());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(CartNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("cart not found"),HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProdRequest prod,
                                        @AuthenticationPrincipal UserDetailsImpl user){

        try{
            cartService.addProductToCart(user.getUsername(), prod.getIdProd(), prod.getQuantity());
            return ResponseEntity.ok().body(new MessageResponse("product added to cart"));
        }catch(QuantityNotAvailableException e){
            return new ResponseEntity<>(new MessageResponse("quantity not available"), HttpStatus.BAD_REQUEST);
        }catch(CustomerNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("trying to access the cart of a non existing customer"), HttpStatus.BAD_REQUEST);
        }catch(ProductNotAvailableException e){
            return new ResponseEntity<>(new MessageResponse("product not in the db"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeProduct(@RequestParam(name = "idProd") Integer id,
                                           @AuthenticationPrincipal UserDetailsImpl user){

        try{
            cartService.removeProduct(user.getUsername(), id);
            return ResponseEntity.ok().body(new MessageResponse("product removed from the cart"));
        }catch(CartNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("the user doesn't have a cart"), HttpStatus.NOT_FOUND);
        }catch(ProductNotInCartException e){
            return new ResponseEntity<>(new MessageResponse("the product is not in the cart"), HttpStatus.NOT_FOUND);
        }
    }

    private ProductDTO convertToDTO(CartElement ce){
        ProductDTO res=new ProductDTO();
        res.copyInfo(ce);
        return res;
    }
}
