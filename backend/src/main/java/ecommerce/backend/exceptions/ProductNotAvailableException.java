package ecommerce.backend.exceptions;

public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException(){
        super();
    }

    public ProductNotAvailableException(String message){
        super(message);
    }
    
}
