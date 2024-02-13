package ecommerce.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.backend.configurations.UserDetailsImpl;
import ecommerce.backend.dto.MessageResponse;
import ecommerce.backend.dto.OrderDTO;
import ecommerce.backend.dto.PurchaseDTO;
import ecommerce.backend.exceptions.AddressNotExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.exceptions.ProductNotAvailableException;
import ecommerce.backend.exceptions.ProductNotInCartException;
import ecommerce.backend.exceptions.QuantityNotAvailableException;
import ecommerce.backend.model.Order;
import ecommerce.backend.services.OrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                        @RequestParam(name = "sort", defaultValue = "createdAt") String sort,
                                        @AuthenticationPrincipal UserDetailsImpl user){
            
        List<Order> result=orderService.getHistory(user.getUsername(), pageNumber, pageSize, sort);
        List<OrderDTO> response=result.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody PurchaseDTO purchase,
                                        @AuthenticationPrincipal UserDetailsImpl user){
        try{
            orderService.placeOrder(user.getUsername(), purchase);
            return ResponseEntity.ok().body(new MessageResponse("order placed correctly"));
        }catch(ProductNotInCartException e){
            return new ResponseEntity<>(new MessageResponse("buying a product not in cart"),HttpStatus.NOT_FOUND);
        }catch(AddressNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("address selected not found"),HttpStatus.NOT_FOUND);
        }catch(CustomerNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("customer placing the order not in db"),HttpStatus.NOT_FOUND);
        }catch(ProductNotAvailableException e){
            return new ResponseEntity<>(new MessageResponse("trying to buy a product not in db"),HttpStatus.NOT_FOUND);
        }catch(QuantityNotAvailableException e){
            return new ResponseEntity<>(new MessageResponse("product quantity not available"),HttpStatus.BAD_REQUEST);
        }

    }

    private OrderDTO convertToDto(Order order){
        OrderDTO res=new OrderDTO();
        res.copyInfo(order);
        return res;
    }
}
