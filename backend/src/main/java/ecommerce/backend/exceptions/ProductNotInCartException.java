package ecommerce.backend.exceptions;

public class ProductNotInCartException extends RuntimeException {

    public ProductNotInCartException(){
        super();
    }

    public ProductNotInCartException(String message){
        super(message);
    }
    
}
