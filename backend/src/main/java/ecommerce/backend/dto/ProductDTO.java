package ecommerce.backend.dto;





import ecommerce.backend.model.CartElement;
import ecommerce.backend.model.OrderElement;
import ecommerce.backend.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Integer id;
   
    private String name;

    private String description;
    
    private Float price;
   
    private Float discountPrice;

    private Integer quantity;

    private String image;

    public void copyInfo(Product prod){
        this.id=prod.getId();
        this.description=prod.getDescription();
        this.name=prod.getName();
        this.price=prod.getPrice();
        this.quantity=prod.getQuantity();
        this.discountPrice=prod.getDiscountPrice();
        this.image=prod.getImage();
    }

    public void copyInfo(OrderElement ord){
        this.id=ord.getProduct().getId();
        this.description=null;
        this.name=ord.getProduct().getName();
        this.price=ord.getProduct().getPrice();
        this.quantity=ord.getQuantity();
        this.discountPrice=ord.getProduct().getDiscountPrice();
        this.image=ord.getProduct().getImage();
    }

    public void copyInfo(CartElement ce){
        this.id=ce.getProduct().getId();
        this.description=null;
        this.name=ce.getProduct().getName();
        this.price=ce.getProduct().getPrice();
        this.quantity=ce.getQuantity();
        this.discountPrice=ce.getProduct().getDiscountPrice();
        this.image=ce.getProduct().getImage();
    }
    
}
