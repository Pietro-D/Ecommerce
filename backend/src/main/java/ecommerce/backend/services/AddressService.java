package ecommerce.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.backend.dto.AddressDTO;
import ecommerce.backend.exceptions.AddressAlreadyExistsException;
import ecommerce.backend.exceptions.AddressNotExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.exceptions.InternalErrorException;
import ecommerce.backend.model.Address;
import ecommerce.backend.model.Customer;
import ecommerce.backend.repositories.AddressRepository;
import ecommerce.backend.repositories.CustomerRepository;


@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRep;

    @Autowired
    private CustomerRepository customerRep;

    @Transactional
    public void removeAddress(Integer idAddress, String emailCustomer) throws AddressNotExistsException, InternalErrorException{

        Address toRemove=addressRep.findByUsernameAndId(emailCustomer,idAddress)
                                    .orElseThrow(() ->  new AddressNotExistsException());
        addressRep.delete(toRemove);
    }

    @Transactional(readOnly = true)
    public List<Address> getAddresses(String emailCustomer){

        return addressRep.findByUsername(emailCustomer);
    }

    @Transactional
    public void modifyAddress(AddressDTO addr, String emailCustomer){

        Address toModify=addressRep.findByUsernameAndId(emailCustomer, addr.getId())
                                    .orElseThrow( () -> new AddressNotExistsException());
        toModify.setCity(addr.getCity());
        toModify.setAddressLine(addr.getAddressLine());
        toModify.setPostalCode(addr.getPostalCode());
        toModify.setCountry(addr.getCountry());
    }

    @Transactional
    public void addAddress(AddressDTO addr, String emailCustomer){

        Customer customer =customerRep.findByEmail(emailCustomer)
                                        .orElseThrow(()->new CustomerNotExistsException());

        if(addressRep.existsAddress(emailCustomer, addr.getAddressLine(), addr.getPostalCode(), 
                                    addr.getCountry(), addr.getCity())) throw new AddressAlreadyExistsException();
        
        Address newAddress=new Address(addr.getAddressLine(), addr.getPostalCode(), 
                                addr.getCountry(), addr.getCity());
        
        newAddress.setCustomerAddress(customer);
        Address savedAddress=addressRep.save(newAddress);
        customer.addAddress(savedAddress);
    }
}
