package ecommerce.backend.exceptions;

public class CustomerNotExistsException extends RuntimeException{

    public CustomerNotExistsException(){
        super();
    }

    public CustomerNotExistsException(String message){
        super(message);
    }
    
}
