package ecommerce.backend.exceptions;

public class AddressNotExistsException extends RuntimeException {

    public AddressNotExistsException(){
        super();
    }

    public AddressNotExistsException(String message){
        super(message);
    }
    
}
