package ecommerce.backend.dto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProdRequest {
    
    @NotNull
    @Positive
    private Integer idProd;

    @NotNull
    @Positive
    private Integer quantity;
}
