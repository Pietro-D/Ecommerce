package ecommerce.backend.exceptions;

public class CartNotExistsException extends RuntimeException {

    public CartNotExistsException(){
        super();
    }

    public CartNotExistsException(String message){
        super(message);
    }
    
}
