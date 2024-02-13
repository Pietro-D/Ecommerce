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
import ecommerce.backend.dto.AddressDTO;
import ecommerce.backend.dto.MessageResponse;
import ecommerce.backend.exceptions.AddressAlreadyExistsException;
import ecommerce.backend.exceptions.AddressNotExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.model.Address;
import ecommerce.backend.services.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    

    @GetMapping
    public ResponseEntity<?> showAddresses(@AuthenticationPrincipal UserDetailsImpl user){
        List<Address> result=addressService.getAddresses(user.getUsername());
        List<AddressDTO> response= result.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> modifyAddress(@Valid @RequestBody AddressDTO address, 
                                           @AuthenticationPrincipal UserDetailsImpl user){

        try{
            addressService.modifyAddress(address, user.getUsername());
            return ResponseEntity.ok().body(new MessageResponse("address correctly updated"));
        }catch(AddressNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("address not found"), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDTO address,
                                        @AuthenticationPrincipal UserDetailsImpl user){
        
        try{
            addressService.addAddress(address, user.getUsername());
            return ResponseEntity.ok().body(new MessageResponse("address correctly added"));
        }catch(CustomerNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("user not found"), HttpStatus.NOT_FOUND);
        }catch(AddressAlreadyExistsException e){
            return new ResponseEntity<>(new MessageResponse("address already exists found"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> addAddress(@RequestParam(name="idAddress") Integer idAddress,
                                        @AuthenticationPrincipal UserDetailsImpl user){
        try{
            addressService.removeAddress(idAddress, user.getUsername());
            return ResponseEntity.ok().body(new MessageResponse("address correctly removed"));
        }catch(AddressNotExistsException e){
            return new ResponseEntity<>(new MessageResponse("address not found"), HttpStatus.NOT_FOUND);
        }
    
    }


    private AddressDTO convertToDTO(Address addr){
        AddressDTO res=new AddressDTO();
        res.copyInfo(addr);
        return res;
    }
    
}
