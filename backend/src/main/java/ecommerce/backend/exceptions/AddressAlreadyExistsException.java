package ecommerce.backend.exceptions;

public class AddressAlreadyExistsException extends RuntimeException{

    public AddressAlreadyExistsException(){
        super();
    }

    public AddressAlreadyExistsException(String message){
        super(message);
    }
    
    
}
