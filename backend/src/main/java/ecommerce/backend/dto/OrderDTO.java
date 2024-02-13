package ecommerce.backend.dto;


import java.util.ArrayList;
import java.util.List;


import ecommerce.backend.model.Order;
import ecommerce.backend.model.OrderElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    @NotNull
    @Positive
    private Float total;
    
    @NotNull
    @NotBlank
    private String createdAt;

    @NotNull
    private AddressDTO customerAddress;

    @NotNull
    private List<ProductDTO> ordersElement;

    public void copyInfo(Order ord){
        this.total=ord.getTotal();
        this.createdAt=ord.getCreatedAt().toString();

        this.customerAddress=new AddressDTO();
        this.customerAddress.copyInfo(ord.getCustomerAddress());

        this.ordersElement=new ArrayList<>();

        for(OrderElement oe: ord.getOrdersElement()){
            ProductDTO newEle=new ProductDTO();
            newEle.copyInfo(oe);
            this.ordersElement.add(newEle);
        }
        
    }
    
}
