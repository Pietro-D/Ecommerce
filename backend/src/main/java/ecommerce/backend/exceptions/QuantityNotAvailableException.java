package ecommerce.backend.exceptions;

public class QuantityNotAvailableException extends RuntimeException{

    public QuantityNotAvailableException(){
        super();
    }

    public QuantityNotAvailableException(String message){
        super(message);
    }
    
}
